package com.cloud.fly;

import java.util.ArrayList;

public class ShareWork {
	final static String TABLE_DATE = "20170306";
	final static String TABLE_FULL_NAME = "share_results_"+TABLE_DATE;
	
	public enum DATA_CLASS{
		BASE_INFO,
		PROB,
		CLOSE_AND_PANKOU,
		JYAQ
	};
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//query();
		insertShareSnakeInfo();
//		downloadAndInsertShareAllInfo(TABLE_FULL_NAME, DATA_CLASS.BASE_INFO);
//		downloadAndInsertShareAllInfo(TABLE_FULL_NAME, DATA_CLASS.PROB);
//		downloadAndInsertShareAllInfo(TABLE_FULL_NAME, DATA_CLASS.CLOSE_AND_PANKOU);
//		downloadAndInsertShareAllInfo(TABLE_FULL_NAME, DATA_CLASS.JYAQ);
	}
	public static void insertShareSnakeInfo() {
		String tableName = createTable();	
		ArrayList<SnakeInfo> list = GPInfoGet.getAllSnake();
		WebDownload.gotAllSnakeFuture(list);
		JiaoYiAnQuan.downloadAll(list);
		BagDownload.downloadAllBag(list);
		for (int i = 0; i < list.size(); i++) {
			DBUtil.insertShareInfo(tableName, list.get(i));
		}
	}
	public static void downloadAndInsertShareAllInfo(String tableName, DATA_CLASS dataClass) {
		ArrayList<SnakeInfo> list = GPInfoGet.getAllSnake();
		switch (dataClass) {
		case BASE_INFO:
			createTable();
			insertShareInfo(list, tableName, DATA_CLASS.BASE_INFO);
			break;
		case CLOSE_AND_PANKOU:
			BagDownload.downloadAllBag(list);			
			insertShareInfo(list,tableName, dataClass);
			break;
		case PROB:
			WebDownload.gotAllSnakeFuture(list);
			insertShareInfo(list,tableName, dataClass);
			break;
		case JYAQ:
			JiaoYiAnQuan.downloadAll(list);
			insertShareInfo(list,tableName, dataClass);
			break;
		}
	}
	public static String createTable(){
		String tableName = null;
		try {
			tableName = Procedure.createShareResultDayTable(DBUtil.getConnection(), TABLE_DATE);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}				
		return tableName;
	}
	public static void insertShareInfo(ArrayList<SnakeInfo> list,String tableName, DATA_CLASS dataClass){
		for (int i = 0; i < list.size(); i++) {
			switch (dataClass) {
			case BASE_INFO:
				DBUtil.insertBaseShareInfo(tableName, list.get(i));
				break;
			case PROB:
				DBUtil.updateProbInfo(tableName, list.get(i));
				break;
			case JYAQ:
				DBUtil.updateJYAQInfo(tableName, list.get(i));
				break;
			case CLOSE_AND_PANKOU:
				DBUtil.updateCloseInfo(tableName, list.get(i));
				DBUtil.updatePankouInfo(tableName, list.get(i));
				break;
			}
		}	
	}
}
