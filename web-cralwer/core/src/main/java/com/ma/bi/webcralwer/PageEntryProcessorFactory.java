/**
 * 
 */
package com.ma.bi.webcralwer;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import org.iq80.leveldb.DB;
import org.iq80.leveldb.DBException;
import org.iq80.leveldb.Options;

/**
 * @author Vicente Yuen
 *
 */
public class PageEntryProcessorFactory {
	
	
	private static PageEntryProcessorFactory _inst;
	
	public static PageEntryProcessorFactory getInstance() {
		if (_inst == null) {
			_inst = new PageEntryProcessorFactory();
		}
		return _inst;
	}
	
	
	public PageEntryProcessor newPageEntryProcessor(Class<AbstractPageEntryProcessor> pageEntryProcessorCls , File baseFolder , Properties envProps) {
		
		AbstractPageEntryProcessor app = null;
		
		try {
			
			if (baseFolder.exists()) {
				baseFolder.mkdirs();
			}
			
			app =  pageEntryProcessorCls.newInstance();
			
			
			// --- init processor context ---
			initPageEntryProcessor(app , baseFolder ,envProps);
			
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return app;
	}
	
	
	private void initPageEntryProcessor(AbstractPageEntryProcessor app ,File baseFolder , Properties envProps) {
		
		File stateFolder = new File(baseFolder , "state");
		if (!stateFolder.exists()) {
			stateFolder.mkdirs();
		}
		
		// --- set levelDBState 
		Options options = new Options();
		LevelDBState levelDBState = new LevelDBState(stateFolder,options);
		
		
		ProcessorContextImpl pci = new ProcessorContextImpl();
		pci.setState( levelDBState );

	
		
		
			
		// --- add context ---
		app.setProcessorContext(pci);
	}
	


}
