package com.litao.train.business.config;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;

@SpringBootApplication
@ComponentScan("com.litao")
@MapperScan("com.litao.train.*.mapper")
@EnableFeignClients("com.litao.train.business.feign")
public class BusinessApplication {

    private static final Logger LOG= LoggerFactory.getLogger(BusinessApplication.class);

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(BusinessApplication.class);
        Environment env = app.run(args).getEnvironment();
        LOG.info("启动成功！！");
        LOG.info("测试地址：\t http://127.0.0.1:{}{}/hello",env.getProperty("server.port"),
                env.getProperty("server.servlet.context-path"));

    }
}