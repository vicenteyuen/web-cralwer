/**
 * 
 */
package com.ma.bi.webcralwer.page.yoox;

import java.util.Iterator;
import java.util.concurrent.locks.Lock;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import us.codecraft.webmagic.Page;

import com.ma.bi.webcralwer.LastRequestListener;
import com.ma.bi.webcralwer.PageHandler;
import com.ma.bi.webcralwer.ProcessorContext;
import com.ma.bi.webcralwer.State;
import com.ma.bi.webcralwer.TraceAttr;

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
	
	private LastRequestListener lastRequestLis;
	
	public void setLastRequestListener(LastRequestListener listener) {
		this.lastRequestLis = listener;
	}



	/* (non-Javadoc)
	 * @see com.ma.bi.webcralwer.handle.PageHandler#handle(us.codecraft.webmagic.Page)
	 */
	@Override
	public void handle(Page page) {
		// TODO Auto-generated method stub
		State state = context.getState();
		
		Lock lock = context.getLock();		
		byte levelPosi = 0;
		byte stateValue = State.PENDING;
		try {
			lock.lock();
			
			state.open();	
			byte[] values = state.get(page.getRequest().getUrl().getBytes());
			
			if (null != values) {
				stateValue = values[0];
				levelPosi = values[1];
			}
			state.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			lock.unlock();			
		}
	

		
		
		TraceAttr ta = context.getTraceAttr();
		String currentTAKey = "reqed-link-level-" +levelPosi ;
		String childTAKey = "link-level-"+(levelPosi+1);
		String currentLinkLevelKey = "link-level-" +levelPosi;
		java.io.Serializable taValue = ta.getValue(childTAKey);
		int levelCount = null == taValue ? 0 : Integer.parseInt(taValue.toString());		
		
		
		Document doc = page.getHtml().getDocument();
		

		
		try {
			lock.lock();

			
			Elements elems = doc.select( "div.itemContainer" );
			Iterator<Element> iter = elems.iterator();
			state.open();
			while (iter.hasNext()) {
				Element elem = iter.next();
				
				Elements linkElems = elem.select("a[href].itemlink");
				for (Iterator<Element> subLinkElemsIter = linkElems.iterator(); subLinkElemsIter.hasNext() ; ) {
					Element subLink = subLinkElemsIter.next();
					
					String href = subLink.attr("href");
					byte curLevel = (byte)(levelPosi + 1);
					state.update(href.getBytes(), new byte[]{stateValue , curLevel});
					levelCount = levelCount + 1;
					
					if (logger.isDebugEnabled()) {
						logger.debug("Add Request : " + href);
					}
				}

			}
			
			
			// --- updte current url status ---
			state.update(page.getRequest().getUrl().getBytes(), new byte[]{State.FININED , levelPosi});
			
			state.commit();
			
			state.close();			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}

		
		java.io.Serializable curKeyValue = ta.getValue(currentTAKey);
		int parsedUrlCount = null == curKeyValue ? 0 : Integer.parseInt(curKeyValue.toString());
		parsedUrlCount = parsedUrlCount + 1;
		ta.putValue(currentTAKey, parsedUrlCount);

		ta.putValue(childTAKey, levelCount);
		// --- update handler 
		ta.flushOrSave();
		
		java.io.Serializable clvk = ta.getValue(currentLinkLevelKey);
		int clvkCount = null == clvk ? 0 : Integer.parseInt(clvk.toString());
		
		// --- fire event ---
		if ( parsedUrlCount == clvkCount) {
			if (null != this.lastRequestLis) {
				lastRequestLis.fireHandle();
			}
		}


		
		// --- fire event listener ---

		
	}

}
