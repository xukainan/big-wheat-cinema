package com.wheat.cinema.service;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.wheat.cinema.dao.crawler.CrawlerConfig;
import com.wheat.cinema.dao.crawler.CrawlerConfigDetail;
import com.wheat.cinema.dao.crawler.CrawlerConfigMapper;
import com.wheat.cinema.dao.crawler.HtmlUnitHelper;
import com.wheat.cinema.inner.dto.CrawlerDetailDTO;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
            List<CrawlerDetailDTO> crawlerDetailDTOS = new ArrayList<>();
            crawlerConfigDetails.stream().filter(item -> item.getConfigTypeId() <= 4).forEach(item -> {
                //爬第一页
                Document firstListDoc = getDocumentByUrl(item.getConfigUrl(), webClient);
                if(firstListDoc != null){
                    crawlerDetailDTOS.addAll(crawlerListPage(firstListDoc, item.getConfigXpath(), crawlerConfigDetails));
                    //总页数
                    int pageCount = 1;
                    Optional<CrawlerConfigDetail> pageCountConfigDetails = crawlerConfigDetails.stream().filter(item1 -> item1.getConfigTypeId() == 7).findFirst();
                    Elements pageCountElements = firstListDoc.selectXpath(pageCountConfigDetails.get().getConfigXpath());
                    if(pageCountElements != null && pageCountElements.size() > 0) {
                        Element lastPageElement = pageCountElements.get(pageCountElements.size() - 1);
                        Element lastPageLinkElement = lastPageElement.selectXpath(pageCountConfigDetails.get().getConfigSuffix()).first();
                        String lastPageLink = lastPageLinkElement.attr(pageCountConfigDetails.get().getConfigAttribute());
                        Pattern compile = Pattern.compile(pageCountConfigDetails.get().getConfigRegex());
                        Matcher matcher = compile.matcher(lastPageLink);
                        pageCount = Integer.valueOf(matcher.group());
                    }
                    //爬后续页
                    for (int nextPage = 2; nextPage <= pageCount; nextPage++) {
                        String nextUrl = item.getConfigUrl() + item.getConfigSuffix().replace("%d", String.valueOf(nextPage));
                        Document nextListDoc = getDocumentByUrl(nextUrl, webClient);
                        crawlerDetailDTOS.addAll(crawlerListPage(nextListDoc, item.getConfigXpath(), crawlerConfigDetails));
                    }
                }
            });
            //爬详情页
            crawlerDetailDTOS.stream().forEach(crawlerDetailDTO -> {
                String detailPage = crawlerConfig.getCrawlerIndexUrl() + crawlerDetailDTO.getDetailLink();
                Document detailPageDoc = getDocumentByUrl(detailPage, webClient);
                crawlerDetailPage(detailPageDoc, crawlerConfigDetails, crawlerDetailDTO);
            });
        });
    }

    private void crawlerDetailPage(Document detailPageDoc, List<CrawlerConfigDetail> crawlerConfigDetails, CrawlerDetailDTO crawlerDetailDTO) {

    }

    private List<CrawlerDetailDTO> crawlerListPage(Document firstListDoc, String configXpath,
                                 List<CrawlerConfigDetail> crawlerConfigDetails) {
        //列表页视频
        Elements videoElements = firstListDoc.selectXpath(configXpath);
        List<CrawlerDetailDTO> crawlerDetailDTOS = new ArrayList<>(videoElements.size());
        videoElements.stream().forEach(video -> {
            CrawlerDetailDTO detailDTO = new CrawlerDetailDTO();
            //视频图片的角标 如HD、正片等
            Optional<CrawlerConfigDetail> cornerConfigDetail =
                    crawlerConfigDetails.stream().filter(item -> item.getConfigTypeId() == 5).findFirst();
            Element cornerElement = video.selectXpath(cornerConfigDetail.get().getConfigXpath()).first();
            String cornerAttribute = cornerElement.attr(cornerConfigDetail.get().getConfigAttribute());
            //视频详情页的链接
            Optional<CrawlerConfigDetail> detailLinkConfigDetail =
                    crawlerConfigDetails.stream().filter(item -> item.getConfigTypeId() == 6).findFirst();
            Element detailLinkElement = video.selectXpath(detailLinkConfigDetail.get().getConfigXpath()).first();
            String detailLink = detailLinkElement.attr(detailLinkConfigDetail.get().getConfigAttribute());
            detailDTO.setCorner(cornerAttribute);
            detailDTO.setDetailLink(detailLink);
            crawlerDetailDTOS.add(detailDTO);
        });
        return crawlerDetailDTOS;
    }

    Document getDocumentByUrl(String url, WebClient webClient) {
        HtmlPage page = null;
        try {
            page = webClient.getPage(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(page != null) {
            return Jsoup.parse(page.asXml());
        }
        return null;
    }
}
