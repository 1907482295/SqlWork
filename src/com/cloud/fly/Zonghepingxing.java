package com.cloud.fly;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

public class Zonghepingxing {
	public static String URL = "http://h5sm.upchinapro.com/getSpeedDialExpr?gscode=%25E7%25BB%25BC%25E5%2590%2588%25E8%25AF%2584%25E6%2598%259F_%25E4%25B9%259D%25E5%25AE%25AB%25E5%259B%25BE&gpcode=";
	public static void main(String[] args) {		
		//downloadJYAQ();
		//downloadJson("000900");
	}
	public static void downloadZHPX(ArrayList<SnakeInfo> list){
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
						list.get(i).zhpxList = data;
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
	public static int getData(List<JYAQData> list, int index){
		if(list == null || list.size() <= 0) {
			return 0;
		}
		JYAQData data = list.get(0);
		if(data == null) {
			return 0;
		}
		switch(index) {
		case 0:
			return Integer.parseInt(data.f1);
		case 1:
			return Integer.parseInt(data.f2);
		case 2:
			return Integer.parseInt(data.f3);
		}
		return 0;
	}
    
	
	public static String removePrefix(String code){
		return code.substring(2);
	}
}
