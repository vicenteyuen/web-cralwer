/**
 * 
 */
package com.ma.bi.webcralwer.processors.yoox;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

import com.ma.bi.webcralwer.handle.PageHandler;

/**
 * @author vison
 *
 */
public class YooxMenPageEntryProcessor implements PageProcessor {
	
	
	private static Logger logger = LoggerFactory.getLogger( YooxMenPageEntryProcessor.class );

	private Site site = Site.me().setRetryTimes(3).setSleepTime(1000);		
	
	/* (non-Javadoc)
	 * @see us.codecraft.webmagic.processor.PageProcessor#getSite()
	 */
	@Override
	public Site getSite() {
		// TODO Auto-generated method stub
		return site;
	}
	
	private String siteHost = "http://www.yoox.cn";

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

		PageHandler pmHandler = null;
		String path = "/cn/男士";
		if (url.getPath().equals(path)) {
			// --- handle processor ---
			pmHandler = new MenPageHandler();
			pmHandler.handle( page );
			
			// --- break handle ---
			return;
		}
		
		path = "/cn/男士/shoponline/";
		if (url.getPath().startsWith(path)) {
			
			pmHandler = new ShopOnlinePageHandler();
			pmHandler.handle( page );		
			return;
		}
		
		if (matchProductItemUrl(url)) {
			pmHandler = new ProductItemPageHandler();
			pmHandler.handle( page );		
			return;
			
		}
		
		System.exit(0);
		


		


	}
	
	private boolean matchProductItemUrl(URL url) {
		String path = url.getPath();
		boolean match1 = path.startsWith("/cn/");
		boolean match2 = path.endsWith("/item");
		return match1 && match2;
	}
	

}
