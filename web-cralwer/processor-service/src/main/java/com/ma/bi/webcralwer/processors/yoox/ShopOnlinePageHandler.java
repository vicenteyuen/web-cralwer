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
import us.codecraft.webmagic.Request;

import com.ma.bi.webcralwer.handle.PageHandler;

/**
 * @author ruanweibiao
 *
 */
public class ShopOnlinePageHandler implements PageHandler {

	private static Logger logger = LoggerFactory.getLogger( ShopOnlinePageHandler.class );	
	
	/**
	 * 
	 */
	public ShopOnlinePageHandler() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see com.ma.bi.webcralwer.handle.PageHandler#handle(us.codecraft.webmagic.Page)
	 */
	@Override
	public void handle(Page page) {
		// TODO Auto-generated method stub
		
		Document doc = page.getHtml().getDocument();

		Elements elems = doc.select( "div.itemContainer" );
		Iterator<Element> iter = elems.iterator();
		while (iter.hasNext()) {
			Element elem = iter.next();
			
			Elements linkElems = elem.select("a[href].itemlink");
			for (Iterator<Element> subLinkElemsIter = linkElems.iterator(); subLinkElemsIter.hasNext() ; ) {
				Element subLink = subLinkElemsIter.next();
				
				String href = subLink.attr("href");
				
				page.addTargetRequest( new Request(href) );
				
				if (logger.isDebugEnabled()) {
					logger.debug("Add Request : " + href);
				}
			}

		}
		
	}

}
