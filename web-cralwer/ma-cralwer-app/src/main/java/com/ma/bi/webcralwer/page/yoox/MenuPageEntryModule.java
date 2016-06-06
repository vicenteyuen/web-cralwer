package com.ma.bi.webcralwer.page.yoox;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import com.ma.bi.webcralwer.AbstractPageEntryProcessor;
import com.ma.bi.webcralwer.PreLoadProcRecord;
import com.ma.bi.webcralwer.WebcralwerServiceManager;
import com.ma.bi.webcralwer.WebcralwerServiceManagerProvider;

public class MenuPageEntryModule extends AbstractModule {
	
	
	private Map<String,Object> moduleArgs;

	public MenuPageEntryModule(Map<String,Object> args) {
		// TODO Auto-generated constructor stub
		moduleArgs = args;
	}

	@Override
	protected void configure() {
		// TODO Auto-generated method stub
		
		File baseConf = new File(moduleArgs.get("conf").toString());
		File procsConf = new File(baseConf , "procs");
		File[] allConfs = procsConf.listFiles(new FileFilter() {

			@Override
			public boolean accept(File file) {
				// TODO Auto-generated method stub
				return file.getName().endsWith(".json");
			}
			
		});
		
		
		// --- global conf ---
		File globalConf = new File(baseConf, "conf.json");
		
		
		if (allConfs == null) {
			return;
		}

		
		// ---- 
		try {
			Map<String,String> globalEnv = readGlobalConf(globalConf);
			
			
			List<PreLoadProcRecord> pprList = new LinkedList<PreLoadProcRecord>();
			Map<String, List<PreLoadProcRecord>> recordProcMaps = new LinkedHashMap<String, List<PreLoadProcRecord>>();
			readToPreLoadProcRecord(allConfs , recordProcMaps);
			
			
			// --- 
			File baseDir = new File(globalEnv.get("base-dir"));
			if (!baseDir.exists()) {
				throw new FileNotFoundException("Could not define base dir.");
			}

			this.bind(Map.class).annotatedWith(Names.named("processors.record.map")).toInstance( recordProcMaps );
			this.bind(File.class).annotatedWith(Names.named("base.dir")).toInstance(baseDir);
			this.bind(Map.class).annotatedWith(Names.named("env.conf")).toInstance(globalEnv);
			
			// --- bind state ---
			this.bind(WebcralwerServiceManager.class).toProvider(WebcralwerServiceManagerProvider.class);
			
			
			
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		



		
		
		
		

	}
	
	
	private Map<String,String> readGlobalConf(File globalConf) throws FileNotFoundException, IOException {
		// --- parse config ---
		String content = IOUtils.toString(new FileInputStream(globalConf) , "utf-8");
		
		Map<String,String> globalEnv = new LinkedHashMap<String,String>();
		
		JSONObject obj = JSON.parseObject(content);

		globalEnv.put("base-dir", obj.getString("base-dir") );
		
		// --- read leveldb config ---
		JSONObject levelDBConf =  obj.getJSONObject("leveldb");
		if (null != levelDBConf) {
			// --- read conf ---
		}
		
		return globalEnv;
	}
	
	private void readToPreLoadProcRecord(File[] allConfs , Map<String, List<PreLoadProcRecord>> recordProcMaps) throws FileNotFoundException, IOException {
		


		
		for (File file : allConfs) {
			
			String key = file.getName().replace(".json", "");
			List<PreLoadProcRecord> pprList = recordProcMaps.get(key);
			if (null == pprList ) {
				pprList = new LinkedList<PreLoadProcRecord>();	
			}

			String content = IOUtils.toString(new FileInputStream(file) , "utf-8");
			
			JSONObject obj = JSON.parseObject(content);
			JSONArray processorsArray =  obj.getJSONArray("processors");
			for (Iterator<JSONObject> procIter =  (Iterator<JSONObject>)(Iterator)processorsArray.iterator() ; procIter.hasNext(); ) {
				JSONObject proc = procIter.next();
				
				String clsName = (String)proc.get("className");
				
				try {
					PreLoadProcRecord ppr = new PreLoadProcRecord();
					
					Class<AbstractPageEntryProcessor> cls = (Class<AbstractPageEntryProcessor>)(Class)Class.forName(clsName);
					ppr.setBaseClass( cls );

					
					JSONArray urlsArray  = proc.getJSONArray("entry-urls");
					
					for (Iterator<String> urlArray =  (Iterator<String>)(Iterator)urlsArray.iterator() ; urlArray.hasNext(); ) {
						String urlStr = urlArray.next();
						
						ppr.getUrls().add(urlStr);
					}
					
					pprList.add( ppr );
					
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			
			// --- put to value ---
			recordProcMaps.put(key, pprList);


		}		
	}
	

}
