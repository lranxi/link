package com.lyranxi.link.user.service;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.lyranxi.link.user.biz.PermissionBiz;
import com.lyranxi.link.user.bo.permission.PermissionCreateBO;
import com.lyranxi.link.user.converter.PermissionConverter;
import com.lyranxi.link.user.entity.Permission;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ranxi
 * @date 2025-03-24 18:09
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PermissionService {

    private final PermissionBiz permissionBiz;

    /**
     * 批量保存接口信息
     *
     * @param params 接口信息
     * @return 是否保存成功
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean batchSave(List<PermissionCreateBO> params) {
        List<Permission> newPermissions = PermissionConverter.INSTANCE.to(params);
        // 查询所有接口信息
        List<Permission> existPermissions = permissionBiz.getAll();
        List<Permission> updatePermissions = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(existPermissions)) {
            for (Permission existPermission : existPermissions) {
                String existApiSign = existPermission.getApiPath() + existPermission.getApiMethod();
                for (Permission permission : newPermissions) {
                    String newApiSign = permission.getApiPath() + permission.getApiMethod();
                    if (StrUtil.equals(existApiSign, newApiSign)) {
                        permission.setId(existPermission.getId());
                        permission.setPermissionId(existPermission.getPermissionId());
                        updatePermissions.add(permission);
                    }
                }
            }
        }
        permissionBiz.batchUpdate(updatePermissions);

        return permissionBiz.batchSave(newPermissions);
    }


}
