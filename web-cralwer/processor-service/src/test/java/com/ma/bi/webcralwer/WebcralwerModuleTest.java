/**
 * 
 */
package com.ma.bi.webcralwer;

import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * @author ruanweibiao
 *
 */
public class WebcralwerModuleTest {



	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		// TODO Auto-generated method stub
		Injector injector = Guice.createInjector(new WebcralwerModule());
		
		
		// --- startup handle ---
		WebcralwerServiceManager webcralwerService =  injector.getInstance( WebcralwerServiceManager.class );
		
		webcralwerService.run();


		
	}

}
