/**
 * 
 */
package com.ma.bi.webcralwer.processors;

import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;

/**
 * @author vison
 *
 */
public class LanecrawfordPageProcessorTest {


	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		// --- test class ---
		LanecrawfordPageProcessor pp = new LanecrawfordPageProcessor();
		
		Spider.create(pp)
        //从"https://github.com/code4craft"开始抓
        .addUrl("http://www.lanecrawford.com.cn/landing/featureArticle.jsp?article=60300014&=shop&p=shop")
        //开启5个线程抓取
        .thread(5)
        //启动爬虫
        .run();
	}

}
