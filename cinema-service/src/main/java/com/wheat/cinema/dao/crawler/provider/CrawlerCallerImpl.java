package com.wheat.cinema.dao.crawler.provider;

import com.wheat.cinema.dao.crawler.ICrawlerCaller;
import org.apache.dubbo.config.annotation.DubboService;

import java.util.Date;

@DubboService
public class CrawlerCallerImpl implements ICrawlerCaller {

    @Override
    public Date testCaller(String uuid) {
        System.out.println(uuid);
        return new Date();
    }

}
