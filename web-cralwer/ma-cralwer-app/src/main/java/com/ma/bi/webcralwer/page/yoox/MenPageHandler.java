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
import us.codecraft.webmagic.Request;

import com.ma.bi.webcralwer.LastRequestListener;
import com.ma.bi.webcralwer.PageHandler;
import com.ma.bi.webcralwer.ProcessorContext;
import com.ma.bi.webcralwer.State;
import com.ma.bi.webcralwer.TraceAttr;

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
	
	private ProcessorContext context;


	@Override
	public void setProcessorContext(ProcessorContext procContext) {
		// TODO Auto-generated method stub
		this.context = procContext;
	}

	


	@Override
	public void setLastRequestListener(LastRequestListener listener) {
		// TODO Auto-generated method stub
		
	}




	/* (non-Javadoc)
	 * @see com.ma.bi.webcralwer.handle.PageHandler#handle(us.codecraft.webmagic.Page)
	 */
	@Override
	public void handle(Page page) {
		// TODO Auto-generated method stub
		
		Document doc = page.getHtml().getDocument();
		
		Elements elems = doc.select( "a.topFiltersTracking" );
		
		// --- mark state ---
		TraceAttr ta = context.getTraceAttr();
		String taKey = "link-level-1";
		java.io.Serializable taValue = ta.getValue(taKey);
		int levelCount = null == taValue ? 0 : Integer.parseInt(taValue.toString());
		
		Lock lock = context.getLock();
		
		lock.lock();
		
		try {
			
			State state = context.getState();
			state.open();
			
			Iterator<Element> iter = elems.iterator();
			while (iter.hasNext()) {
				Element ele = iter.next();
				String urlLink = ele.attr("href");

				// ---- get the infomation ---
				
				String dataType = ele.attr("data-type");
				Request req = new Request(urlLink);
				
				// --- set the first level ---
				state.update(urlLink.getBytes(), new byte[]{State.PENDING , 1});
				// --- add the category and brand --
				page.addTargetRequest(req);
				levelCount = levelCount + 1;
				if (logger.isDebugEnabled()) {
					logger.debug("Add Request : " + urlLink);
				}
				
			}
			
			state.commit();
			
			state.close();			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}

		ta.putValue(taKey, levelCount);
		
		ta.flushOrSave();
	}

}
