package com.lyranxi.link.user.service;

import com.lyranxi.link.common.exception.BusinessException;
import com.lyranxi.link.user.biz.ApiBiz;
import com.lyranxi.link.user.bo.ApiCreateBO;
import com.lyranxi.link.user.bo.ApiSearchBO;
import com.lyranxi.link.user.converter.ApiConverter;
import com.lyranxi.link.user.entity.Api;
import com.lyranxi.link.user.feign.ApiFeign;
import com.lyranxi.link.user.vo.ApiVO;
import com.lyranxi.link.web.result.Result;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author ranxi
 * @date 2025-04-10 18:14
 */
@Slf4j
@Service
@RestController
@RequiredArgsConstructor
public class ApiService implements ApiFeign {

    private final ApiBiz apiBiz;

    @Override
    public Result<ApiVO> getByUri(Integer scope, String method, String uri) {
        return Result.success(apiBiz.getByUri(scope, method, uri));
    }


    @Transactional(rollbackFor = Exception.class)
    public Result<Boolean> batchSave(List<ApiCreateBO> params) {
        for (ApiCreateBO param : params) {
            Api database = apiBiz.getByScopeAndMethodAndUri(param.getScope(), param.getMethod(), param.getUrl());
            Api entity = ApiConverter.INSTANCE.to(param);
            if (database != null) {
                entity.setId(database.getId());
                if (!apiBiz.updateByPrimaryKey(entity)) {
                    log.error("更新api失败: {}", entity);
                    throw new BusinessException("update api id = '" + entity.getId() + "' failed");
                }
            } else {
                if (!apiBiz.insert(entity)) {
                    log.warn("添加接口失败: {}", entity);
                    throw new BusinessException("insert api name = '" + entity.getName() + "' failed");
                }
            }
        }
        GenericMessage<String> message = new GenericMessage<>("s");
        return Result.success(Boolean.TRUE);
    }

    @Override
    public Result<List<ApiVO>> getByScope(Integer scope) {
        return Result.success(apiBiz.getByScope(scope));
    }

    @Override
    public Result<List<ApiVO>> search(ApiSearchBO params) {
        return Result.success(apiBiz.search(params));
    }
}
