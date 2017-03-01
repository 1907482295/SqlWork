package com.cloud.fly;

import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;

import com.google.gson.JsonObject;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.text.SimpleDateFormat;
import java.util.Date;
public class JiaoYiAnQuan {
	
    public static String URL = "http://h5sm.upchinapro.com/getSpeedDialExpr?gscode=%E4%BA%A4%E6%98%93%E5%AE%89%E5%85%A8_%E4%B9%9D%E5%AE%AB%E5%9B%BE&gpcode=";
	public static void main(String[] args) {		
		//downloadJYAQ();
		//downloadJson("000900");
	}
	public static void downloadJYAQ(ArrayList<SnakeInfo> list){
		downloadAll(list);
	}
	
	public static String addPrefix(String code){
		if(code.charAt(0) == '6'){
			return "SH"+code;
		}else{
			return "SZ"+code;
		}
	}
  

	public static void  downloadAll(ArrayList<SnakeInfo> list){
		try {
			for(int i=0;i<list.size();i++){
				String json = downloadJson(list.get(i).code);
				if(json != null) {
				    try {
						List<JYAQData> data = jsonToArrayList(json, JYAQData.class);
						list.get(i).jyaqList = data;
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static String downloadJson(String code){
		String code1 = addPrefix(code);
		String url = URL+code1;
		String s = HttpRequest.sendGet(url, null,null);
		if(s == null || s.isEmpty()) {
			return null;
		}
		System.out.println(s);
		return s;
	}

	public static <T> ArrayList<T> jsonToArrayList(String json, Class<T> clazz) {
		Type type = new TypeToken<ArrayList<JsonObject>>() {
		}.getType();
		ArrayList<JsonObject> jsonObjects = new Gson().fromJson(json, type);

		ArrayList<T> arrayList = new ArrayList<>();
		for (JsonObject jsonObject : jsonObjects) {
			arrayList.add(new Gson().fromJson(jsonObject, clazz));
		}
		return arrayList;
	}	

	public static String getCommentByIndex(int index){
		String s="异常";
		switch(index){
		case 1:
			s="B点";
			break;
		case 2:
			s="S点";
			break;
		case 3:
			s="逢高兑现";
			break;
		case 4:
			s="逢低关注";
			break;
		case 5:
			s="上涨阶段";
			break;
		case 6:
			s="下跌阶段";
			break;
		}
		return s;
	}
	public static int getIndex(JYAQData data){
		if(data == null) {
			return 0;
		}
		if(data.f1.equals("1")){
			return 1;
		}else if(data.f2.equals("1")){
			return 2;
		}else if(data.f3.equals("1")){
			return 3;
		}else if(data.f4.equals("1")){
			return 4;
		}else if(data.f5.equals("1")){
			return 5;
		}else if(data.f6.equals("1")){
			return 6;
		}else if(data.f7.equals("1")){
			return 7;
		}else if(data.f8.equals("1")){
			return 8;
		}else if(data.f9.equals("1")){
			return 9;
		}else if(data.f10.equals("1")){
			return 10;
		}else if(data.f11.equals("1")){
			return 11;
		}else if(data.f12.equals("1")){
			return 12;
		}else if(data.f13.equals("1")){
			return 13;
		}else if(data.f14.equals("1")){
			return 14;
		}else if(data.f15.equals("1")){
			return 15;
		}else if(data.f16.equals("1")){
			return 16;
		}
		return 0;
	}
    
	public static String getComment(List<JYAQData> jyaqList){
		if(jyaqList == null || jyaqList.size() == 0) {
			return "";
		}
		return getCommentByIndex(getIndex(jyaqList.get(0)));
	}
	public static String removePrefix(String code){
		return code.substring(2);
	}

//	JiaoYiAnQuan.PackData
}
