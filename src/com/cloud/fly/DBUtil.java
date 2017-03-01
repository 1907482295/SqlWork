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
		String sqlFormat = "INSERT INTO %s (%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
		String sql = String.format(sqlFormat, tableName, "share_no", "share_name", "prob_1", "prob_5", "prob_10",
				"prob_20", "close", "preClose","buyPankou" ,"sellPankou","jyaq");

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
