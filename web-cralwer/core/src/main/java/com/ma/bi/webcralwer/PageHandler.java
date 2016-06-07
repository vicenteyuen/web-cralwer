/**
 * 
 */
package com.ma.bi.webcralwer;

import us.codecraft.webmagic.Page;

/**
 * @author ruanweibiao
 *
 */
public interface PageHandler{
	
	
	public void handle(Page page);
	
	
	void setProcessorContext(ProcessorContext  procContext);
	
	
	void setLastRequestListener(LastRequestListener listener);


}
