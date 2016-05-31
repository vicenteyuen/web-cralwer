/**
 * 
 */
package com.ma.bi.webcralwer.processors.yoox;

import java.util.Iterator;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
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
		//Selectable  st = page.getHtml().xpath("//a[@class='topFiltersTracking']");
		Document doc = page.getHtml().getDocument();
		
		Elements elems = doc.select( "a.topFiltersTracking" );
		
		Iterator<Element> iter = elems.iterator();
		while (iter.hasNext()) {
			Element ele = iter.next();
			String urlLink = ele.attr("href");
			System.out.println(ele);
			// ---- get the infomation ---
			
			String dataType = ele.attr("data-type");
			
			// --- handle brand designer
			if ( "topDesigners".equalsIgnoreCase(dataType) ) {
				
			}
			// ---- handle for categories ----
			else if ("topCategories".equalsIgnoreCase(dataType) || "categories".equalsIgnoreCase(dataType) ) {
				
			}
			
			// --- add the category and brand --
			page.addTargetRequest(new Request(urlLink));

		}





		


	}

}
