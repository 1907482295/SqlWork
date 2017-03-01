package com.cloud.fly;

import java.sql.CallableStatement;
import java.sql.Connection;

public class Procedure {
	public static String createShareResultDayTable(Connection con, String date) throws Exception {
		CallableStatement cs;
		cs = con.prepareCall("{CALL share_creatDayTbProc(?,?)}");
		// 设置参数
		cs.setString(1, date);
		// 注册输出参数
		cs.registerOutParameter(2, java.sql.Types.VARCHAR);
		// 执行过程
		cs.execute();
		// 获取结果
		String result = cs.getString(2);
		System.out.println("结果为:" + result);
		cs.close();
		con.close();
		return result;
	}
}
