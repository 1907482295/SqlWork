package com.cloud.fly;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

public class SharePriceFetch {
	public static String URL = "http://h5sm.upchinapro.com/yzyq_ser/yq_a.ashx?gpcode=";
	public static void main(String[] args) {		
		//downloadJYAQ();
		//downloadJson("000900");
	}
	public static void downloadSharePrice(ArrayList<SnakeInfo> list){
		downloadAll(list);
	}
	
	public static String addPrefix(String code){
		if(code.charAt(0) == '6'){
			return "01"+code;
		}else{
			return "00"+code;
		}
	}
  

	public static void  downloadAll(ArrayList<SnakeInfo> list){
		try {
			for(int i=0;i<list.size();i++){
				String json = downloadJson(list.get(i).code);
				if(json != null) {
				    try {
						List<SharePrice> data = jsonToArrayList(json, SharePrice.class);
						list.get(i).sharePriceList = data;
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
	
    
	public static double getPriceMax(List<SharePrice> list){
		if (list == null || list.size() <= 0) {
			return 0;
		}
		double priceMax = 0;
		for (int i = 0; i < list.size(); i++) {
			SharePrice data = list.get(i);
			if (data != null) {
				double price = 0;
				try {
					price = Double.parseDouble(data.cp);
					if(price > priceMax) {
						priceMax = price;
					}
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return priceMax;
	}
	public static double getPriceMin(List<SharePrice> list){
		if (list == null || list.size() <= 0) {
			return 0;
		}
		double priceMin = 1000;
		for (int i = 0; i < list.size(); i++) {
			SharePrice data = list.get(i);
			if (data != null) {
				double price = 0;
				try {
					price = Double.parseDouble(data.cp);
					if(price < priceMin) {
						priceMin = price;
					}
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return priceMin;
	}
	public static String removePrefix(String code){
		return code.substring(2);
	}
}
