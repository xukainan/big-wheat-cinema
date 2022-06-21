package com.wheat.cinema.crawler;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;

public class HtmlUnitHelper {

    private static volatile WebClient webClient;

    /**
     * 初始化Jsoup
     */
    public static WebClient getWebClient() {
        if(webClient == null){
            synchronized (HtmlUnitHelper.class){
                if(webClient == null){
                    try {
                        webClient = new WebClient(BrowserVersion.CHROME);
                        webClient.getOptions().setJavaScriptEnabled(false);
                        webClient.getOptions().setCssEnabled(false);
                        webClient.getOptions().setActiveXNative(false);
                        webClient.getOptions().setThrowExceptionOnScriptError(false);
                        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
                        webClient.getOptions().setRedirectEnabled(true);
                        webClient.getOptions().setTimeout(12 * 1000);
                        webClient.setJavaScriptTimeout(10 * 1000);
                        webClient.getOptions().setUseInsecureSSL(true); //ignore insecure ssl exception
                        webClient.getOptions().setSSLClientProtocols(new String[] { "TLSv1.2","TLSv1.1" });
                        webClient.setAjaxController(new NicelyResynchronizingAjaxController());// 很重要，设置支持AJAX
                        webClient.getCookieManager().setCookiesEnabled(false);
                        // doc = Jsoup.connect(url)
                        // .header("Accept-Encoding", "gzip, deflate")
                        // .userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:23.0) Gecko/20100101
                        // Firefox/23.0")
                        // .maxBodySize(0)
                        // .timeout(600000)
                        // .get();
                    } catch (Exception e) {
                        e.printStackTrace();
                        return null;
                    }
                }
                return webClient;

            }
        }
        return webClient;
    }
}
