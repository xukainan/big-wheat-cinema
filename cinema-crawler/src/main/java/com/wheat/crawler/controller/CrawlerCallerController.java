//package com.wheat.crawler.controller;
//
//import com.wheat.cinema.dao.crawler.ICrawlerCaller;
//import org.apache.dubbo.config.annotation.DubboReference;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.Date;
//import java.util.UUID;
//
//@RestController
//public class CrawlerCallerController {
//
//    @Autowired
//    ICrawlerCaller crawlerCaller;
//
//
//    @GetMapping("/test")
//    String test(){
//        String uuid = UUID.randomUUID().toString();
//        Date date = crawlerCaller.testCaller(uuid);
//        return date.toString() + "               " + uuid;
//    }
//}