package com.lyranxi.link.user.service;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.http.HttpDownloader;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.alibaba.nacos.shaded.com.google.gson.Gson;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ranxi
 * @date 2025-03-20 13:06
 */
public class PicTest {

    public static final List<String> list = CollectionUtil.newArrayList("金融中心店",
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
            "版纳店"
    );

    public static final List<String> brands = CollectionUtil.newArrayList("招牌精酿","工业啤酒");


    public static void main(String[] args) throws FileNotFoundException {
        String path = "/Users/ranxi/workspace/开远/";
        String str = readFile(path + "json.json");

//        String str = "[{\"name\":\"代号9翠羽铝瓶1打套餐\",\"picture\":\"https://new-evideo-lyjb.oss-cn-shenzhen.aliyuncs.com/marketGood/java.text.SimpleDateFormat@cdc86c93/7ba090f5a1e54e18a8db7140b33c157a.jpeg\"},{\"name\":\"代号9小麦啤1打套餐\",\"picture\":\"https://new-evideo-lyjb.oss-cn-shenzhen.aliyuncs.com/marketGood/java.text.SimpleDateFormat@cdc86c93/e281ed8317f64f5496e0e51bd4294e47.jpeg\"},{\"name\":\"代号9小麦啤2打套餐\",\"picture\":\"https://new-evideo-lyjb.oss-cn-shenzhen.aliyuncs.com/marketGood/java.text.SimpleDateFormat@cdc86c93/d7ccbec180214112b359fbd38da90dc6.jpeg\"},{\"name\":\"代号9芒果海盐1打套餐\",\"picture\":\"https://new-evideo-lyjb.oss-cn-shenzhen.aliyuncs.com/marketGood/java.text.SimpleDateFormat@cdc86c93/8cef321dbbab4410bf8049dbd02bce1a.jpeg\"},{\"name\":\"代号9芒果海盐2打套餐\",\"picture\":\"https://new-evideo-lyjb.oss-cn-shenzhen.aliyuncs.com/marketGood/java.text.SimpleDateFormat@cdc86c93/46a9422e5d424bd6ab89725a11462c7d.jpeg\"},{\"name\":\"代号9桃园樱花1打套餐\",\"picture\":\"https://new-evideo-lyjb.oss-cn-shenzhen.aliyuncs.com/marketGood/java.text.SimpleDateFormat@cdc86c93/4fe92fd114a744a8bf3fa24fa94e3d35.jpeg\"},{\"name\":\"代号9桃园樱花2打套餐\",\"picture\":\"https://new-evideo-lyjb.oss-cn-shenzhen.aliyuncs.com/marketGood/java.text.SimpleDateFormat@cdc86c93/5645b1b8647a470691dc39aa50435f1c.jpeg\"},{\"name\":\"代号9荔枝玫瑰1打套餐\",\"picture\":\"https://new-evideo-lyjb.oss-cn-shenzhen.aliyuncs.com/marketGood/java.text.SimpleDateFormat@cdc86c93/a6dbc654ef3a4753bfcb87be74d6a78b.jpeg\"},{\"name\":\"代号9荔枝玫瑰2打套餐\",\"picture\":\"https://new-evideo-lyjb.oss-cn-shenzhen.aliyuncs.com/marketGood/java.text.SimpleDateFormat@cdc86c93/4b116486acbf45efbcc08e6f1ba1dc15.jpeg\"},{\"name\":\"鲜酿原味2L套餐\",\"picture\":\"https://new-evideo-lyjb.oss-cn-shenzhen.aliyuncs.com/marketGood/java.text.SimpleDateFormat@cdc86c93/68d02a3b579a4eb087f51178e9079d54.jpeg\"},{\"name\":\"鲜酿原味3L套餐\",\"picture\":\"https://new-evideo-lyjb.oss-cn-shenzhen.aliyuncs.com/marketGood/java.text.SimpleDateFormat@cdc86c93/d77d2a762835499a85fbc44a77271e92.jpeg\"},{\"name\":\"鲜酿原味5L套餐\",\"picture\":\"https://new-evideo-lyjb.oss-cn-shenzhen.aliyuncs.com/marketGood/java.text.SimpleDateFormat@cdc86c93/6fd132ef4e8340a89f24004f33c396fa.jpeg\"},{\"name\":\"鲜酿原味10L套餐\",\"picture\":\"https://new-evideo-lyjb.oss-cn-shenzhen.aliyuncs.com/marketGood/java.text.SimpleDateFormat@cdc86c93/7f09f91f049d4fed80dc62ae31cd8d12.jpeg\"},{\"name\":\"鲜酿原味20L套餐\",\"picture\":\"https://new-evideo-lyjb.oss-cn-shenzhen.aliyuncs.com/marketGood/java.text.SimpleDateFormat@cdc86c93/50c2dc5d8be74c5dba751e30f270d6f0.jpeg\"},{\"name\":\"鲜酿果味2L套餐(4选1)\",\"picture\":\"https://new-evideo-lyjb.oss-cn-shenzhen.aliyuncs.com/marketGood/java.text.SimpleDateFormat@cdc86c93/a540d2e335b043af9b7d4d8ee52a11f1.jpeg\"},{\"name\":\"鲜酿果味3L套餐(4选1)\",\"picture\":\"https://new-evideo-lyjb.oss-cn-shenzhen.aliyuncs.com/marketGood/java.text.SimpleDateFormat@cdc86c93/cbffd1f5082e48b8a215fd94e136dbaf.jpeg\"},{\"name\":\"鲜酿果味5L套餐(4选1)\",\"picture\":\"https://new-evideo-lyjb.oss-cn-shenzhen.aliyuncs.com/marketGood/java.text.SimpleDateFormat@cdc86c93/2607cf25633947ca9a97d665f910e20c.jpeg\"},{\"name\":\"鲜酿果味10L套餐(4选1)\",\"picture\":\"https://new-evideo-lyjb.oss-cn-shenzhen.aliyuncs.com/marketGood/java.text.SimpleDateFormat@cdc86c93/c572c4da34ea4d45b2009465501add0a.jpeg\"},{\"name\":\"鲜酿果味20L套餐(4选1)\",\"picture\":\"https://new-evideo-lyjb.oss-cn-shenzhen.aliyuncs.com/marketGood/java.text.SimpleDateFormat@cdc86c93/ef002200874845c089d8762f6a9ecd01.jpeg\"}]";
        List<Pic> list = JSONUtil.toList(str, Pic.class);
        for (Pic pic : list) {
            try {
                URL url = new URL(pic.getPicture());
                InputStream in = url.openStream();
                File file = new File(path + pic.getName() + ".jpeg");
                FileOutputStream fos = new FileOutputStream(file);
                IoUtil.copy(in, fos);
                fos.close();
                in.close();
                System.out.println("Downloaded: " + file.getName());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

//        for (String storeName : list) {
//            for (String brand : brands) {
////                String str = "/Users/ranxi/workspace/pic/"+ storeName +"/" + brand + "/json.json";
//                String path = "/Users/ranxi/workspace/pic/compressor/"+ storeName +"/" + brand + "/";
//                String str = readFile(path + "json.json");
//
////        String str = "[{\"name\":\"代号9翠羽铝瓶1打套餐\",\"picture\":\"https://new-evideo-lyjb.oss-cn-shenzhen.aliyuncs.com/marketGood/java.text.SimpleDateFormat@cdc86c93/7ba090f5a1e54e18a8db7140b33c157a.jpeg\"},{\"name\":\"代号9小麦啤1打套餐\",\"picture\":\"https://new-evideo-lyjb.oss-cn-shenzhen.aliyuncs.com/marketGood/java.text.SimpleDateFormat@cdc86c93/e281ed8317f64f5496e0e51bd4294e47.jpeg\"},{\"name\":\"代号9小麦啤2打套餐\",\"picture\":\"https://new-evideo-lyjb.oss-cn-shenzhen.aliyuncs.com/marketGood/java.text.SimpleDateFormat@cdc86c93/d7ccbec180214112b359fbd38da90dc6.jpeg\"},{\"name\":\"代号9芒果海盐1打套餐\",\"picture\":\"https://new-evideo-lyjb.oss-cn-shenzhen.aliyuncs.com/marketGood/java.text.SimpleDateFormat@cdc86c93/8cef321dbbab4410bf8049dbd02bce1a.jpeg\"},{\"name\":\"代号9芒果海盐2打套餐\",\"picture\":\"https://new-evideo-lyjb.oss-cn-shenzhen.aliyuncs.com/marketGood/java.text.SimpleDateFormat@cdc86c93/46a9422e5d424bd6ab89725a11462c7d.jpeg\"},{\"name\":\"代号9桃园樱花1打套餐\",\"picture\":\"https://new-evideo-lyjb.oss-cn-shenzhen.aliyuncs.com/marketGood/java.text.SimpleDateFormat@cdc86c93/4fe92fd114a744a8bf3fa24fa94e3d35.jpeg\"},{\"name\":\"代号9桃园樱花2打套餐\",\"picture\":\"https://new-evideo-lyjb.oss-cn-shenzhen.aliyuncs.com/marketGood/java.text.SimpleDateFormat@cdc86c93/5645b1b8647a470691dc39aa50435f1c.jpeg\"},{\"name\":\"代号9荔枝玫瑰1打套餐\",\"picture\":\"https://new-evideo-lyjb.oss-cn-shenzhen.aliyuncs.com/marketGood/java.text.SimpleDateFormat@cdc86c93/a6dbc654ef3a4753bfcb87be74d6a78b.jpeg\"},{\"name\":\"代号9荔枝玫瑰2打套餐\",\"picture\":\"https://new-evideo-lyjb.oss-cn-shenzhen.aliyuncs.com/marketGood/java.text.SimpleDateFormat@cdc86c93/4b116486acbf45efbcc08e6f1ba1dc15.jpeg\"},{\"name\":\"鲜酿原味2L套餐\",\"picture\":\"https://new-evideo-lyjb.oss-cn-shenzhen.aliyuncs.com/marketGood/java.text.SimpleDateFormat@cdc86c93/68d02a3b579a4eb087f51178e9079d54.jpeg\"},{\"name\":\"鲜酿原味3L套餐\",\"picture\":\"https://new-evideo-lyjb.oss-cn-shenzhen.aliyuncs.com/marketGood/java.text.SimpleDateFormat@cdc86c93/d77d2a762835499a85fbc44a77271e92.jpeg\"},{\"name\":\"鲜酿原味5L套餐\",\"picture\":\"https://new-evideo-lyjb.oss-cn-shenzhen.aliyuncs.com/marketGood/java.text.SimpleDateFormat@cdc86c93/6fd132ef4e8340a89f24004f33c396fa.jpeg\"},{\"name\":\"鲜酿原味10L套餐\",\"picture\":\"https://new-evideo-lyjb.oss-cn-shenzhen.aliyuncs.com/marketGood/java.text.SimpleDateFormat@cdc86c93/7f09f91f049d4fed80dc62ae31cd8d12.jpeg\"},{\"name\":\"鲜酿原味20L套餐\",\"picture\":\"https://new-evideo-lyjb.oss-cn-shenzhen.aliyuncs.com/marketGood/java.text.SimpleDateFormat@cdc86c93/50c2dc5d8be74c5dba751e30f270d6f0.jpeg\"},{\"name\":\"鲜酿果味2L套餐(4选1)\",\"picture\":\"https://new-evideo-lyjb.oss-cn-shenzhen.aliyuncs.com/marketGood/java.text.SimpleDateFormat@cdc86c93/a540d2e335b043af9b7d4d8ee52a11f1.jpeg\"},{\"name\":\"鲜酿果味3L套餐(4选1)\",\"picture\":\"https://new-evideo-lyjb.oss-cn-shenzhen.aliyuncs.com/marketGood/java.text.SimpleDateFormat@cdc86c93/cbffd1f5082e48b8a215fd94e136dbaf.jpeg\"},{\"name\":\"鲜酿果味5L套餐(4选1)\",\"picture\":\"https://new-evideo-lyjb.oss-cn-shenzhen.aliyuncs.com/marketGood/java.text.SimpleDateFormat@cdc86c93/2607cf25633947ca9a97d665f910e20c.jpeg\"},{\"name\":\"鲜酿果味10L套餐(4选1)\",\"picture\":\"https://new-evideo-lyjb.oss-cn-shenzhen.aliyuncs.com/marketGood/java.text.SimpleDateFormat@cdc86c93/c572c4da34ea4d45b2009465501add0a.jpeg\"},{\"name\":\"鲜酿果味20L套餐(4选1)\",\"picture\":\"https://new-evideo-lyjb.oss-cn-shenzhen.aliyuncs.com/marketGood/java.text.SimpleDateFormat@cdc86c93/ef002200874845c089d8762f6a9ecd01.jpeg\"}]";
//                List<Pic> list = JSONUtil.toList(str, Pic.class);
//                for (Pic pic : list) {
//                    try {
//                        URL url = new URL(pic.getPicture());
//                        InputStream in = url.openStream();
//                        File file = new File(path + pic.getName() + ".jpeg");
//                        FileOutputStream fos = new FileOutputStream(file);
//                        IoUtil.copy(in, fos);
//                        fos.close();
//                        in.close();
//                        System.out.println("Downloaded: " + file.getName());
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }


    }


    private static String readFile(String filePath) {
        StringBuilder contentBuilder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                contentBuilder.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return contentBuilder.toString();
    }

//    public static void main(String[] args) {
//        String filePath = "/Users/ranxi/workspace/布车.txt";
//        List<String> sql = new ArrayList<>();
//        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
//            String line;
//            while ((line = br.readLine()) != null) {
//                String str = "update `member_bind_cards` set group_card_id = '7377accd81a24abe8eb03be453c07f96' where id = \"" + line + "\";";
//                sql.add(str);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        FileUtil.writeLines(sql, "/Users/ranxi/workspace/布车.sql", "UTF-8");
//    }


    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Pic {
        private String name;
        private String picture;
    }

}
