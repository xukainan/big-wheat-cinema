package com.wheat.cinema.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wheat.cinema.dao.crawler.CrawlerConfig;
import com.wheat.cinema.dao.crawler.CrawlerConfigMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CrawlerConfigServiceImpl extends ServiceImpl<CrawlerConfigMapper, CrawlerConfig> implements ICrawlerConfigService {

    @Override
    public List<CrawlerConfig> listActiveConfig() {
        LambdaQueryWrapper<CrawlerConfig> wrapper = new LambdaQueryWrapper();
        wrapper.eq(CrawlerConfig::getCrawlerStatus, 1);
        return this.list(wrapper);
    }
}
