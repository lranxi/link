package com.lyranxi.link.user.controller;

import com.lyranxi.link.common.annotation.Api;
import com.lyranxi.link.user.bo.RevisionCreateBO;
import com.lyranxi.link.user.bo.RevisionUpdateBO;
import com.lyranxi.link.user.feign.RevisionFeign;
import com.lyranxi.link.user.http.HttpContext;
import com.lyranxi.link.web.result.Result;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 版本接口
 *
 * @author ranxi
 * @date 2025-04-23 16:46
 */
@RestController
@RequestMapping("/revision")
@RequiredArgsConstructor
public class RevisionController {

    private final RevisionFeign revisionFeign;

    /**
     * 创建版本
     *
     * @param params 版本信息
     * @return 是否创建成功
     */
    @Api(name = "创建版本", author = "lyranxi")
    @PostMapping("/create")
    public Result<Boolean> create(@RequestBody @Valid RevisionCreateBO params) {
        return revisionFeign.create(HttpContext.build(params));
    }


    /**
     * 编辑版本
     *
     * @param params 版本信息
     * @return 是否编辑成功
     */
    @Api(name = "创建版本", author = "lyranxi")
    @PostMapping("/update")
    public Result<Boolean> update(@RequestBody @Valid RevisionUpdateBO params) {
        return revisionFeign.update(HttpContext.build(params));
    }

    /**
     * 删除版本
     *
     * @param revisionId 版本ID
     * @return 是否删除成功
     */
    @Api(name = "删除版本", author = "lyranxi")
    @GetMapping("/delete")
    public Result<Boolean> delete(@RequestParam("revisionId") Long revisionId) {
        return revisionFeign.delete(HttpContext.build(revisionId));
    }


    /**
     * 修改版本状态
     *
     * @param revisionId 版本ID
     * @return 是否修改成功
     */
    @Api(name = "修改版本状态", author = "lyranxi")
    @GetMapping("/switch-enable")
    public Result<Boolean> switchEnable(@RequestParam("revisionId") Long revisionId) {
        return revisionFeign.switchEnable(HttpContext.build(revisionId));
    }

}
