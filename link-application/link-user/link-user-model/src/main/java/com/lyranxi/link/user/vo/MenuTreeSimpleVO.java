package com.lyranxi.link.user.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lyranxi.link.common.annotation.TreeNode;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * @author ranxi
 * @date 2025-04-18 15:49
 */
@Data
@NoArgsConstructor
@SuperBuilder
public class MenuTreeSimpleVO implements TreeNode<Long>, Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @JsonIgnore
    private Long id;

    /**
     * 父级ID（一级菜单pid=0）
     */
    @JsonIgnore
    private Long pid;

    /**
     * 名称
     */
    private String name;

    /**
     * 路径
     */
    private String url;

    /**
     * icon
     */
    private String icon;

    /**
     * 类型(1:目录/2:菜单/3:按钮)
     */
    private Integer type;

    /**
     * 排序
     */
    private Integer sort;

    private Set<MenuTreeSimpleVO> children;


    @Override
    public void addChild(TreeNode<Long> treeNode) {
        if (this.children == null) {
            this.children = new HashSet<>();
        }
        this.children.add((MenuTreeSimpleVO) treeNode);
    }

    @JsonIgnore
    @Override
    public boolean isRoot() {
        return this.pid == 0;
    }
}
