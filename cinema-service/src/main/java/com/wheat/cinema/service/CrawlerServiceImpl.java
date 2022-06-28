package com.wheat.cinema.service;

import com.gargoylesoftware.htmlunit.WebClient;
import com.wheat.cinema.dao.crawler.CrawlerConfig;
import com.wheat.cinema.dao.crawler.CrawlerConfigDetail;
import com.wheat.cinema.dao.crawler.CrawlerConfigMapper;
import com.wheat.cinema.dao.crawler.HtmlUnitHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;

import java.time.LocalDateTime;
import java.util.List;

@EnableScheduling
public class CrawlerServiceImpl implements ICrawlerService, SchedulingConfigurer {


    @Autowired
    ICrawlerConfigService crawlerConfigService;
    
    @Autowired
    ICrawlerConfigDetailService crawlerConfigDetailService;

    String cron = "0 0 1,5 * * ? ";

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.addTriggerTask(
                //1.添加任务内容(Runnable)
                () -> System.out.println("执行动态定时任务: " + LocalDateTime.now().toLocalTime()),
                //2.设置执行周期(Trigger)
                triggerContext -> {
                    //返回执行周期(Date)
                    return new CronTrigger(cron).nextExecutionTime(triggerContext);
                }
        );
    }


    @Override
    public void crawlerVideos() {
        WebClient webClient = HtmlUnitHelper.getWebClient();
        List<CrawlerConfig> crawlerConfigs = crawlerConfigService.listActiveConfig();
        crawlerConfigs.stream().forEach(crawlerConfig -> {
            List<CrawlerConfigDetail> crawlerConfigDetails = crawlerConfigDetailService.listById(crawlerConfig.getId());

        });
    }
}
