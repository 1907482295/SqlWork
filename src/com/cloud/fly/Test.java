package com.cloud.fly;
import java.sql.Connection;  
import java.sql.DriverManager;  
import java.sql.ResultSet;  
import java.sql.SQLException;  
import java.sql.Statement; 
public class Test {
	static Connection conn;  
    static Statement st; 
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//query();
		try {
			Procedure.createShareResultDayTable(getConnection(), "20170228");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/* 查询数据库，输出符合要求的记录的情况 */  
    public static void query() {  
        // 同样先要获取连接，即连接到数据库  
        conn = getConnection();   
        try {  
            // 查询数据的sql语句  
            String sql = "select * from new_table";   
            // 创建用于执行静态sql语句的Statement对象，st属局部变量  
            st = (Statement) conn.createStatement();   
            // 执行sql查询语句，返回查询数据的结果集  
            ResultSet rs = st.executeQuery(sql);   
            System.out.println("最后的查询结果为：");  
            while (rs.next()) { // 判断是否还有下一个数据  
                // 根据字段名获取相应的值  
                String name = rs.getString("name");  
                String sex = rs.getString("sex");  
                // 输出查到的记录的各个字段的值  
                System.out.println(name + " " + sex);  
            }  
            // 释放所连接的数据库及资源  
            st.close();  
            conn.close(); // 关闭数据库连接  
        } catch (SQLException e) {  
            System.out.println("查询数据失败");  
        }  
    }  
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
}
