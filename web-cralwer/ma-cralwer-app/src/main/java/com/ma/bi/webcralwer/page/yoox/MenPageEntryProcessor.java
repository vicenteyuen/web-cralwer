/**
 * 
 */
package com.ma.bi.webcralwer.page.yoox;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Collection;
import java.util.Vector;
import java.util.concurrent.locks.Lock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;

import com.ma.bi.webcralwer.AbstractPageEntryProcessor;
import com.ma.bi.webcralwer.LastRequestListener;
import com.ma.bi.webcralwer.PageHandler;
import com.ma.bi.webcralwer.ProcessorContext;
import com.ma.bi.webcralwer.State;

/**
 * @author vison
 *
 */
public class MenPageEntryProcessor extends AbstractPageEntryProcessor {
	
	
	private static Logger logger = LoggerFactory.getLogger( MenPageEntryProcessor.class );

	private Site site = Site.me().setRetryTimes(3).setSleepTime(1000);
	
	private String siteHost = "http://www.yoox.cn";


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
		
		
		// --- match handle pattern ---
		URL url = null;
		try {
			url = new URL(URLDecoder.decode(page.getRequest().getUrl(), "utf-8"));
		} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// --- skip handle ---
		if (url == null) {
			return;
		}

		ProcessorContext  procContext =  this.getContext();
		

		// --- check match url ---

		PageHandler pmHandler = null;
		if (url.getPath().equals("/cn/男士")) {
			// --- handle processor ---
			pmHandler = new MenPageHandler();
			pmHandler.setProcessorContext(procContext);
			pmHandler.handle( page );
			
			
			// --- break handle ---
		}
		
		else if (url.getPath().startsWith("/cn/男士/shoponline/")) {
			
			ProcessorContext pc =  this.getContext();
			LastRequestListener lrl = new LastRequestListener() {

				@Override
				public void fireHandle() {
					// TODO Auto-generated method stub
					State state = pc.getState();
					Lock lock = pc.getLock();
					
					Collection<String> urls = new Vector<String>();
					
					try {
						
						lock.lock();
						
						state.open();
						urls = state.foundRecordsByState(State.PENDING);
						state.close();
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					} finally {
						lock.unlock();
					}
					
					System.out.println("total record : " + urls.size());
					
				}
				
			};
			
			
			pmHandler = new ShopOnlinePageHandler();

			pmHandler.setProcessorContext(procContext);
			pmHandler.handle( page );		

		}
		
		
		// --- check the all value item ---
		/*
		if (matchProductItemUrl(url)) {
			pmHandler = new ProductItemPageHandler();
			pmHandler.setProcessorContext(procContext);
			pmHandler.handle( page );		
			return;
			
		}
		
		State state = procContext.getState();
		
		state.open();
		
		// --- update state after process html --
		try {
			state.update( URLDecoder.decode(page.getRequest().getUrl(), "utf-8").getBytes() , State.FININED);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		state.commit();
		
		state.close();
		*/
		

		


		


	}
	
	private boolean matchProductItemUrl(URL url) {
		String path = url.getPath();
		boolean match1 = path.startsWith("/cn/");
		boolean match2 = path.endsWith("/item");
		return match1 && match2;
	}
	

}
