/**
 * 
 */
package com.ma.bi.webcralwer;

import java.util.Collection;

/**
 * @author ruanweibiao
 *
 */
public interface State {
	
	public static byte PENDING = 0;
	
	public static byte FININED = 2;
	
	boolean update(byte[] key , byte state);
	
	/**
	 * get all state 
	 * @param state
	 * @return
	 */
	Collection<String> foundRecordsByState(byte state);
	
	void open();
	
	void commit();
	
	void close();

}
