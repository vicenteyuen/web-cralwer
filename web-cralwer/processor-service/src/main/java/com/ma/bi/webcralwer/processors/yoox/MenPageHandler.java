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
public class MenPageHandler implements PageHandler {

	private static Logger logger = LoggerFactory.getLogger( MenPageHandler.class );		
	
	/**
	 * 
	 */
	public MenPageHandler() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see com.ma.bi.webcralwer.handle.PageHandler#handle(us.codecraft.webmagic.Page)
	 */
	@Override
	public void handle(Page page) {
		// TODO Auto-generated method stub
		
		Document doc = page.getHtml().getDocument();
		
		Elements elems = doc.select( "a.topFiltersTracking" );
		
		Iterator<Element> iter = elems.iterator();
		while (iter.hasNext()) {
			Element ele = iter.next();
			String urlLink = ele.attr("href");

			// ---- get the infomation ---
			
			String dataType = ele.attr("data-type");
			Request req = new Request(urlLink);
			
			// --- handle brand designer
			if ( "topDesigners".equalsIgnoreCase(dataType) ) {
				
			}
			// ---- handle for categories ----
			else if ("topCategories".equalsIgnoreCase(dataType) || "categories".equalsIgnoreCase(dataType) ) {
				
			}

			
			// --- add the category and brand --
			page.addTargetRequest(req);
			
			if (logger.isDebugEnabled()) {
				logger.debug("Add Request : " + urlLink);
			}
			
		}
		
	}

}
