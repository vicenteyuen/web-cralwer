/**
 * 
 */
package com.ma.bi.webcralwer.processors.yoox;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * @author vison
 *
 */
public class YooxMenPageProcessor implements PageProcessor {

	private Site site = Site.me().setRetryTimes(3).setSleepTime(1000);		
	
	/* (non-Javadoc)
	 * @see us.codecraft.webmagic.processor.PageProcessor#getSite()
	 */
	@Override
	public Site getSite() {
		// TODO Auto-generated method stub
		return site;
	}

	/* (non-Javadoc)
	 * @see us.codecraft.webmagic.processor.PageProcessor#process(us.codecraft.webmagic.Page)
	 */
	@Override
	public void process(Page page) {
		// TODO Auto-generated method stub
		System.out.println(page.getHtml());

	}

}
