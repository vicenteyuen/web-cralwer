/**
 * 
 */
package com.ma.bi.webcralwer;

import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import us.codecraft.webmagic.Spider;



/**
 * @author ruanweibiao
 *
 */
public class WebcralwerServiceManagerImpl implements
		WebcralwerServiceManager {
	
	private static Logger logger = LoggerFactory.getLogger( WebcralwerServiceManagerImpl.class );
	
	private List<Spider> prepareSpiders = new LinkedList<Spider>();
	
	void setPreparedSpiders( List<Spider> prepareSpiders) {
		this.prepareSpiders = prepareSpiders;
	}


	
	/* (non-Javadoc)
	 * @see com.ma.bi.webcralwer.WebcralwerServiceManager#run()
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		// --- define and run instance ---
		if (prepareSpiders.isEmpty()) {
			return;
		}
		
		for (Spider item : prepareSpiders) {
			//item.run();
			item.start();
		}
		
		
		// --- boot scheduler ---

		
	}

}
