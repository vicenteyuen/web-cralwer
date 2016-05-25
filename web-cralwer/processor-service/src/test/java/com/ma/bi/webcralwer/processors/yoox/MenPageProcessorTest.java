/**
 * 
 */
package com.ma.bi.webcralwer.processors.yoox;

import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;

/**
 * @author vison
 *
 */
public class MenPageProcessorTest {


	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		// --- test class ---
		YooxMenPageProcessor pp = new YooxMenPageProcessor();
		
		Spider.create(pp)
        //从"https://github.com/code4craft"开始抓
        .addUrl("http://www.yoox.cn/cn/%E7%94%B7%E5%A3%AB")
        //开启5个线程抓取
        .thread(5)
        //启动爬虫
        .run();
	}

}
