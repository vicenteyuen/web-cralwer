/**
 * 
 */
package com.ma.bi.webcralwer;

/**
 * @author ruanweibiao
 *
 */
public interface State {
	
	public static byte PENDING = 0;
	
	public static byte FININED = 2;
	
	boolean update(byte[] key , byte state);
	
	void open();
	
	void commit();
	
	void close();

}
