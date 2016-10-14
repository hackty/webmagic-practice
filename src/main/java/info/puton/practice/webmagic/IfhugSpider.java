package info.puton.practice.webmagic;

import info.puton.practice.webmagic.model.Website;
import info.puton.practice.webmagic.pipeline.IfhugPipeline;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * Created by taoyang on 2016/10/13.
 */
public class IfhugSpider implements PageProcessor {

    // 部分一：抓取网站的相关配置，包括编码、抓取间隔、重试次数等
    private Site site = Site.me()
            .setRetryTimes(3)
            .setSleepTime(1000);

    public void process(Page page) {
        if(page.getUrl().regex("http://ifhug\\.com/forum.*\\.html").match()){
            //列表页
            String linkAreaXpath = "//*[@id=\"threadlisttableid\"]";
            String linkRegex = "http://ifhug\\.com/thread.*\\.html";
            page.addTargetRequests(page.getHtml().xpath(linkAreaXpath).links().regex(linkRegex).all());
        } else {
            //帖子页
            String url = page.getUrl().get();
            String title = page.getHtml().xpath("/html/head/title/text()").toString();
            String keywords = page.getHtml().xpath("/html/head/meta[@name=\"keywords\"]/@content").toString();
            String description = page.getHtml().xpath("/html/head/meta[@name=\"description\"]/@content").toString();
            String content = page.getHtml().xpath("//*[@id=\"ct\"]/tidyText()").toString();
            content = content
                    .replaceAll("\\n+", " ")
                    .replaceAll("\\r+", " ")
                    .replaceAll("\\t+", " ")
                    .replaceAll("\\s+", " ");

            Website website = new Website();
            website.setUrl(url);
            website.setTitle(title);
            website.setKeywords(keywords);
            website.setDescription(description);
            website.setContent(content);
            page.putField("website",website);
        }

    }

    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        Spider.create(new IfhugSpider())
                .addUrl("http://ifhug.com/forum-66-1.html")
                .addPipeline(new IfhugPipeline())
                .thread(5)
                .run();
    }

}
