package com.wowfilm.client;

import feign.Feign;
import feign.Logger;
import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;


/**
 * 获取封装的api接口工厂
 * 传入服务地址
 * @author warden
 * @version v1.0
 * @date Created on 2016-07-23 00:47:14
 */
public class ApiFactory {
    private String serverRoot;
    private Decoder decoder;
    private Encoder encoder;

    private ApiFactory(String path) {
        this.serverRoot = path;
        decoder = new GsonDecoder();
        encoder = new GsonEncoder();
    }

    public static ApiFactory connect(String serverPath) {
        ApiFactory factory = new ApiFactory(serverPath);
        return factory;
    }

    public <T> T api(Class<T> apiType) {
        return Feign.builder()
                .decoder(decoder)
                .encoder(encoder)
                .logger(new Logger.ErrorLogger())
                .logLevel(Logger.Level.BASIC)
                .target(apiType, serverRoot);
    }
}
