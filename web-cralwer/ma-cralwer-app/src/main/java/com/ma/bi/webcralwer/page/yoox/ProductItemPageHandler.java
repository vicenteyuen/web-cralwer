/**
 * 
 */
package com.ma.bi.webcralwer.page.yoox;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Iterator;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import us.codecraft.webmagic.Page;

import com.ma.bi.webcralwer.DataRepo;
import com.ma.bi.webcralwer.LastRequestListener;
import com.ma.bi.webcralwer.PageHandler;
import com.ma.bi.webcralwer.ProcessorContext;
import com.ma.bi.webcralwer.ResultCallback;
import com.ma.bi.webcralwer.State;

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

	private DecimalFormat df_inst_0 = new DecimalFormat("¥ 00,000.0");


	/* (non-Javadoc)
	 * @see com.ma.bi.webcralwer.handle.PageHandler#handle(us.codecraft.webmagic.Page)
	 */
	@Override
	public void handle(Page page) {
		// TODO Auto-generated method stub
		
		State state = context.getState();
		
		DataRepo dr = context.getDataRepo();
		
		Document doc = page.getHtml().getDocument();
		
		StringBuilder brandStr = new StringBuilder();
		Elements brandElems = doc.select("span[itemprop=brand]");
		for (Iterator<Element> elesIter = brandElems.iterator(); elesIter.hasNext();) {
			Element elem = elesIter.next();
			String brandName = elem.text();
			brandStr.append( brandName );
		}

		StringBuilder priceStr = new StringBuilder();		
		Elements priceElems = doc.select("span[itemprop=price]");
		for (Iterator<Element> elesIter = priceElems.iterator(); elesIter.hasNext();) {
			Element elem = elesIter.next();
			String price = elem.text();
			
			try {
				Number num = df_inst_0.parse(price);
				priceStr.append("¥").append(num);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		StringBuilder nameStr = new StringBuilder();
		Elements nameElems = doc.select("span[itemprop=name]");
		for (Iterator<Element> elesIter = nameElems.iterator(); elesIter.hasNext();) {
			Element elem = elesIter.next();
			String name = elem.text();
			nameStr.append( name );
		}
		
		StringBuilder colorStr = new StringBuilder();
		Elements imgElems = doc.select("img.color");
		for (Iterator<Element> elesIter = imgElems.iterator(); elesIter.hasNext();) {
			Element elem = elesIter.next();
			String color = elem.attr("title");
			if (colorStr.length() > 0) {
				colorStr.append("|");
			}
			colorStr.append( color );
		}
		
		StringBuilder linkStr = new StringBuilder();
		Elements linkElems = doc.select("a.breadcrumbLink");
		for (Iterator<Element> elesIter = linkElems.iterator(); elesIter.hasNext();) {
			Element elem = elesIter.next();
			String linkTxt = elem.text();
			if ("男士".equals( linkTxt ) || "首页".equals( linkTxt )) {
				continue;
			}
			if (linkStr.length() > 0) {
				linkStr.append("|");
			}
			linkStr.append( linkTxt );			
		}

		
		// --- open save file ---
		dr.open();
		
		String[] record = new String[]{nameStr.toString(),brandStr.toString(),priceStr.toString(),colorStr.toString(), linkStr.toString(),"male"};
		dr.addOne(record);
		
		dr.commit(new ResultCallback() {

			@Override
			public void completed() {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void fail() {
				// TODO Auto-generated method stub
				
			}
		});
		
		dr.close();
		
		
		state.open();
		// --- mark handle ---
		state.update(page.getRequest().getUrl().getBytes(), State.FININED);
		
		state.commit();
		
		state.close();
		


		
	}

}
