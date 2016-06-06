/**
 * 
 */
package com.ma.bi.webcralwer.page.yoox;

import java.util.Iterator;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;

import com.ma.bi.webcralwer.PageHandler;
import com.ma.bi.webcralwer.ProcessorContext;
import com.ma.bi.webcralwer.State;

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
	
	
	private ProcessorContext context;


	@Override
	public void setProcessorContext(ProcessorContext procContext) {
		// TODO Auto-generated method stub
		this.context = procContext;
	}



	/* (non-Javadoc)
	 * @see com.ma.bi.webcralwer.handle.PageHandler#handle(us.codecraft.webmagic.Page)
	 */
	@Override
	public void handle(Page page) {
		// TODO Auto-generated method stub
		
		Document doc = page.getHtml().getDocument();

		State state = context.getState();
		
		state.open();
		
		Elements elems = doc.select( "div.itemContainer" );
		Iterator<Element> iter = elems.iterator();
		while (iter.hasNext()) {
			Element elem = iter.next();
			
			Elements linkElems = elem.select("a[href].itemlink");
			for (Iterator<Element> subLinkElemsIter = linkElems.iterator(); subLinkElemsIter.hasNext() ; ) {
				Element subLink = subLinkElemsIter.next();
				
				String href = subLink.attr("href");
				state.update(href.getBytes(), State.PENDING);
				page.addTargetRequest( new Request(href) );
				
				if (logger.isDebugEnabled()) {
					logger.debug("Add Request : " + href);
				}
			}

		}
		
		state.commit();
		
		state.close();
	}

}
