package com.ma.bi.webcralwer;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Provider;

import org.iq80.leveldb.Options;

import us.codecraft.webmagic.Spider;

public class WebcralwerServiceManagerProvider implements Provider<WebcralwerServiceManager> {

	@Named("processors.record.map")
	@Inject
	private Map recordProcMaps;
	
	@Named("base.dir")
	@Inject
	private File baseDir;
	
	@Named("env.conf")
	@Inject
	private Map globalConf;
	
	
	@Override
	public WebcralwerServiceManager get() {
		// TODO Auto-generated method stub
		WebcralwerServiceManagerImpl wsmi = new WebcralwerServiceManagerImpl();
		
		Map<String, List<PreLoadProcRecord>>  rpm =  (Map<String, List<PreLoadProcRecord>>)recordProcMaps;
		
		List<Spider> prepareSpiders = new Vector<Spider>();
		
		try {
			
			for (Map.Entry<String, List<PreLoadProcRecord>>  prmEntry : rpm.entrySet()  ) {

				String nameSpace = prmEntry.getKey();
				File namespaceDir = new File(baseDir, nameSpace);

				if (!namespaceDir.exists()) {
					namespaceDir.mkdirs();
				}
				State stateInst = createState(namespaceDir);
				
				
				ProcessorContextImpl pci = new ProcessorContextImpl();
				pci.setState( stateInst );
				
				List<PreLoadProcRecord> recs =  prmEntry.getValue();
				
				for ( Iterator<PreLoadProcRecord> pprIter = recs.iterator() ; pprIter.hasNext() ; ) {
					PreLoadProcRecord ppr = pprIter.next();
					
					Spider spiderInst = createSpiderForProcessor(ppr , pci);
					spiderInst.thread(5);
					prepareSpiders.add( spiderInst );
				}
				
			}
			
			wsmi.setPreparedSpiders( prepareSpiders );
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return wsmi;
	}
	
	private Spider createSpiderForProcessor(PreLoadProcRecord preloadProcRecord , ProcessorContextImpl pci) throws InstantiationException, IllegalAccessException {
		AbstractPageEntryProcessor  proc =  preloadProcRecord.getBaseClass().newInstance();
		proc.setProcessorContext( pci );
		
		Spider inst = Spider.create(proc);
		
		for (String url : preloadProcRecord.getUrls()) {
			inst.addUrl( url );
		}
		return inst;
	}
	
	
	private State createState(File namespaceDir) {
		
		File stateFolder = new File(namespaceDir , "state");
		
		Options options = new Options();
		
		Object createIfMiss = globalConf.get("leveldb.autocreate");
		if (null != createIfMiss) {
			Boolean cim = new Boolean(createIfMiss.toString());
			options.createIfMissing(cim);
		} else {
			options.createIfMissing(true);				
		}
		
		
		LevelDBState state = new LevelDBState(stateFolder , options);
	
		return state;
		
	}

}
