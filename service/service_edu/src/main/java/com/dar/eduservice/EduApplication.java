package com.dar.eduservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author :wx
 * @description :
 * @create :2022-01-21 14:25:00
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
//设置包的扫描规则,为了加载service_base配置类
@ComponentScan(basePackages = {"com.dar"})
public class EduApplication {
    public static void main(String[] args) {
        SpringApplication.run(EduApplication.class, args);
    }
}
