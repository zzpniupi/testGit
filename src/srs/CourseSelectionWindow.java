package srs;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;


public class CourseSelectionWindow extends JFrame implements ActionListener{
	
	//控件
	JTable table_coureseInfo;
	JTable table_selectedCourse;
	JTable table_courseScore;
	
	JPanel jp_coureseInfo;
	JPanel jp_selectedCourse;
	
	JScrollPane jsp_courseInfo;
	JScrollPane jsp_selectedCourse;
	
	String userid=null;
	
	CourseSelectionWindow(String userid){
		
		this.userid=userid;
		//面板	
		JTabbedPane tab = new JTabbedPane(JTabbedPane.TOP); 
		
		jp_coureseInfo=new JPanel();
		jp_selectedCourse=new JPanel();
		JPanel jp_courseScore=new JPanel();
		JButton jb_select;
		JButton jb_drop;
		
		tab.add(jp_coureseInfo,"选课");
		tab.add(jp_selectedCourse,"课程表");
		tab.add(jp_courseScore, "课程成绩");
		 
		//各个面板添加信息
		String []tablehead;
		String [][]content;
		SqlHelper SQL=new SqlHelper();
		
		String sql_text="select * from CourseInfo";
		//选课界面
		tablehead=SQL.getColumnName(sql_text);
		sql_text="select * from CourseInfo";
		content=SQL.getRecord(sql_text);
		table_coureseInfo=new JTable(content,tablehead);
		//选课结果课程界面
		sql_text="select * from CourseInfo where CourseID in (select CourseID from SelectInfo where StuID="+userid+")";
		content=SQL.getRecord(sql_text);
		table_selectedCourse=new JTable(content,tablehead);
		//查询课程成绩界面
		sql_text="select st.StuName,c.CourseName,se.Score from CourseInfo c,StudentInfo st,SelectInfo se where c.CourseID=se.CourseID and se.StuID=st.StuID and st.StuID='"+userid+"'";
		tablehead=SQL.getColumnName(sql_text);
		content=SQL.getRecord(sql_text);
		table_courseScore=new JTable(content,tablehead);
		//table.setCellSelectionEnabled(false);
		
		jb_select=new JButton("选课");
		jb_drop=new JButton("退选");
		
		//加入组件
		jsp_courseInfo=new JScrollPane(table_coureseInfo);
		jsp_selectedCourse=new JScrollPane(table_selectedCourse);
		JScrollPane jsp_courseScore=new JScrollPane(table_courseScore);
		
		jp_coureseInfo.add(jsp_courseInfo);
		jp_coureseInfo.add(jb_select);
		
		jp_selectedCourse.add(jsp_selectedCourse);
		jp_selectedCourse.add(jb_drop);
		
		jp_courseScore.add(jsp_courseScore);
		
		//添加监听
		jb_select.addActionListener(this);
		jb_select.setActionCommand("select");
		jb_drop.addActionListener(this);
		jb_drop.setActionCommand("drop");
		//加入面板
		this.add(tab);
		
		//布局设置
		this.setLayout(null);
		jp_coureseInfo.setLayout(null);
		jp_selectedCourse.setLayout(null);
		jp_courseScore.setLayout(null);
		
		tab.setBounds(0, 0, 600, 600);
		
		//面板选课
		jp_coureseInfo.setBounds(0, 0, 600, 600);
		jsp_courseInfo.setBounds(0, 0, 600, 400);
		jb_select.setBounds(270, 500, 60, 30);
		
		//面板查看课程
		jp_selectedCourse.setBounds(0, 0, 600, 600);
		jsp_selectedCourse.setBounds(0, 0, 600, 400);
		jb_drop.setBounds(270, 500, 60, 30);
		
		//显示成绩面板
		jp_courseScore.setBounds(0, 0, 600, 600);
		jsp_courseScore.setBounds(0, 0, 600, 600);
		
		//底层窗口设置
		this.setTitle("学生选课界面");
	    this.setSize(618, 647);
		this.setLocationRelativeTo(null);		//在屏幕中间显示(居中显示) 
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		//退出关闭JFrame  
	    this.setVisible(true);
	    
		/*
 

		this.setVisible(true);		//显示窗体  
*/	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO 自动生成的方法存根
		if(e.getActionCommand().equals("select")) {
			int selectedRow=table_coureseInfo.getSelectedRow();
			//int slectedCloumn=table_coureseInfo.getSelectedColumn();
			String courseID=(String) table_coureseInfo.getValueAt(selectedRow, 0);				//获取id
			String courseName=(String) table_coureseInfo.getValueAt(selectedRow, 1);			//获取name
			SqlHelper SQL=new SqlHelper();
			SQL.insertSelectInfo(this.userid, courseID);							//选课
			/*//更新选课表
			String sql_text="select * from CourseInfo";
			String []tablehead;
			String [][]content;
			tablehead=SQL.getColumnName(sql_text);
			content=SQL.getRecord(sql_text);*/
			
			this.setVisible(false);
			new CourseSelectionWindow(userid);
			/*this.jp_coureseInfo.removeAll();
			jsp_courseInfo=new JScrollPane(table_coureseInfo);
			jp_coureseInfo.add(jsp_courseInfo);
			jp_coureseInfo.repaint();
			jp_coureseInfo.validate();
			//jsp_courseInfo.setBounds(0, 0, 600, 400);
			this.jsp_courseInfo.removeAll();
			table_coureseInfo=new JTable(content,tablehead);
			jsp_courseInfo=new JScrollPane(table_coureseInfo);
			jp_coureseInfo.add(jsp_courseInfo);
			//jsp_courseInfo.setBounds(0, 0, 600, 400);
			//jp_coureseInfo.update(getGraphics());
			jp_coureseInfo.updateUI();
			//更新课程表
			sql_text="select * from CourseInfo where CourseID in (select CourseID from SelectInfo where StuID="+userid+")";;
			tablehead=SQL.getColumnName(sql_text);
			content=SQL.getRecord(sql_text);
			table_selectedCourse=new JTable(content,tablehead);
			this.jsp_selectedCourse.removeAll();
			jsp_selectedCourse=new JScrollPane(table_selectedCourse);
			jp_selectedCourse.add(jsp_selectedCourse);
			jsp_selectedCourse.setBounds(0, 0, 600, 400);
			jp_selectedCourse.update(getGraphics());
			
			JOptionPane.showMessageDialog(null, "成功选择课程："+courseName, "选课成功", JOptionPane.WARNING_MESSAGE);
			
			jp_coureseInfo.validate();
			jp_selectedCourse.validate();
			//String i=table.getValueAt(selectedRow, slectedCloumn);
			//System.out.println(this.userid);
*/		
			JOptionPane.showMessageDialog(null, "成功选择课程："+courseName, "选课成功", JOptionPane.WARNING_MESSAGE);	
		} 	
		if(e.getActionCommand().equals("drop")) {
			int selectedRow=table_selectedCourse.getSelectedRow();
			String courseID=(String) table_selectedCourse.getValueAt(selectedRow, 0);				//获取id
			String courseName=(String) table_selectedCourse.getValueAt(selectedRow, 1);			//获取name
			SqlHelper SQL=new SqlHelper();
			SQL.delete(this.userid, courseID);
			/*//更新课程表
			String sql_text="select * from CourseInfo where CourseID in (select CourseID from SelectInfo where StuID="+userid+")";;
			String []tablehead;
			String [][]content;
			tablehead=SQL.getColumnName(sql_text);
			content=SQL.getRecord(sql_text);
			table_selectedCourse=new JTable(content,tablehead);
			this.jsp_selectedCourse.removeAll();
			jsp_selectedCourse=new JScrollPane(table_selectedCourse);
			jp_selectedCourse.add(jsp_selectedCourse);
			jsp_selectedCourse.setBounds(0, 0, 600, 400);
			this.update(getGraphics());
			//更新选课表
			sql_text="select * from CourseInfo";
			tablehead=SQL.getColumnName(sql_text);
			content=SQL.getRecord(sql_text);
			table_coureseInfo=new JTable(content,tablehead);
			this.jsp_courseInfo.removeAll();
			jsp_courseInfo=new JScrollPane(table_coureseInfo);
			jp_coureseInfo.add(jsp_courseInfo);
			jsp_courseInfo.setBounds(0, 0, 600, 400);
			this.update(getGraphics());
			*/
			this.setVisible(false);
			new CourseSelectionWindow(userid);
			JOptionPane.showMessageDialog(null, "成功退选课程："+courseName, "退选成功", JOptionPane.WARNING_MESSAGE);
		}
	}

}
