package com.cloud.fly;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

public class WebDownload {
	public static Hashtable<String, String> mReqProperty = new Hashtable<>();
	public static Hashtable<String,String> mBagAnalysis = new Hashtable<>();
	{
		mReqProperty.put("accept", "*/*");
		mReqProperty.put("connection", "Keep-Alive");
		mReqProperty.put("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
		mReqProperty.put("Upgrade-Insecure-Requests", "1");
		mReqProperty.put("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
		mReqProperty.put("Accept-Encoding","gzip, deflate, sdch");
		mReqProperty.put("Accept-Language","zh-CN,zh;q=0.8");
		mReqProperty.put("Cache-Control","max-age=0");
		mReqProperty.put("Host","stgy.upchina.com");
	}
	public static void main(String[] args) {		
		ArrayList<SnakeInfo> list = GPInfoGet.getAllSnake();
		gotAllSnakeFuture(list);
		
	}
	public static boolean downloadFuture(SnakeInfo snake) {
		String url = "http://stgy.upchina.com/jsonpredit/GET_1" + snake.code + ".html";
		String s = HttpRequest.sendGet(url, null,mReqProperty);
		if(s == null || s.isEmpty()) {
			return false;
		}
		System.out.println(s);
		List<FutureInfo> list = Parser.parse(s);
		snake.futureList = list;
//		if(list == null || list.size() < 4)
//			return false;
		return true;
		// System.out.println(list.size());
	}

	public static void gotAllSnakeFuture(ArrayList<SnakeInfo> list) {
        if(list == null) {
        	    return;
        }
        int i = 0;
		for(i = 0;i<list.size();i++) {
			SnakeInfo snake = list.get(i);
			if(snake == null) {
//				list.remove(i);
				continue;
			}
			downloadFuture(snake);
		}
	}
}
