/**
 * 
 */
package com.ma.bi.webcralwer.page.yoox;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;

import com.ma.bi.webcralwer.AbstractPageEntryProcessor;

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
		
		// --- check match url ---
		/*

		PageHandler pmHandler = null;
		String path = "/cn/男士";
		if (url.getPath().equals(path)) {
			// --- handle processor ---
			pmHandler = new MenPageHandler();
			pmHandler.setState(state);
			pmHandler.handle( page );
			
			
			// --- break handle ---
			return;
		}
		
		path = "/cn/男士/shoponline/";
		if (url.getPath().startsWith(path)) {
			
			pmHandler = new ShopOnlinePageHandler();
			pmHandler.setState(state);
			pmHandler.handle( page );		
			return;
		}
		
		if (matchProductItemUrl(url)) {
			pmHandler = new ProductItemPageHandler();
			pmHandler.setState(state);
			pmHandler.handle( page );		
			return;
			
		}
		
		
		// --- update state after process html --
		try {
			state.update( URLDecoder.decode(page.getRequest().getUrl(), "utf-8").getBytes() , State.FININED);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		

		


		


	}
	
	private boolean matchProductItemUrl(URL url) {
		String path = url.getPath();
		boolean match1 = path.startsWith("/cn/");
		boolean match2 = path.endsWith("/item");
		return match1 && match2;
	}
	

}
