package com.lyranxi.link.user.biz;

import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.lyranxi.link.common.util.uuid.UuidUtil;
import com.lyranxi.link.mysql.biz.BaseBiz;
import com.lyranxi.link.user.entity.Permission;
import com.lyranxi.link.user.mapper.PermissionMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * permission biz
 *
 * @author ranxi
 * @date 2025-03-18 17:03
 */
@Slf4j
@Service
public class PermissionBiz extends BaseBiz<PermissionMapper, Permission> {

    public List<Permission> getAll() {
        return this.baseMapper.selectAll();
    }

    public boolean batchUpdate(List<Permission> permissions) {
        return SqlHelper.retBool(this.baseMapper.batchUpdateById(permissions));
    }

    public boolean batchSave(List<Permission> permissions) {
        for (Permission permission : permissions) {
            permission.setPermissionId(UuidUtil.uuid22());
        }
        return this.batchInsert(permissions, 200);
    }

}
