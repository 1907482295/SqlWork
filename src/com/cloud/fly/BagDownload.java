package com.cloud.fly;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import com.google.gson.Gson;

public class BagDownload {
	public static String mFilePath = "";
	public static Hashtable<String, String> mReqProperty = new Hashtable<>();
	public static ArrayList<SnakeInfo> mSnakeInfos;
	public static void main(String[] args) {
		mSnakeInfos = GPInfoGet.getAllSnake();
		downloadAllBag(mSnakeInfos);
	}
	public static void downloadAllBag(ArrayList<SnakeInfo> list) {
		for(int i=0;i<list.size();i++){
			System.out.println(list.get(i).code+"...");
			downloadBag(list.get(i).code, list.get(i));
		}
	}
	
	public static boolean downloadBag(String code,SnakeInfo info) {
		String url = "https://app.leverfun.com/timelyInfo/timelyOrderForm?stockCode="+code;
		String s = HttpRequest.sendGet(url, null,mReqProperty);
		if(s == null || s.isEmpty()) {
			return false;
		}
		System.out.println(s);
		Bag bag = new Gson().fromJson(s, Bag.class);
		info.bag = bag;
		return true;
		// System.out.println(list.size());
	}
	public static double getClose(SnakeInfo info){
		if(info.bag == null || info.bag.data == null) {
			return 0;
		}
		return info.bag.data.match;
	}
	public static double getPreClose(SnakeInfo info){
		if(info.bag == null || info.bag.data == null) {
			return 0;
		}
		return info.bag.data.preClose;
	}
	public static String getBuyPankou(SnakeInfo info){
		if(info.bag == null || info.bag.data == null || info.bag.data.buyPankou == null) {
			return "";
		}
		String s = "";
		for(int i=0;i<info.bag.data.buyPankou.length;i++){
			if(i > 0){
				s+=",";
			}
			s += String.valueOf((int)(info.bag.data.buyPankou[i].volume));
		}
		return s;
	}
	public static String getSellPankou(SnakeInfo info){
		if(info.bag == null || info.bag.data == null || info.bag.data.sellPankou == null) {
			return "";
		}
		String s = "";
		for(int i=0;i<info.bag.data.sellPankou.length;i++){
			if(i > 0){
				s+=",";
			}
			s += String.valueOf((int)(info.bag.data.sellPankou[i].volume));
		}
		return s;
	}
}
