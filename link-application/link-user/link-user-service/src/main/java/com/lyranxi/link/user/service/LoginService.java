package com.lyranxi.link.user.service;

import cn.hutool.core.collection.CollectionUtil;
import com.lyranxi.link.common.util.asserts.AssertBusinessException;
import com.lyranxi.link.common.util.asserts.AssertRequestParameter;
import com.lyranxi.link.common.util.tree.TreeTransformer;
import com.lyranxi.link.common.util.uuid.UuidUtil;
import com.lyranxi.link.redis.JdkSerializationRedisTemplate;
import com.lyranxi.link.redis.unrepatable.Unrepeatable;
import com.lyranxi.link.user.biz.*;
import com.lyranxi.link.user.bo.UserLoginBO;
import com.lyranxi.link.user.converter.MenuConverter;
import com.lyranxi.link.user.converter.UserConverter;
import com.lyranxi.link.user.entity.Menu;
import com.lyranxi.link.user.entity.Role;
import com.lyranxi.link.user.entity.User;
import com.lyranxi.link.user.enums.RoleScopeEnum;
import com.lyranxi.link.user.feign.UserLoginFeign;
import com.lyranxi.link.user.token.UserTenantInfo;
import com.lyranxi.link.user.token.UserToken;
import com.lyranxi.link.user.util.TokenRedisUtil;
import com.lyranxi.link.user.vo.LoginVO;
import com.lyranxi.link.user.vo.MenuTreeSimpleVO;
import com.lyranxi.link.web.result.Result;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author ranxi
 * @date 2025-04-11 15:36
 */
@Slf4j
@Service
@RestController
@RequiredArgsConstructor
public class LoginService implements UserLoginFeign {

    private final UserBiz userBiz;
    private final RoleBiz roleBiz;
    private final TenantBiz tenantBiz;
    private final MenuBiz menuBiz;
    private final UserRoleRelateBiz userRoleRelateBiz;
    private final JdkSerializationRedisTemplate jdkSerializationRedisTemplate;


    @Unrepeatable(key = "'user:login:password:' + #username", leaseTime = 3_000)
    @Override
    public Result<LoginVO> loginByPassword(UserLoginBO params) {
        // 根据username查询用户信息
        User user = userBiz.getByUsername(params.getUsername());
        AssertBusinessException.nonNull(user, "账号或密码错误");
        // 生成token
        String token = UuidUtil.uuid22();
        UserToken userToken = new UserToken(user.getId(), user.getUsername(), user.getNickname(), params.getPlatform(), params.getClientId());
        // 填充apiIds
        userToken.setApiIds(roleBiz.getApiIdsByUserIdAndPlatform(user.getId(), params.getPlatform()));
        // 保存token到redis
        this.putTokenToRedis(token, userToken);
        return Result.success(this.buildLoginVO(token, user.getId()));
    }

    @Override
    public Result<Boolean> logout(String token) {
        this.doLogout(token);
        return Result.ok();
    }

    @Override
    public Result<List<MenuTreeSimpleVO>> getMenuTree(String token) {
        UserToken usertoken = this.getTokenFromRedis(token);
        AssertRequestParameter.nonNull(usertoken, "token无效");

        // 查询用户菜单信息
        Role role = roleBiz.getRoleByUserId(usertoken.getUserId());
        List<Menu> menus = menuBiz.getMenuListByRoleIdAndPlatform(role.getId(), usertoken.getPlatform());
        List<MenuTreeSimpleVO> menuTreeSimpleVOS = MenuConverter.INSTANCE.entity2TreeSimpleVO(menus);
        return Result.success(TreeTransformer.toTree(menuTreeSimpleVOS));
    }

    /**
     * 生成loginVO
     *
     * @param token  token
     * @param userId 用户ID
     * @return LoginVO
     */
    private LoginVO buildLoginVO(String token, Long userId) {
        LoginVO loginVo = new LoginVO();
        loginVo.setToken(token);
        // 查询可登录门店
        Role role = userRoleRelateBiz.getUserMaxScopeRole(userId);
        Set<UserTenantInfo> userTenantInfos = this.getUserTenant(role);
        loginVo.setUserTenants(userTenantInfos);
        // 设置登录门店
        UserTenantInfo currentLoginTenantInfo = this.setCurrentLoginTenantInfo(role, userTenantInfos);
        loginVo.setCurrentTenant(currentLoginTenantInfo);
        // 用户基础信息
        User user = userBiz.getById(userId);
        loginVo.setUserInfo(UserConverter.INSTANCE.entity2SimpleInfo(user));
        return loginVo;
    }

    /**
     * 用户退出登录
     *
     * @param token token
     */
    private void doLogout(String token) {
        this.removeTokenFromRedis(token);
    }


    /**
     * 查询用户可以登录的租户
     * <ul>
     *     <li>租户级别则查询当前用户关联的租户和下属品牌、门店</li>
     *     <li>品牌级别则查询当前用户关联的下属品牌、门店</li>
     *     <li>门店级别则查询当前用户关联的归属门店</li>
     * </ul>
     *
     * @param role 用户的角色信息
     * @return List<UserTenantInfo> 可以登录的租户信息
     */
    private Set<UserTenantInfo> getUserTenant(Role role) {
        return switch (RoleScopeEnum.getByIdMust(role.getRoleScope())) {
            case RoleScopeEnum.TENANT -> tenantBiz.getTenantAllStores(role.getTenantId());
            case RoleScopeEnum.BRAND -> tenantBiz.getBrandAllStores(role.getBrandId());
            case RoleScopeEnum.STORE -> tenantBiz.getStore(role.getStoreId());
        };
    }

    /**
     * 设置当前登录租户信息
     *
     * @param role        当前用户角色信息
     * @param tenantInfos 可登录角色信息
     * @return UserTenantInfo 当前登录租户信息
     */
    private UserTenantInfo setCurrentLoginTenantInfo(Role role, Set<UserTenantInfo> tenantInfos) {

        return switch (RoleScopeEnum.getByIdMust(role.getRoleScope())) {
            case RoleScopeEnum.TENANT -> {
                UserTenantInfo tenant = CollectionUtil.getFirst(tenantInfos);
                yield new UserTenantInfo(tenant.getTenantId(), tenant.getTenantName());
            }
            case RoleScopeEnum.BRAND -> {
                UserTenantInfo tenant = CollectionUtil.getFirst(tenantInfos);
                yield new UserTenantInfo(tenant.getTenantId(), tenant.getTenantName(), tenant.getBrandId(), tenant.getBrandName());
            }
            case RoleScopeEnum.STORE -> CollectionUtil.getFirst(tenantInfos);
        };
    }


    /**
     * 保存token到redis中
     *
     * @param token     token
     * @param userToken 用户token信息
     */
    private void putTokenToRedis(String token, UserToken userToken) {
        jdkSerializationRedisTemplate.set(TokenRedisUtil.getKey(token), userToken, TokenRedisUtil.EXPIRE_SECONDS, TimeUnit.SECONDS);
    }

    /**
     * 从redis中查询用户token信息
     *
     * @param token token
     * @return UserToken 用户token信息
     */
    private UserToken getTokenFromRedis(String token) {
        return jdkSerializationRedisTemplate.get(TokenRedisUtil.getKey(token), UserToken.class);
    }

    /**
     * 从redis中删除token
     *
     * @param token token信息
     */
    private void removeTokenFromRedis(String token) {
        jdkSerializationRedisTemplate.delete(TokenRedisUtil.getKey(token));
    }

}
