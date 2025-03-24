package com.lyranxi.link.user.service;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.io.FileUtil;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

/**
 * @author ranxi
 * @date 2025-03-20 13:53
 */
public class FileTest {

    public static final List<String> list = CollectionUtil.newArrayList("金融中心店",
            "普洱店",
            "昌宁店",
            "临沧店",
            "保山店",
            "楚雄店",
            "丽江旅游学院店",
            "华坪店",
            "玉溪珊瑚路店",
            "开远店",
            "芒市店",
            "瑞丽店",
            "弥勒店",
            "腾冲店",
            "凤庆店",
            "丽江店",
            "阡帝广南店",
            "耿马店",
            "怒江店",
            "云县店",
            "宣威店",
            "彝良店",
            "建水店",
            "蒙自店",
            "镇雄店",
            "文山店",
            "玉溪店",
            "大理店",
            "曲靖店",
            "版纳店");

    public static void main(String[] args) throws IOException {
        List<String> brand = CollectionUtil.newArrayList("招牌精酿","工业啤酒");
        String base = "/Users/ranxi/workspace/pic/compressor/";
        for (String name : list) {
            for (String s : brand) {
                // 门店
//                String firstFilePath = base + name;
//                FileUtil.file(firstFilePath).mkdirs();

                // 分类
                String secondFilePath = base + name + "/" + s;
//                System.out.println(secondFilePath);
                FileUtil.file(secondFilePath).mkdirs();

            }
        }
    }

}
