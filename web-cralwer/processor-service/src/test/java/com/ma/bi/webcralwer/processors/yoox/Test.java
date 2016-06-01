package com.ma.bi.webcralwer.processors.yoox;

import java.io.File;
import java.io.IOException;

import org.iq80.leveldb.DB;
import org.iq80.leveldb.Options;

public class Test {

	public Test() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Options options = new Options();
		options.createIfMissing(true);
		DB db = null;
		try {
			db = org.fusesource.leveldbjni.JniDBFactory.factory.open(new File("example"), options);
			

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			  // Make sure you close the db to shutdown the 
			  // database and avoid resource leaks.
			  try {
				db.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
