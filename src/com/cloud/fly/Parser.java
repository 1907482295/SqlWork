package com.cloud.fly;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class Parser {
	public static List<FutureInfo> parse(String str) {
		if(str == null) {
			return null;
		}
//		FutureInfo entity = new Gson().fromJson(str, FutureInfo.class);
		
		List<FutureInfo> ps = new Gson().fromJson(str, new TypeToken<List<FutureInfo>>(){}.getType());
		return ps;
	}
}
