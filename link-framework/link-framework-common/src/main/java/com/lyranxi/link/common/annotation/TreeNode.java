package com.lyranxi.link.common.annotation;

/**
 * @author ranxi
 * @date 2025-04-18 15:51
 */
public interface TreeNode<T> {

    T getId();

    T getPid();

    void addChild(TreeNode<T> children);

    boolean isRoot();
}
