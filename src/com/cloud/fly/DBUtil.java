package com.cloud.fly;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class DBUtil {
	/* 获取数据库连接的函数 */
	public static Connection getConnection() {
		// 创建用于连接数据库的Connection对象
		Connection con = null;
		try {
			// 加载Mysql数据驱动
			Class.forName("com.mysql.jdbc.Driver");
			// 创建数据连接
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ShareDB", "root", "qwerty");
		} catch (Exception e) {
			System.out.println("数据库连接失败" + e.getMessage());
		}
		// 返回所建立的数据库连接
		return con;
	}

	public static double getProb(List<FutureInfo> list, int i) {
		if(list == null || i >= list.size()) {
			return 0;
		}
		return list.get(i).UP_PROB;
	}
	public static int insertShareInfo(String tableName, SnakeInfo info) {
		int i = 0;
		String sqlFormat = "INSERT INTO %s (%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		String sql = String.format(sqlFormat, tableName, "share_no", "share_name", "prob_1", "prob_5", "prob_10",
				"prob_20", "close", "preClose","buyPankou" ,"sellPankou","jyaq","jibenmian","zijinmian","jishumian");

		Connection cnn = getConnection();

		try {
			PreparedStatement preStmt = cnn.prepareStatement(sql);
			preStmt.setString(1, info.code);
			preStmt.setString(2, info.name);
			int index = 0;
			for(i = 0;i<4;i++){
				index= 3 + i;
			    preStmt.setDouble(index, getProb(info.futureList, i));
			}
			preStmt.setDouble(index+1, BagDownload.getClose(info));
			preStmt.setDouble(index+2, BagDownload.getPreClose(info));
			preStmt.setString(index+3, BagDownload.getBuyPankou(info));
			preStmt.setString(index+4, BagDownload.getSellPankou(info));
			preStmt.setString(index+5, JiaoYiAnQuan.getComment(info.jyaqList));
			preStmt.setInt(index+6, Zonghepingxing.getData(info.zhpxList,0));
			preStmt.setInt(index+7, Zonghepingxing.getData(info.zhpxList,1));
			preStmt.setInt(index+8, Zonghepingxing.getData(info.zhpxList,2));
			i = preStmt.executeUpdate();
			preStmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			try {
				cnn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return i;// 返回影响的行数，1为执行成功
	}
	public static int insertBaseShareInfo(String tableName, SnakeInfo info) {
		int i = 0;
		String sqlFormat = "INSERT INTO %s (%s,%s) VALUES (?,?)";
		String sql = String.format(sqlFormat, tableName, "share_no", "share_name");

		Connection cnn = getConnection();

		try {
			PreparedStatement preStmt = cnn.prepareStatement(sql);
			preStmt.setString(1, info.code);
			preStmt.setString(2, info.name);
			i = preStmt.executeUpdate();
			preStmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			try {
				cnn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return i;// 返回影响的行数，1为执行成功
	}
	public static int updateProbInfo(String tableName, SnakeInfo info) {
		int i = 0;
		String sqlFormat = "UPDATE %s SET %s=?,%s=?,%s=?,%s=? WHERE %s=?";
		String sql = String.format(sqlFormat, tableName, "prob_1", "prob_5", "prob_10", "prob_20", "share_no");

		Connection cnn = getConnection();

		try {
			PreparedStatement preStmt = cnn.prepareStatement(sql);
			for(i = 0;i<4;i++){
			    preStmt.setDouble(i+1, getProb(info.futureList, i));			    
			}			
			preStmt.setString(5, info.code);
			i = preStmt.executeUpdate();
			preStmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			try {
				cnn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return i;// 返回影响的行数，1为执行成功
	}
	public static int updateCloseInfo(String tableName, SnakeInfo info) {
		int i = 0;
		String sqlFormat = "UPDATE %s SET %s=?,%s=? WHERE %s=?";
		String sql = String.format(sqlFormat, tableName, "close", "preClose","share_no");

		Connection cnn = getConnection();

		try {
			PreparedStatement preStmt = cnn.prepareStatement(sql);
			preStmt.setDouble(1, BagDownload.getClose(info));
			preStmt.setDouble(2, BagDownload.getPreClose(info));
			preStmt.setString(3, info.code);
			i = preStmt.executeUpdate();
			preStmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			try {
				cnn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return i;// 返回影响的行数，1为执行成功
	}
	public static int updatePankouInfo(String tableName, SnakeInfo info) {
		int i = 0;
		String sqlFormat = "UPDATE %s SET %s=?,%s=? WHERE %s=?";
		String sql = String.format(sqlFormat, tableName, "buyPankou", "sellPankou","share_no");

		Connection cnn = getConnection();

		try {
			PreparedStatement preStmt = cnn.prepareStatement(sql);
			preStmt.setString(1, BagDownload.getBuyPankou(info));
			preStmt.setString(2, BagDownload.getSellPankou(info));
			preStmt.setString(3, info.code);
			i = preStmt.executeUpdate();
			preStmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			try {
				cnn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return i;// 返回影响的行数，1为执行成功
	}
	public static int updateJYAQInfo(String tableName, SnakeInfo info) {
		int i = 0;
		String sqlFormat = "UPDATE %s SET %s=? WHERE %s=?";
		String sql = String.format(sqlFormat, tableName, "jyaq","share_no");

		Connection cnn = getConnection();

		try {
			PreparedStatement preStmt = cnn.prepareStatement(sql);
			preStmt.setString(1, JiaoYiAnQuan.getComment(info.jyaqList));
			preStmt.setString(2, info.code);
			i = preStmt.executeUpdate();
			preStmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			try {
				cnn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return i;// 返回影响的行数，1为执行成功
	}
	public static int updateZHPXInfo(String tableName, SnakeInfo info) {
		int i = 0;
		String sqlFormat = "UPDATE %s SET %s=?,%s=?,%s=? WHERE %s=?";
		String sql = String.format(sqlFormat, tableName, "jibenmian", "zijinmian","jishumian" ,"share_no");

		Connection cnn = getConnection();

		try {
			PreparedStatement preStmt = cnn.prepareStatement(sql);
			preStmt.setInt(1, Zonghepingxing.getData(info.zhpxList, 0));
			preStmt.setInt(2, Zonghepingxing.getData(info.zhpxList, 1));
			preStmt.setInt(3, Zonghepingxing.getData(info.zhpxList, 2));
			preStmt.setString(4, info.code);
			i = preStmt.executeUpdate();
			preStmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			try {
				cnn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return i;// 返回影响的行数，1为执行成功
	}
	
	public static int updateQSDXInfo(String tableName, SnakeInfo info) {
		int i = 0;
		String sqlFormat = "UPDATE %s SET %s=? WHERE %s=?";
		String sql = String.format(sqlFormat, tableName, "qushidongxiang","share_no");

		Connection cnn = getConnection();

		try {
			PreparedStatement preStmt = cnn.prepareStatement(sql);
			preStmt.setDouble(1, QuShiDongXiang.getData(info.qsdxList));
			preStmt.setString(2, info.code);
			i = preStmt.executeUpdate();
			preStmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			try {
				cnn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return i;// 返回影响的行数，1为执行成功
	}
	public static int updateZCYLInfo(String tableName, SnakeInfo info) {
		int i = 0;
		String sqlFormat = "UPDATE %s SET %s=?,%s=? WHERE %s=?";
		String sql = String.format(sqlFormat, tableName, "yaliwei","zhichengwei","share_no");

		Connection cnn = getConnection();

		try {
			PreparedStatement preStmt = cnn.prepareStatement(sql);
			preStmt.setDouble(1, ZhiChengYaLi.getData(info.zcylList,0));
			preStmt.setDouble(2, ZhiChengYaLi.getData(info.zcylList,1));
			preStmt.setString(3, info.code);
			i = preStmt.executeUpdate();
			preStmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			try {
				cnn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return i;// 返回影响的行数，1为执行成功
	}
	public static int updateRQInfo(String tableName, SnakeInfo info) {
		int i = 0;
		String sqlFormat = "UPDATE %s SET %s=?,%s=?,%s=? WHERE %s=?";
		String sql = String.format(sqlFormat, tableName, "renqi_yesterday","renqi_today","renqi_status","share_no");

		Connection cnn = getConnection();

		try {
			PreparedStatement preStmt = cnn.prepareStatement(sql);
			preStmt.setDouble(1, RenQi.getData(info.rqList,0));
			preStmt.setDouble(2, RenQi.getData(info.rqList,1));
			preStmt.setString(3, RenQi.getData(info.rqList));
			preStmt.setString(4, info.code);
			i = preStmt.executeUpdate();
			preStmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			try {
				cnn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return i;// 返回影响的行数，1为执行成功
	}
}
