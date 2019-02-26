package srs;

import java.sql.*;

public class SqlHelper {
	static Connection con;
	static Statement sql;
	static ResultSet rs;
	
	DBConnecter Connecter=new DBConnecter();		//创建连接对象，连接数据库

	
	//给出登陆账号，返回密码
	public String querypwd(String userid) {			//实例方法，创捷对象后调用
		con=Connecter.connectDB();
		//con=DBConnecter.connectDB();
		String sql_text="select userpwd from Users where userid=?";
		 //sql="select * from admin where username='"+k1.getText()+"'";
		String password = null;
		try{
			PreparedStatement presql;
			presql=con.prepareStatement(sql_text);
			presql.setString(1, userid);
			rs=presql.executeQuery();
			rs.next();
			password=rs.getString(1);
			//close(con,sql,rs);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return password;
	}
	
	//给出登陆账号，返回是学生还是老师 
	//flag=1 学生    flag=0 老师
	public int queryflag(String userid) {
		con=Connecter.connectDB();
		//String SQL="select stuflag from Users where userid='"+userid+"'";
		String SQL="select stuflag from Users where userid=?";
		int flag=-1;			//
		try{
			PreparedStatement presql;
			presql=con.prepareStatement(SQL);
			presql.setString(1, userid);
			//sql=con.createStatement();
			rs=presql.executeQuery();
			rs.next();
			flag=rs.getInt(1);
			//close(con,sql,rs);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	
	//获取课程表列名 				
	//可封装成为查询指定表，返回不同列名与数据的方法
	public String[] getColumnName(String sql_text){
		con=Connecter.connectDB();
		//String sql_text="select * from CourseInfo";
		String []columnName = null;
		try {
			sql=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			rs=sql.executeQuery(sql_text);
			ResultSetMetaData metaData = rs.getMetaData();
			//System.out.println(metaData);
			int columnCount=metaData.getColumnCount();		//获取数据的列数
			columnName=new String[columnCount];
			 for(int i=1;i<=columnCount;i++){		//获取列名
		            columnName[i-1]=metaData.getColumnName(i);		
		        } 
			 con.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		 if(columnName ==null ){
	           System.out.println("先查询记录");
	           return null;
	       }
	       return columnName;
	}
	
	//获取课程表数据
	public String[][] getRecord(String sql_text){
		con=Connecter.connectDB();
		//String sql_text="select * from CourseInfo";
		String [][]record = null;
		try {
			sql=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			rs=sql.executeQuery(sql_text);
			ResultSetMetaData metaData = rs.getMetaData();
			int columnCount=metaData.getColumnCount();		//获取数据的列数
			rs.last();
			int recordAmount=rs.getRow();			//获取数据的行数
			record=new String[recordAmount][columnCount];
			int i=0;
	        rs.beforeFirst();
	     /*   
	        String resultRow = "";
	        for (i = 1; i < columnCount; i++) { 
	        	resultRow += metaData.getColumnName(i) + ";"; 
	        	} 
	        	System.out.println(resultRow); 
	        	*/
	        while(rs.next()) { 
	          for(int j=1;j<=columnCount;j++){
	             record[i][j-1]=rs.getString(j); //第i条记录，放入二维数组的第i行
	          }
	          i++;
	        }
	        con.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		 return record;
	}
	
	//插入选课记录，并减少可选数量
	public void insertSelectInfo(String stuID,String courseID) {
		con=Connecter.connectDB();
		String sql_text="insert into SelectInfo values(?,?,null)";
		String sql_decrease="update CourseInfo set AvailableStuNum=AvailableStuNum-1 where CourseID=?";
		try{
			PreparedStatement presql;
			//插入记录
			presql=con.prepareStatement(sql_text);
			presql.setString(1, stuID);
			presql.setString(2, courseID);
			presql.executeUpdate();
			//减少可选人数
			presql=con.prepareStatement(sql_decrease);
			
			presql.setString(1, courseID);
			presql.executeUpdate();
			con.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	//删除选定的课程记录，并更新可选人数
	public void delete(String stuID,String courseID) {
		con=Connecter.connectDB();
		String sql_text="delete SelectInfo where StuID=? and CourseID=?";
		String sql_increase="update CourseInfo set AvailableStuNum=AvailableStuNum+1 where CourseID=?";
		try{
			PreparedStatement presql;
			//插入记录
			presql=con.prepareStatement(sql_text);
			presql.setString(1, stuID);
			presql.setString(2, courseID);
			presql.executeUpdate();
			//减少可选人数
			presql=con.prepareStatement(sql_increase);
			
			presql.setString(1, courseID);
			presql.executeUpdate();
			con.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	//只执行传入sql语句，无其他功能  可供其他地方调用
	public void insertScore(String sql_text) {
		con=Connecter.connectDB();
		try {
			sql=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			sql.executeUpdate(sql_text);
			 con.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	//
	
	//关闭连接 con,sql,rs
	static void close(Connection con,Statement sql,ResultSet rs) throws SQLException {
		con.close();
		sql.close();
		rs.close();
	}
/*	public class Query {
	   String databaseName="";    	//数据库名
	   String SQL;        		//SQL语句
	   String [] columnName;        //全部字段（列）名
	   String [][] record;          //查询到的记录
	   public Query() {
	      try{  Class.forName("com.mysql.jdbc.Driver");//加载JDBC-MySQL驱动
	      }
	      catch(Exception e){}
	   }
	   public void setDatabaseName(String s) {
	      databaseName=s.trim();
	   }
	   public void setSQL(String SQL) {
	      this.SQL=SQL.trim();
	   }
	   public String[] getColumnName() {
	       if(columnName ==null ){
	           System.out.println("先查询记录");
	           return null;
	       }
	       return columnName;
	   }
	   public String[][] getRecord() {
	       startQuery();
	       return record;
	   }
	   private void startQuery() { 
	      Connection con;
	      Statement sql;  
	      ResultSet rs;
	      String uri = 
	     "jdbc:mysql://localhost:3306/"+
	      databaseName+"?useSSL=true&characterEncoding=utf-8";
	      try { 
	        con=DriverManager.getConnection(uri,"root","");
	        sql=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
	                                ResultSet.CONCUR_READ_ONLY);
	        rs=sql.executeQuery(SQL);
	        ResultSetMetaData metaData = rs.getMetaData();
	        int columnCount = metaData.getColumnCount();//字段数目 
	        
	        columnName=new String[columnCount]; 
	        for(int i=1;i<=columnCount;i++){		//获取列名
	            columnName[i-1]=metaData.getColumnName(i);		
	        } 
	        rs.last(); 
	        int recordAmount =rs.getRow();  //结果集中的记录数目
	        record = new String[recordAmount][columnCount];
	        int i=0;
	        rs.beforeFirst();
	        while(rs.next()) { 
	          for(int j=1;j<=columnCount;j++){
	             record[i][j-1]=rs.getString(j); //第i条记录，放入二维数组的第i行
	          }
	          i++;
	        }
	        con.close();
	      }
	      catch(SQLException e) {
	        System.out.println("请输入正确的表名"+e);
	      }
	   }    
	}*/
	
	/*public static void main(String [] args)
	 {
		Connection Con=null;		//创建连接的对象	
		Statement sql;				//SQL语句对象
		ResultSet rs;				//内存中的结果
		String driverName="com.microsoft.sqlserver.jdbc.SQLServerDriver";
		String dbURL="jdbc:sqlserver://localhost:1433;database=srsDB";
		String userName="srsuser";
		String userPwd="12345";  
		try {
			Class.forName(driverName);
			Con=DriverManager.getConnection(dbURL, userName, userPwd);
			System.out.println("连接数据库成功");
		}
		catch(Exception e) {
			e.printStackTrace();
			System.out.print("连接失败");
		}
		finally {
			//Con.close();
		}
		try {
			sql=Con.createStatement();
			rs=sql.executeQuery("SELECT *FROM Users");
			while(rs.next()) {
				String userid=rs.getString(1);
				String userpwd=rs.getString(2);
				System.out.println(userid);
				System.out.println(userpwd);
			}
			Con.close();
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	 }*/
}
