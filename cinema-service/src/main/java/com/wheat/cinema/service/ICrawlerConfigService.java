package com.wheat.cinema.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wheat.cinema.dao.crawler.CrawlerConfig;

import java.util.List;

public interface ICrawlerConfigService extends IService<CrawlerConfig> {

    List<CrawlerConfig> listActiveConfig();
}
