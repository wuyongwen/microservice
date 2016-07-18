package com.wowfilm.client;

import com.wowfilm.entity.wechat.Test;
import feign.Feign;
import feign.Logger;
import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;

/**
 * Created by wen on 2016/7/15 16:20.
 */
public class TestApiTest {
    static TestApi connect(){
        Decoder decoder = new GsonDecoder();
        Encoder encoder = new GsonEncoder();
        return Feign.builder()
                .decoder(decoder)
                .encoder(encoder)
                .logger(new Logger.ErrorLogger())
                .logLevel(Logger.Level.BASIC)
                .target(TestApi.class, "http://localhost:8081/wechat");
    }

    public static void main(String[] args) {
        TestApi api = TestApiTest.connect();
        Test t = api.test();
        System.out.println(t.toString());
        t = api.testParam(2,"this is a feign client call");
        System.out.println(t);
        Test t1 = api.testBean(t);
        System.out.println(t1);

    }
}
