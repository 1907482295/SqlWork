package com.cloud.fly;

import java.util.ArrayList;

public class ShareWork {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//query();
		insertShareSnakeInfo();
	}
	public static void insertShareSnakeInfo() {
		String tableName = null;
		try {
			tableName = Procedure.createShareResultDayTable(DBUtil.getConnection(), "20170301");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}		
		ArrayList<SnakeInfo> list = GPInfoGet.getAllSnake();
		WebDownload.gotAllSnakeFuture(list);
		JiaoYiAnQuan.downloadAll(list);
		BagDownload.downloadAllBag(list);
		for (int i = 0; i < list.size(); i++) {
			DBUtil.insertShareInfo(tableName, list.get(i));
		}
	}
}
