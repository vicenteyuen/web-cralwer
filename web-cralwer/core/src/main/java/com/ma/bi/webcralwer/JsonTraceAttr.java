/**
 * 
 */
package com.ma.bi.webcralwer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;

import org.apache.commons.io.IOUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * @author Vicente Yuen
 *
 */
public class JsonTraceAttr implements TraceAttr {
	
	private JSONObject jsonObj = new JSONObject();
	
	private File taFile;
	
	public JsonTraceAttr(File taFile) {
		this.taFile = taFile;
	}

	/* (non-Javadoc)
	 * @see com.ma.bi.webcralwer.TraceAttr#putValue(java.lang.String, java.io.Serializable)
	 */
	@Override
	public void putValue(String key, Serializable value) {
		// TODO Auto-generated method stub
		jsonObj.put(key, value);
	}

	/* (non-Javadoc)
	 * @see com.ma.bi.webcralwer.TraceAttr#getValue(java.lang.String)
	 */
	@Override
	public Serializable getValue(String key) {
		// TODO Auto-generated method stub
		Serializable value = (Serializable)jsonObj.get(key);
		return value;
	}

	
	
	@Override
	public void flushOrSave() {
		// TODO Auto-generated method stub
		
		String data = JSON.toJSONString(jsonObj);
		
		try {
			IOUtils.write(data.getBytes(), new FileOutputStream(taFile));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}
	

	

}
