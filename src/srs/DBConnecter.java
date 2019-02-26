package srs;

import java.sql.*;

public class DBConnecter {
	static Connection Con=null;		//创建连接的对象	
	
	static String driverName="com.microsoft.sqlserver.jdbc.SQLServerDriver";
	static String dbURL="jdbc:sqlserver://localhost:1433;database=srsDB";
	static String userName="srsuser";
	static String userPwd="12345";  
	
	public Connection connectDB() {			//实例方法
		try {
			Class.forName(driverName);
			Con=DriverManager.getConnection(dbURL, userName, userPwd);
			System.out.println("连接数据库成功");
		}
		catch(Exception e) {
			e.printStackTrace();
			System.out.print("连接失败");
		}
		return Con;
	}
}
