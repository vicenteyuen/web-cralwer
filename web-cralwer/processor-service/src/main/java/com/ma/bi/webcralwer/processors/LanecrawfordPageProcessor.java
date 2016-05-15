package com.ma.bi.webcralwer.processors;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Selectable;

public class LanecrawfordPageProcessor implements PageProcessor {

	
	private Site site = Site.me().setRetryTimes(3).setSleepTime(1000);	
	
	@Override
	public Site getSite() {
		// TODO Auto-generated method stub
		return site;
	}

	@Override
	public void process(Page page) {
		// TODO Auto-generated method stub
		Selectable  st = page.getHtml().xpath("//li[@class='product-item']");
		System.out.println( st.all() );

	}

}
