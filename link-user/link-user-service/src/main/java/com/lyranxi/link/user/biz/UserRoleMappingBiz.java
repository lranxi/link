package com.lyranxi.link.user.biz;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.lyranxi.link.mysql.biz.BaseBiz;
import com.lyranxi.link.user.entity.Role;
import com.lyranxi.link.user.entity.UserRoleMapping;
import com.lyranxi.link.user.mapper.UserRoleMappingMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author ranxi
 * @date 2025-03-18 18:09
 */
@Slf4j
@Service
public class UserRoleMappingBiz extends BaseBiz<UserRoleMappingMapper, UserRoleMapping> {

    public boolean insertMappings(String roleId, Set<String> userIds) {
        List<UserRoleMapping> mappings = new ArrayList<>(userIds.size());
        for (String userId : userIds) {
            mappings.add(UserRoleMapping.builder()
                    .roleId(roleId)
                    .userId(userId)
                    .build());
        }
        return this.batchInsert(mappings);
    }


    public boolean deleteByRoleId(String roleId) {
        return SqlHelper.retBool(this.baseMapper.deleteByRoleId(roleId));
    }

}
