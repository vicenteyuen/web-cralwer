package com.ma.bi.webcralwer.processors.yoox;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class BaseTest {

	public BaseTest() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String url = "http://www.yoox.cn/cn/%E7%94%B7%E5%A3%AB/shoponline/%E5%A4%A7%E8%A1%A3%E5%8F%8A%E5%A4%96%E5%A5%97_mc#/dept=men&gender=U&page=2&attributes=%7b%27ctgr%27%3a%5b%27mntll%27%2c%27cpptt%27%2c%27mntn2%27%2c%27mntgmr1%27%5d%7d&season=X";
		
		try {
			String u = URLDecoder.decode(url, "utf-8");
			
			System.out.println(u);
			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	

	}

}
