package srs;

import java.sql.*;

public class DBConnecter {
	static Connection Con=null;		//�������ӵĶ���	
	
	static String driverName="com.microsoft.sqlserver.jdbc.SQLServerDriver";
	static String dbURL="jdbc:sqlserver://localhost:1433;database=srsDB";
	static String userName="srsuser";
	static String userPwd="12345";  
	
	public Connection connectDB() {			//ʵ������
		try {
			Class.forName(driverName);
			Con=DriverManager.getConnection(dbURL, userName, userPwd);
			System.out.println("�������ݿ�ɹ�");
		}
		catch(Exception e) {
			e.printStackTrace();
			System.out.print("����ʧ��");
		}
		return Con;
	}
}
