package com.lyranxi.link.user.biz;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.lyranxi.link.mysql.biz.BaseBiz;
import com.lyranxi.link.user.bo.ApiSearchBO;
import com.lyranxi.link.user.converter.ApiConverter;
import com.lyranxi.link.user.entity.Api;
import com.lyranxi.link.user.mapper.ApiMapper;
import com.lyranxi.link.user.vo.ApiVO;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.apache.bcel.generic.RET;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * api biz
 *
 * @author ranxi
 * @date 2025-04-09 18:19
 */
@Slf4j
@Service
public class ApiBiz extends BaseBiz<ApiMapper, Api> {

    /**
     * 查询所有接口信息
     *
     * @return List<Api> 接口信息
     */
    public List<Api> getAll() {
        return this.baseMapper.selectAll();
    }

    /**
     * 批量更新接口信息
     *
     * @param apis apis
     * @return boolean 是否保存成功
     */
    public boolean batchUpdate(List<Api> apis) {
        if (CollectionUtil.isEmpty(apis)) {
            return Boolean.TRUE;
        }
        return SqlHelper.retBool(this.baseMapper.batchUpdateById(apis));
    }

    /**
     * 批量保存
     *
     * @param apis 接口信息
     * @return boolean 是否保存成功
     */
    public boolean batchSave(List<Api> apis) {
        if (CollectionUtil.isEmpty(apis)) {
            return Boolean.TRUE;
        }
        return this.batchInsert(apis, 200);
    }

    public ApiVO getByUri(Integer scope, String method, String uri) {
        Api api = this.getByScopeAndMethodAndUri(scope, method, uri);
        return ApiConverter.INSTANCE.to(api);
    }


    public Api getByScopeAndMethodAndUri(Integer scope, String method, String uri) {
        return this.baseMapper.selectOne(Wrappers.<Api>lambdaQuery()
                .eq(Api::getScope, scope)
                .eq(Api::getMethod, method.toUpperCase())
                .eq(Api::getUrl, uri));
    }

    public List<ApiVO> getByScope(Integer scope) {
        List<Api> apis = this.baseMapper.selectList(Wrappers.lambdaQuery(Api.class)
                .eq(Api::getScope, scope));
        return ApiConverter.INSTANCE.entity2Vo(apis);
    }

    public List<ApiVO> search(ApiSearchBO params) {
        return this.baseMapper.selectByCondition(params);
    }

}
