package com.lyranxi.link.user.bo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Set;

/**
 * @author ranxi
 * @date 2025-04-21 18:39
 */
@Data
public class MenuUpdateBO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 菜单ID
     */
    @NotNull(message = "菜单ID不能为空")
    private Long id;

    /**
     * 父级ID（一级菜单pid=0）
     */
    @NotNull(message = "父级菜单不能为空")
    private Long pid;

    /**
     * 名称
     */
    @NotBlank(message = "菜单名称不能为空")
    @Size(min = 1, max = 50, message = "菜单名称长度不能超过50个字符")
    private String name;

    /**
     * 路径
     */
    @Size(min = 1, max = 200, message = "菜单路径长度不能超过200个字符")
    private String url;

    /**
     * icon
     */
    @Size(min = 1, max = 100, message = "菜单icon长度不能超过100个字符")
    private String icon;

    /**
     * 类型(1:目录/2:菜单/3:按钮)
     */
    @NotNull(message = "菜单类型不能为空")
    @Size(min = 1, max = 3, message = "菜单类型只能为目录/菜单/按钮")
    private Integer type;

    /**
     * 排序
     */
    @NotNull(message = "排序不能为空")
    @Size(min = 1, max = 99999, message = "排序号最大值为99999")
    private Integer sort;

    /**
     * 平台(1:运营后台/2: 代理商后台/3: 租户后台/4: 商家app/5: 用户小程序)
     */
    @NotNull(message = "归属平台不能为空")
    @Size(min = 1, max = 5, message = "归属平台不合法")
    private Integer platform;

    /**
     * 是否可见
     */
    @NotNull(message = "是否可见不能为空")
    private Boolean show;

    /** api Ids */
    private Set<Long> apiIds;

}
