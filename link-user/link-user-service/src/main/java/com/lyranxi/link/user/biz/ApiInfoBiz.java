package com.lyranxi.link.user.biz;

import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.lyranxi.link.common.util.uuid.UuidUtil;
import com.lyranxi.link.mysql.biz.BaseBiz;
import com.lyranxi.link.user.entity.ApiInfo;
import com.lyranxi.link.user.mapper.ApiInfoMapper;
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
public class ApiInfoBiz extends BaseBiz<ApiInfoMapper, ApiInfo> {

    public List<ApiInfo> getAll() {
        return this.baseMapper.selectAll();
    }

    public boolean batchUpdate(List<ApiInfo> apiInfos) {
        return SqlHelper.retBool(this.baseMapper.batchUpdateById(apiInfos));
    }

    public boolean batchSave(List<ApiInfo> apiInfos) {
        for (ApiInfo apiInfo : apiInfos) {
            apiInfo.setPermissionId(UuidUtil.uuid22());
        }
        return this.batchInsert(apiInfos, 200);
    }

}
