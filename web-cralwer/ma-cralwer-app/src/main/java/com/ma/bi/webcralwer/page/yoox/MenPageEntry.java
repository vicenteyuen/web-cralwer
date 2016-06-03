package com.ma.bi.webcralwer.page.yoox;

import java.util.LinkedHashMap;
import java.util.Map;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.ma.bi.webcralwer.WebcralwerServiceManager;

public class MenPageEntry {

	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Map<String,Object> argMap = new LinkedHashMap<String,Object>();
		
		// --- match args --
		for (String arg : args) {
			if (null != arg) {
				if (arg.startsWith("-conf=")) {
					String value = arg.replace("-conf=", "");
					argMap.put("conf", value);
				}
			}
		}
		
		// --- use instance entry ---
		Injector injector = Guice.createInjector(new MenuPageEntryModule(argMap));
		WebcralwerServiceManager  wsm = injector.getInstance( WebcralwerServiceManager.class );
		wsm.run();

	}

}
