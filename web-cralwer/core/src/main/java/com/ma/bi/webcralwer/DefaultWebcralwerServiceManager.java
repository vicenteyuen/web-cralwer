/**
 * 
 */
package com.ma.bi.webcralwer;

import java.util.LinkedList;
import java.util.List;

/**
 * @author ruanweibiao
 *
 */
public class DefaultWebcralwerServiceManager implements
		WebcralwerServiceManager {
	
	private List<AbstractPageEntryProcessor> processes = new LinkedList<AbstractPageEntryProcessor>();

	/**
	 * 
	 */
	public DefaultWebcralwerServiceManager() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see com.ma.bi.webcralwer.WebcralwerServiceManager#run()
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		// --- define and run instance ---



	}

}
