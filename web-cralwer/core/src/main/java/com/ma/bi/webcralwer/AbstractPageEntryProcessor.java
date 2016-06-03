/**
 * 
 */
package com.ma.bi.webcralwer;


/**
 * @author Vicente Yuen
 *
 */
public abstract class AbstractPageEntryProcessor implements PageEntryProcessor {

	
	
	/**
	 * 
	 */
	public AbstractPageEntryProcessor() {
		// TODO Auto-generated constructor stub
	}
	
	
	private ProcessorContext context;

	@Override
	public ProcessorContext getContext() {
		// TODO Auto-generated method stub
		return context;
	}
	
	public void setProcessorContext(ProcessorContext context) {
		this.context = context;
	}



}
