package com.ma.bi.webcralwer;

import java.util.concurrent.locks.Lock;


/**
 * Mark The Processor handle 
 * @author Vicente Yuen
 *
 */
public interface ProcessorContext {
	
	
	State getState();
	
	
	DataRepo getDataRepo();
	
	
	TraceAttr  getTraceAttr();
	
	Lock getLock();

}
