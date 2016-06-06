/**
 * 
 */
package com.ma.bi.webcralwer.component;

import java.io.File;
import java.io.IOException;

import org.iq80.leveldb.DB;
import org.iq80.leveldb.DBException;
import org.iq80.leveldb.DBIterator;
import org.iq80.leveldb.Options;
import org.iq80.leveldb.impl.Iq80DBFactory;
import org.junit.Test;

/**
 * @author ruanweibiao
 *
 */
public class LevelDBTest {

	private String mockDir = "testMockData";

	@Test
	public void test_01_writeDB() {

		Options options = new Options();
		options.createIfMissing(true);

		// --- create tmp example ---
		DB db = null;
		try {
			db = Iq80DBFactory.factory.open(new File(mockDir), options);

			db.put(Iq80DBFactory.bytes("key1"),
					Iq80DBFactory.bytes("{test:'testvalue1'}"));
			db.put(Iq80DBFactory.bytes("key2"),
					Iq80DBFactory.bytes("{test:'testvalue2'}"));
			db.put(Iq80DBFactory.bytes("key3"), Iq80DBFactory.bytes("中文测试"));
		} catch (DBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				db.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				db = null;
			}
		}

		System.out.println("write message");

	}

	@Test
	public void test_02_readDB() {
		Options options = new Options();
		options.createIfMissing(true);

		// --- create tmp example ---
		DB db = null;
		try {
			db = Iq80DBFactory.factory.open(new File(mockDir), options);

			byte[] valueBytes1 = db.get(Iq80DBFactory.bytes("key1"));
			String value1 = Iq80DBFactory.asString(valueBytes1);
			System.out.println(value1);

			byte[] valueBytes2 = db.get(Iq80DBFactory.bytes("key2"));
			String value2 = Iq80DBFactory.asString(valueBytes2);
			System.out.println(value2);

			byte[] valueBytes3 = db.get(Iq80DBFactory.bytes("key3"));
			String value3 = Iq80DBFactory.asString(valueBytes3);
			System.out.println(value3);
		} catch (DBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				db.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				db = null;
			}
		}
	}

	@Test
	public void test_03_readAllValues() {

		Options options = new Options();
		options.createIfMissing(true);

		// --- create tmp example ---
		DB db = null;
		try {
			db = Iq80DBFactory.factory.open(new File(mockDir), options);
			
			DBIterator iterator = db.iterator();
			try {
				for (iterator.seekToFirst(); iterator.hasNext(); iterator.next()) {
					String key = Iq80DBFactory.asString(iterator.peekNext().getKey());
					String value = Iq80DBFactory.asString(iterator.peekNext()
							.getValue());
					System.out.println(key + " = " + value);
				}			
			} finally {
				
				try {
					iterator.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}			

		} catch (DBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				db.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				db = null;
			}
		}

	}

}
