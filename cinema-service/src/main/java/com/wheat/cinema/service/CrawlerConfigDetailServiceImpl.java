package com.wheat.cinema.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wheat.cinema.dao.crawler.CrawlerConfigDetail;
import com.wheat.cinema.dao.crawler.CrawlerConfigDetailMapper;

import java.util.List;

public class CrawlerConfigDetailServiceImpl extends ServiceImpl<CrawlerConfigDetailMapper, CrawlerConfigDetail> implements ICrawlerConfigDetailService {

    @Override
    public List<CrawlerConfigDetail> listById(int configId) {
        LambdaQueryWrapper<CrawlerConfigDetail> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CrawlerConfigDetail::getConfigId, configId)
                .eq(CrawlerConfigDetail::getIsDelete, 0);
        return this.list(wrapper);
    }
}
