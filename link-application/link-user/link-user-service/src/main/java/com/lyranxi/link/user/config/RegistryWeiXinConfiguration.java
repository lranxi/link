package com.lyranxi.link.user.config;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.impl.WxMaServiceImpl;
import cn.binarywang.wx.miniapp.config.impl.WxMaRedissonConfigImpl;
import com.lyranxi.link.redis.redisson.RedissonFactory;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

/**
 * @author ranxi
 * @date 2025-05-06 16:05
 */
@Slf4j
@Configuration
@EnableConfigurationProperties(WeixinMiniAppProperties.class)
public class RegistryWeiXinConfiguration {

    private final WeixinMiniAppProperties weixinMiniAppProperties;

    public RegistryWeiXinConfiguration(WeixinMiniAppProperties weixinMiniAppProperties) {
        this.weixinMiniAppProperties = weixinMiniAppProperties;
    }

    @Bean
    public WxMaService wxMaService() {
        WxMaService service = new WxMaServiceImpl();
        if (Objects.isNull(weixinMiniAppProperties)) {
            throw new RuntimeException("微信小程序未配置");
        }
        WxMaRedissonConfigImpl config = new WxMaRedissonConfigImpl(RedissonFactory.getRedissonClient());
        config.setAppid(weixinMiniAppProperties.getAppId());
        config.setSecret(weixinMiniAppProperties.getSecret());
        config.setToken(weixinMiniAppProperties.getToken());
        config.setAesKey(weixinMiniAppProperties.getAesKey());
        config.setMsgDataFormat(weixinMiniAppProperties.getMsgDataFormat());
        service.addConfig(weixinMiniAppProperties.getAppId(), config);
        return service;
    }

}
