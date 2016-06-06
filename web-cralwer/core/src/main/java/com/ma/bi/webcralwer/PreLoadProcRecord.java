/**
 * 
 */
package com.ma.bi.webcralwer;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author ruanweibiao
 *
 */
public class PreLoadProcRecord {
	
	Class<AbstractPageEntryProcessor> baseClass;
	
	Set<String> urls  = new LinkedHashSet<String>();

	public Class<AbstractPageEntryProcessor> getBaseClass() {
		return baseClass;
	}

	public void setBaseClass(Class<AbstractPageEntryProcessor> baseClass) {
		this.baseClass = baseClass;
	}

	public Set<String> getUrls() {
		return urls;
	}

	public void setUrls(Set<String> urls) {
		this.urls = urls;
	}

	
	
}
