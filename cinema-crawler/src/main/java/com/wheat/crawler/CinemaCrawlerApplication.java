package com.wheat.crawler;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableDubbo(scanBasePackages = "com.wheat.cinema.dao.crawler.provider")
@SpringBootApplication(scanBasePackages = {"com.wheat.crawler", "com.wheat.cinema"})
public class CinemaCrawlerApplication {

    public static void main(String[] args) {
        SpringApplication.run(CinemaCrawlerApplication.class, args);
    }

}
