package com.wheat.cinema.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wheat.cinema.dao.crawler.CrawlerConfigDetail;

import java.util.List;

public interface ICrawlerConfigDetailService extends IService<CrawlerConfigDetail> {

    List<CrawlerConfigDetail> listById(int configId);

}
