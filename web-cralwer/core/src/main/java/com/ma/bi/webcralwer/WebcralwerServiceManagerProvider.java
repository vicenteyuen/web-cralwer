package com.ma.bi.webcralwer;

import java.io.File;
import java.util.Calendar;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

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
	
	private Lock lock = new ReentrantLock();
	
	@Override
	public WebcralwerServiceManager get() {
		// TODO Auto-generated method stub
		WebcralwerServiceManagerImpl wsmi = new WebcralwerServiceManagerImpl();
		
		Map<String, List<PreLoadProcRecord>>  rpm =  (Map<String, List<PreLoadProcRecord>>)recordProcMaps;
		
		List<Spider> prepareSpiders = new Vector<Spider>();
		
		try {
			
			Calendar cale = Calendar.getInstance();
			int weekNum  = cale.get( Calendar.WEEK_OF_YEAR );
			int yearNum = cale.get( Calendar.YEAR );			
			StringBuilder dateString = new StringBuilder();
			dateString.append(yearNum).append(weekNum);

			
			// --- list record list --
			for (Map.Entry<String, List<PreLoadProcRecord>>  prmEntry : rpm.entrySet()  ) {

				String nameSpace = prmEntry.getKey();
				File namespaceDir = new File(baseDir, nameSpace);

				if (!namespaceDir.exists()) {
					namespaceDir.mkdirs();
				}
				
				LevelDBState stateInst = createState(namespaceDir,dateString.toString());
				DataRepo dataRepo = createDataRepo(namespaceDir , dateString.toString());
				JsonTraceAttr jta =  createTraceAttr(namespaceDir , dateString.toString());
				
				
				// --- start handle ---
				ProcessorContextImpl pci = new ProcessorContextImpl();
				pci.setState( stateInst );
				pci.setDataRepo( dataRepo );
				pci.setTraceAttr( jta );
				pci.setLock(lock);

				stateInst.open();
				Collection<String> needToLoads = stateInst.foundRecordsByState( State.PENDING );
				stateInst.close();
				
				List<PreLoadProcRecord> recs =  prmEntry.getValue();
							
				for ( Iterator<PreLoadProcRecord> pprIter = recs.iterator() ; pprIter.hasNext() ; ) {
					PreLoadProcRecord ppr = pprIter.next();
					
					Spider spiderInst = null;
					if (needToLoads.isEmpty()) {
						spiderInst = createSpiderForProcessor(ppr , pci);
					} else {
						spiderInst = createSpiderForProcessor(ppr , pci , needToLoads);
					}
					
					/*
					SpiderListener sl = new SpiderListener() {

						@Override
						public void onSuccess(Request request) {
							// TODO Auto-generated method stub
							
						}

						@Override
						public void onError(Request request) {
							// TODO Auto-generated method stub
							
						}
						
					};
					*/

					spiderInst.setExitWhenComplete(true);
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
	
	
	private Spider createSpiderForProcessor(PreLoadProcRecord preloadProcRecord , ProcessorContextImpl pci , Collection<String> urls) throws InstantiationException, IllegalAccessException {
		AbstractPageEntryProcessor  proc =  preloadProcRecord.getBaseClass().newInstance();
		proc.setProcessorContext( pci );
		
		Spider inst = Spider.create(proc);
		
		for (String url : urls) {
			inst.addUrl( url );
		}

		return inst;
	}	
	
	
	private LevelDBState createState(File namespaceDir,  String dateString) {
		
		File stateFolder = new File(namespaceDir , dateString+"_state");
		
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
	
	private DataRepo createDataRepo(File namespaceDir, String dateString) {

		dateString = dateString+".txt";
		
		File currentTxt = new File(namespaceDir , dateString);
		// --- create file ---
		
		FileDataRepo fdr = new FileDataRepo(currentTxt);
		

		return fdr;
	}
	
	private JsonTraceAttr createTraceAttr(File namespaceDir , String dateString) {
		dateString = dateString+".ta";
		File currentFile = new File(namespaceDir , dateString);
		
		JsonTraceAttr jta = new JsonTraceAttr(currentFile);
		
		return jta;
	}
	

}
