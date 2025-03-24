package com.lyranxi.link.user.service;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.lyranxi.link.user.biz.ApiInfoBiz;
import com.lyranxi.link.user.bo.api.ApiInfoCreateBO;
import com.lyranxi.link.user.converter.ApiInfoConverter;
import com.lyranxi.link.user.entity.ApiInfo;
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
public class ApiInfoService {

    private final ApiInfoBiz apiInfoBiz;

    /**
     * 批量保存接口信息
     *
     * @param params 接口信息
     * @return 是否保存成功
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean batchSave(List<ApiInfoCreateBO> params) {
        List<ApiInfo> newApiInfos = ApiInfoConverter.INSTANCE.to(params);
        // 查询所有接口信息
        List<ApiInfo> existApiInfos = apiInfoBiz.getAll();
        List<ApiInfo> updateApiInfos = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(existApiInfos)) {
            for (ApiInfo existApiInfo : existApiInfos) {
                String existApiSign = existApiInfo.getApiPath() + existApiInfo.getApiMethod();
                for (ApiInfo apiInfo : newApiInfos) {
                    String newApiSign = apiInfo.getApiPath() + apiInfo.getApiMethod();
                    if (StrUtil.equals(existApiSign, newApiSign)) {
                        apiInfo.setId(existApiInfo.getId());
                        apiInfo.setPermissionId(existApiInfo.getPermissionId());
                        updateApiInfos.add(apiInfo);
                    }
                }
            }
        }
        apiInfoBiz.batchUpdate(updateApiInfos);

        return apiInfoBiz.batchSave(newApiInfos);
    }


}
