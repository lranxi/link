package com.lyranxi.link.common.util.uuid;

import com.fasterxml.uuid.Generators;

import java.util.UUID;

/**
 * uuid 工具类
 */
public class UuidUtil {
    private static final TimeBasedUUIDGenerator TIME_BASED_UUID_GENERATOR = new TimeBasedUUIDGenerator();
    /**
     * UUID经过Base58压缩, 由数字和大小写字母构成, 区分大小写
     * @return 返回使用Base58压缩后的UUID字串，长度22
     */
    public static String uuid22(){
        return Base58.uuid22();
    }
    
    /**
     * 普通uuid字串，移除"-", 由数字和字母构成
     * @return 无中横线32位uuid字符串
     */
    public static String uuid32(){
        return UUID.randomUUID().toString().replace("-","");
    }
    
    /**
     * uuid字符串,使用base64编码, 由数字+字母+符号构成
     * @return 长度20位的uuid字符串,参考自Elasticsearch的_id生成方法
     */
    public static String uuid20(){
        return TIME_BASED_UUID_GENERATOR.getBase64UUID();
    }

    /**
     * uuid字符串,使用uuid v7
     */
    public static String uuid(){
        return Generators.timeBasedGenerator().generate().toString();
    }

    
}
