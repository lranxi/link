package com.lyranxi.link.common.util.tree;

import com.lyranxi.link.common.annotation.TreeNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ranxi
 * @date 2025-04-18 16:08
 */
public class TreeTransformer {

    private TreeTransformer() {
    }


    public static <E extends TreeNode<?>> List<E> toTree(List<E> list) {
        Map<Object, TreeNode<?>> map = new HashMap<>(list.size());
        List<E> roots = new ArrayList<>();

        for (E node : list) {
            map.put(node.getId(), node);
            if (node.isRoot()) {
                roots.add(node);
            }
        }

        for (E node : list) {
            TreeNode<?> p = map.get(node.getPid());
            if (p != null) {
                p.addChild((TreeNode) node);
            }
        }
        return roots;
    }

}
