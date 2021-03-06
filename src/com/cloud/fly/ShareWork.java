package com.cloud.fly;

import java.util.ArrayList;

public class ShareWork {
	final static String TABLE_DATE = "20170316";
	final static String TABLE_FULL_NAME = "share_results_"+TABLE_DATE;
	
	public enum DATA_CLASS{
		BASE_INFO, //基本信息
		PROB,    //上涨概率信息
		CLOSE_AND_PANKOU,//收盘价，昨日收盘价，盘口信息
		JYAQ,//交易安全，买卖点，上涨阶段，下跌阶段，观察阶段，逢高抛空阶段
		ZHPX,//综合评星，资金面，基本面，技术面
		QSDX,//趋势动向，下行，盘整，上行
		ZCYL,//支撑位，压力位，
		RQ,//人气，昨日人气，今日人气，人气活跃状态
		EPS,//EPS
		HISTORY_PRICE
	};
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//query();
//		insertShareSnakeInfo();
		downloadAndInsertShareAllInfo(TABLE_FULL_NAME, DATA_CLASS.BASE_INFO);
		downloadAndInsertShareAllInfo(TABLE_FULL_NAME, DATA_CLASS.CLOSE_AND_PANKOU);
		downloadAndInsertShareAllInfo(TABLE_FULL_NAME, DATA_CLASS.JYAQ);
		downloadAndInsertShareAllInfo(TABLE_FULL_NAME, DATA_CLASS.ZHPX);
		downloadAndInsertShareAllInfo(TABLE_FULL_NAME, DATA_CLASS.QSDX);
		downloadAndInsertShareAllInfo(TABLE_FULL_NAME, DATA_CLASS.ZCYL);
		downloadAndInsertShareAllInfo(TABLE_FULL_NAME, DATA_CLASS.RQ);
		downloadAndInsertShareAllInfo(TABLE_FULL_NAME, DATA_CLASS.EPS);
		downloadAndInsertShareAllInfo(TABLE_FULL_NAME, DATA_CLASS.HISTORY_PRICE);
		downloadAndInsertShareAllInfo(TABLE_FULL_NAME, DATA_CLASS.PROB);
		System.out.println("finish ...");
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
		case ZHPX:
			Zonghepingxing.downloadAll(list);
			insertShareInfo(list,tableName, dataClass);
			break;
		case QSDX:
			QuShiDongXiang.downloadAll(list);
			insertShareInfo(list,tableName, dataClass);
			break;			
		case ZCYL:
			ZhiChengYaLi.downloadAll(list);
			insertShareInfo(list,tableName, dataClass);
			break;			
		case RQ:
			RenQi.downloadAll(list);
			insertShareInfo(list,tableName, dataClass);
			break;			
		case EPS:
			EpsFetch.downloadAll(list);
			insertShareInfo(list,tableName, dataClass);
			break;	
		case HISTORY_PRICE:
			SharePriceFetch.downloadAll(list);
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
			case ZHPX:
				DBUtil.updateZHPXInfo(tableName, list.get(i));
				break;
			case QSDX:
				DBUtil.updateQSDXInfo(tableName, list.get(i));
				break;
			case ZCYL:
				DBUtil.updateZCYLInfo(tableName, list.get(i));
				break;
			case RQ:
				DBUtil.updateRQInfo(tableName, list.get(i));
				break;
			case EPS:
				DBUtil.updateEPSInfo(tableName, list.get(i));
				break;
			case HISTORY_PRICE:
				DBUtil.updateSharePriceInfo(tableName, list.get(i));
				break;
			case CLOSE_AND_PANKOU:
				DBUtil.updateCloseInfo(tableName, list.get(i));
				DBUtil.updatePankouInfo(tableName, list.get(i));
				break;
			}
		}	
	}
}
