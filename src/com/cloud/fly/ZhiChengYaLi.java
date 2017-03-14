package com.cloud.fly;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

public class ZhiChengYaLi {
	public static String URL = "http://h5sm.upchinapro.com/getSpeedDialExpr?gscode=%25E8%25B6%258B%25E5%258A%25BF%25E5%25AE%2589%25E5%2585%25A8-%25E6%2594%25AF%25E6%2592%2591%25E5%258E%258B%25E5%258A%259B&gpcode=";
	public static void main(String[] args) {		
		//downloadJYAQ();
		//downloadJson("000900");
	}
	public static void downloadZCYL(ArrayList<SnakeInfo> list){
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
						list.get(i).zcylList = data;
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

	public static double getData(List<JYAQData> list, int index){
		if(list == null || list.size() <= 0) {
			return 0;
		}
		JYAQData data = list.get(0);
		if(data == null) {
			return 0;
		}
		switch(index) {
		case 0:
			return Double.parseDouble(data.f1);
		case 1:
			return Double.parseDouble(data.f2);
		}
		return 0;
	}
    
	
	public static String removePrefix(String code){
		return code.substring(2);
	}
}
