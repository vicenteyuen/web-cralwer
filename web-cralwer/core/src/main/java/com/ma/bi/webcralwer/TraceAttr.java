package com.ma.bi.webcralwer;

public interface TraceAttr {
	
	void putValue(String key , java.io.Serializable value);
	
	java.io.Serializable getValue(String key);
	
	
	void flushOrSave();

}
