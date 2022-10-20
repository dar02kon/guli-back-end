package com.dar.vod.config;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author :wx
 * @description :
 * @create :2022-10-01 19:11:00
 */
@Component
public class VodConfig implements InitializingBean {
    @Value("${polyv.vod.file.writetoken}")
    private String writeToken;
    @Value("${polyv.vod.file.userId}")
    private String userId;
    @Value("${polyv.vod.file.secretkey}")
    private String secretKey;

    public static String WRITE_TOKEN;
    public static String USER_ID;
    public static String SECRET_KEY;

    @Override
    public void afterPropertiesSet() {
        WRITE_TOKEN = this.writeToken;
        USER_ID = this.userId;
        SECRET_KEY = this.secretKey;
    }


}
