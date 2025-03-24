package com.lyranxi.link.user.service;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.img.ImgUtil;
import cn.hutool.core.io.FileUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;

/**
 * @author ranxi
 * @date 2025-03-20 15:18
 */
public class ImageCompressor {

    public static final List<String> list = CollectionUtil.newArrayList(
            "/Users/ranxi/workspace/pic/金融中心店/招牌精酿",
            "/Users/ranxi/workspace/pic/金融中心店/工业啤酒",
            "/Users/ranxi/workspace/pic/普洱店/招牌精酿",
            "/Users/ranxi/workspace/pic/普洱店/工业啤酒",
            "/Users/ranxi/workspace/pic/昌宁店/招牌精酿",
            "/Users/ranxi/workspace/pic/昌宁店/工业啤酒",
            "/Users/ranxi/workspace/pic/临沧店/招牌精酿",
            "/Users/ranxi/workspace/pic/临沧店/工业啤酒",
            "/Users/ranxi/workspace/pic/保山店/招牌精酿",
            "/Users/ranxi/workspace/pic/保山店/工业啤酒",
            "/Users/ranxi/workspace/pic/楚雄店/招牌精酿",
            "/Users/ranxi/workspace/pic/楚雄店/工业啤酒",
            "/Users/ranxi/workspace/pic/丽江旅游学院店/招牌精酿",
            "/Users/ranxi/workspace/pic/丽江旅游学院店/工业啤酒",
            "/Users/ranxi/workspace/pic/华坪店/招牌精酿",
            "/Users/ranxi/workspace/pic/华坪店/工业啤酒",
            "/Users/ranxi/workspace/pic/玉溪珊瑚路店/招牌精酿",
            "/Users/ranxi/workspace/pic/玉溪珊瑚路店/工业啤酒",
            "/Users/ranxi/workspace/pic/开远店/招牌精酿",
            "/Users/ranxi/workspace/pic/开远店/工业啤酒",
            "/Users/ranxi/workspace/pic/芒市店/招牌精酿",
            "/Users/ranxi/workspace/pic/芒市店/工业啤酒",
            "/Users/ranxi/workspace/pic/瑞丽店/招牌精酿",
            "/Users/ranxi/workspace/pic/瑞丽店/工业啤酒",
            "/Users/ranxi/workspace/pic/弥勒店/招牌精酿",
            "/Users/ranxi/workspace/pic/弥勒店/工业啤酒",
            "/Users/ranxi/workspace/pic/腾冲店/招牌精酿",
            "/Users/ranxi/workspace/pic/腾冲店/工业啤酒",
            "/Users/ranxi/workspace/pic/凤庆店/招牌精酿",
            "/Users/ranxi/workspace/pic/凤庆店/工业啤酒",
            "/Users/ranxi/workspace/pic/丽江店/招牌精酿",
            "/Users/ranxi/workspace/pic/丽江店/工业啤酒",
            "/Users/ranxi/workspace/pic/阡帝广南店/招牌精酿",
            "/Users/ranxi/workspace/pic/阡帝广南店/工业啤酒",
            "/Users/ranxi/workspace/pic/耿马店/招牌精酿",
            "/Users/ranxi/workspace/pic/耿马店/工业啤酒",
            "/Users/ranxi/workspace/pic/怒江店/招牌精酿",
            "/Users/ranxi/workspace/pic/怒江店/工业啤酒",
            "/Users/ranxi/workspace/pic/云县店/招牌精酿",
            "/Users/ranxi/workspace/pic/云县店/工业啤酒",
            "/Users/ranxi/workspace/pic/宣威店/招牌精酿",
            "/Users/ranxi/workspace/pic/宣威店/工业啤酒",
            "/Users/ranxi/workspace/pic/彝良店/招牌精酿",
            "/Users/ranxi/workspace/pic/彝良店/工业啤酒",
            "/Users/ranxi/workspace/pic/建水店/招牌精酿",
            "/Users/ranxi/workspace/pic/建水店/工业啤酒",
            "/Users/ranxi/workspace/pic/蒙自店/招牌精酿",
            "/Users/ranxi/workspace/pic/蒙自店/工业啤酒",
            "/Users/ranxi/workspace/pic/镇雄店/招牌精酿",
            "/Users/ranxi/workspace/pic/镇雄店/工业啤酒",
            "/Users/ranxi/workspace/pic/文山店/招牌精酿",
            "/Users/ranxi/workspace/pic/文山店/工业啤酒",
            "/Users/ranxi/workspace/pic/玉溪店/招牌精酿",
            "/Users/ranxi/workspace/pic/玉溪店/工业啤酒",
            "/Users/ranxi/workspace/pic/大理店/招牌精酿",
            "/Users/ranxi/workspace/pic/大理店/工业啤酒",
            "/Users/ranxi/workspace/pic/曲靖店/招牌精酿",
            "/Users/ranxi/workspace/pic/曲靖店/工业啤酒",
            "/Users/ranxi/workspace/pic/版纳店/招牌精酿",
            "/Users/ranxi/workspace/pic/版纳店/工业啤酒");

    public static void main(String[] args) throws IOException {
        String s = "/Users/ranxi/workspace/开远";
        File[] files = FileUtil.ls(s + "/");
        for (File file : files) {
            if (file.getName().contains("json") || file.getName().contains(".DS_Store")
            || file.getName().contains("compressor")){
                continue;
            }
            System.out.println(file.getCanonicalPath() + ":" + file.length());


            float quality = 0.5f;
            if (file.length() < (1024 * 1024)){
                quality = 1f;
            }else if (file.length() > (1024 * 104) && file.length() < (1024 * 1024 * 2)) {
                quality = 0.5f;
            }else if (file.length() > (1024 * 104 * 2) && file.length() < (1024 * 1024 * 3)) {
                quality = 0.3f;
            }else {
                quality = 0.2f;
            }


            // /Users/ranxi/workspace/pic/普洱店/工业啤酒/百威(小)2打套餐.jpeg
            String canonicalPath = file.getCanonicalPath();
            String destPath = "/Users/ranxi/workspace/开远/compressor/"+file.getName();
            String srcPath = file.getCanonicalPath();
            // 压缩后图片保存路径
            ImgUtil.compress(new File(srcPath), new File(destPath), quality);
            System.out.println(destPath);
        }
//        for (String s : list) {
//            File[] files = FileUtil.ls(s + "/");
//            for (File file : files) {
//                if (file.getName().contains("json") || file.getName().contains(".DS_Store")){
//                    continue;
//                }
//                System.out.println(file.getCanonicalPath() + ":" + file.length());
//
//
//                float quality = 0.5f;
//                if (file.length() < (1024 * 1024)){
//                    quality = 1f;
//                }else if (file.length() > (1024 * 104) && file.length() < (1024 * 1024 * 2)) {
//                    quality = 0.5f;
//                }else if (file.length() > (1024 * 104 * 2) && file.length() < (1024 * 1024 * 3)) {
//                    quality = 0.3f;
//                }else {
//                    quality = 0.2f;
//                }
//
//
//                // /Users/ranxi/workspace/pic/普洱店/工业啤酒/百威(小)2打套餐.jpeg
//                String canonicalPath = file.getCanonicalPath();
//                String destPath = canonicalPath.substring(0,26) + "/compressor" + canonicalPath.substring(26);
//                String srcPath = file.getCanonicalPath();
//                // 压缩后图片保存路径
//                ImgUtil.compress(new File(srcPath), new File(destPath), quality);
//                System.out.println(destPath);
//            }
//        }


    }



}
