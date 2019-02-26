package srs;

import java.sql.*;

public class SqlHelper {
	static Connection con;
	static Statement sql;
	static ResultSet rs;
	
	DBConnecter Connecter=new DBConnecter();		//�������Ӷ����������ݿ�

	
	//������½�˺ţ���������
	public String querypwd(String userid) {			//ʵ�����������ݶ�������
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
	
	//������½�˺ţ�������ѧ��������ʦ 
	//flag=1 ѧ��    flag=0 ��ʦ
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
	
	//��ȡ�γ̱����� 				
	//�ɷ�װ��Ϊ��ѯָ�������ز�ͬ���������ݵķ���
	public String[] getColumnName(String sql_text){
		con=Connecter.connectDB();
		//String sql_text="select * from CourseInfo";
		String []columnName = null;
		try {
			sql=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			rs=sql.executeQuery(sql_text);
			ResultSetMetaData metaData = rs.getMetaData();
			//System.out.println(metaData);
			int columnCount=metaData.getColumnCount();		//��ȡ���ݵ�����
			columnName=new String[columnCount];
			 for(int i=1;i<=columnCount;i++){		//��ȡ����
		            columnName[i-1]=metaData.getColumnName(i);		
		        } 
			 con.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		 if(columnName ==null ){
	           System.out.println("�Ȳ�ѯ��¼");
	           return null;
	       }
	       return columnName;
	}
	
	//��ȡ�γ̱�����
	public String[][] getRecord(String sql_text){
		con=Connecter.connectDB();
		//String sql_text="select * from CourseInfo";
		String [][]record = null;
		try {
			sql=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			rs=sql.executeQuery(sql_text);
			ResultSetMetaData metaData = rs.getMetaData();
			int columnCount=metaData.getColumnCount();		//��ȡ���ݵ�����
			rs.last();
			int recordAmount=rs.getRow();			//��ȡ���ݵ�����
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
	             record[i][j-1]=rs.getString(j); //��i����¼�������ά����ĵ�i��
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
	
	//����ѡ�μ�¼�������ٿ�ѡ����
	public void insertSelectInfo(String stuID,String courseID) {
		con=Connecter.connectDB();
		String sql_text="insert into SelectInfo values(?,?,null)";
		String sql_decrease="update CourseInfo set AvailableStuNum=AvailableStuNum-1 where CourseID=?";
		try{
			PreparedStatement presql;
			//�����¼
			presql=con.prepareStatement(sql_text);
			presql.setString(1, stuID);
			presql.setString(2, courseID);
			presql.executeUpdate();
			//���ٿ�ѡ����
			presql=con.prepareStatement(sql_decrease);
			
			presql.setString(1, courseID);
			presql.executeUpdate();
			con.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	//ɾ��ѡ���Ŀγ̼�¼�������¿�ѡ����
	public void delete(String stuID,String courseID) {
		con=Connecter.connectDB();
		String sql_text="delete SelectInfo where StuID=? and CourseID=?";
		String sql_increase="update CourseInfo set AvailableStuNum=AvailableStuNum+1 where CourseID=?";
		try{
			PreparedStatement presql;
			//�����¼
			presql=con.prepareStatement(sql_text);
			presql.setString(1, stuID);
			presql.setString(2, courseID);
			presql.executeUpdate();
			//���ٿ�ѡ����
			presql=con.prepareStatement(sql_increase);
			
			presql.setString(1, courseID);
			presql.executeUpdate();
			con.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	//ִֻ�д���sql��䣬����������  �ɹ������ط�����
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
	
	//�ر����� con,sql,rs
	static void close(Connection con,Statement sql,ResultSet rs) throws SQLException {
		con.close();
		sql.close();
		rs.close();
	}
/*	public class Query {
	   String databaseName="";    	//���ݿ���
	   String SQL;        		//SQL���
	   String [] columnName;        //ȫ���ֶΣ��У���
	   String [][] record;          //��ѯ���ļ�¼
	   public Query() {
	      try{  Class.forName("com.mysql.jdbc.Driver");//����JDBC-MySQL����
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
	           System.out.println("�Ȳ�ѯ��¼");
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
	        int columnCount = metaData.getColumnCount();//�ֶ���Ŀ 
	        
	        columnName=new String[columnCount]; 
	        for(int i=1;i<=columnCount;i++){		//��ȡ����
	            columnName[i-1]=metaData.getColumnName(i);		
	        } 
	        rs.last(); 
	        int recordAmount =rs.getRow();  //������еļ�¼��Ŀ
	        record = new String[recordAmount][columnCount];
	        int i=0;
	        rs.beforeFirst();
	        while(rs.next()) { 
	          for(int j=1;j<=columnCount;j++){
	             record[i][j-1]=rs.getString(j); //��i����¼�������ά����ĵ�i��
	          }
	          i++;
	        }
	        con.close();
	      }
	      catch(SQLException e) {
	        System.out.println("��������ȷ�ı���"+e);
	      }
	   }    
	}*/
	
	/*public static void main(String [] args)
	 {
		Connection Con=null;		//�������ӵĶ���	
		Statement sql;				//SQL������
		ResultSet rs;				//�ڴ��еĽ��
		String driverName="com.microsoft.sqlserver.jdbc.SQLServerDriver";
		String dbURL="jdbc:sqlserver://localhost:1433;database=srsDB";
		String userName="srsuser";
		String userPwd="12345";  
		try {
			Class.forName(driverName);
			Con=DriverManager.getConnection(dbURL, userName, userPwd);
			System.out.println("�������ݿ�ɹ�");
		}
		catch(Exception e) {
			e.printStackTrace();
			System.out.print("����ʧ��");
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
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
	 }*/
}
