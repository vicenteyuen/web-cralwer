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
		YooxMenPageEntryProcessor pp = new YooxMenPageEntryProcessor();
		
		Spider.create(pp)
        //从 男士下的品牌开始获取数据
        //.addUrl("http://www.yoox.cn/cn/%E7%94%B7%E5%A3%AB")
		.addUrl("http://www.yoox.cn/cn/49189587LQ/item#dept=men&sts=SearchResult")
        //开启5个线程抓取
        .thread(5)
        //启动爬虫
        .run();
	}

}
