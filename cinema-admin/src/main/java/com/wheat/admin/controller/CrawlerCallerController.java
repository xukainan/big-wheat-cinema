package com.wheat.admin.controller;

import com.wheat.cinema.crawler.ICrawlerCaller;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.UUID;

@RestController
public class CrawlerCallerController {

    @DubboReference(check = false)
    ICrawlerCaller crawlerCaller;


    @GetMapping("/test")
    String test(){
        String uuid = UUID.randomUUID().toString();
        Date date = crawlerCaller.testCaller(uuid);
        return date.toString() + "               " + uuid;
    }
}
