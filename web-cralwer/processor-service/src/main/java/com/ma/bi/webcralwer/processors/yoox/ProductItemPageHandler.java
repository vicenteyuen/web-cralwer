/**
 * 
 */
package com.ma.bi.webcralwer.processors.yoox;

import java.util.Iterator;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import us.codecraft.webmagic.Page;

import com.ma.bi.webcralwer.handle.PageHandler;

/**
 * @author ruanweibiao
 *
 */
public class ProductItemPageHandler implements PageHandler {

	private static Logger logger = LoggerFactory.getLogger( ProductItemPageHandler.class );	
	
	/**
	 * 
	 */
	public ProductItemPageHandler() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see com.ma.bi.webcralwer.handle.PageHandler#handle(us.codecraft.webmagic.Page)
	 */
	@Override
	public void handle(Page page) {
		// TODO Auto-generated method stub
		
		Document doc = page.getHtml().getDocument();
		
		Elements brandElems = doc.select("span[itemprop=brand]");
		for (Iterator<Element> elesIter = brandElems.iterator(); elesIter.hasNext();) {
			Element elem = elesIter.next();
			String brandName = elem.text();
		}

		Elements priceElems = doc.select("span[itemprop=price]");
		for (Iterator<Element> elesIter = priceElems.iterator(); elesIter.hasNext();) {
			Element elem = elesIter.next();
			String price = elem.text();
		}
		
		Elements nameElems = doc.select("span[itemprop=name]");
		for (Iterator<Element> elesIter = nameElems.iterator(); elesIter.hasNext();) {
			Element elem = elesIter.next();
			String name = elem.text();
		}
		
		Elements imgElems = doc.select("img.color");
		for (Iterator<Element> elesIter = imgElems.iterator(); elesIter.hasNext();) {
			Element elem = elesIter.next();
			String color = elem.text();
		}
		
		Elements linkElems = doc.select("a.breadcrumbLink");
		for (Iterator<Element> elesIter = linkElems.iterator(); elesIter.hasNext();) {
			Element elem = elesIter.next();
			String linkTxt = elem.text();
			if ("男士".equals( linkTxt ) || "首页".equals( linkTxt )) {
				continue;
			}
		}		

		
	}

}
