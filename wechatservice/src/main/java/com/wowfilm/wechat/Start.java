package com.wowfilm.wechat;

import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.core.SpringVersion;

/**
 * Created by wen on 2016/7/11.
 */

@SpringBootApplication
public class Start extends SpringBootServletInitializer {
    private static Logger logger = Logger.getLogger(Start.class.getName());

    public static void main(String[] args) {
        logger.info("SPRING VERSION: " + SpringVersion.getVersion());
        SpringApplication.run(Start.class, args);
    }
}
