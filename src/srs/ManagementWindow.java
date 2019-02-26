package srs;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;

public class ManagementWindow extends JFrame implements ActionListener{
	
	JTable table_stuInfo;
	JTable table_manageScore;
	JTable table_addCourse;
	
	JPanel jp_manageScore;
	JPanel jp_addCourse;
	JPanel jp_new_addCourse;
	
	JScrollPane jsp_manageScore;
	JScrollPane jsp_addCourse;
	
	JLabel jl_stuName,jl_courseID,jl_score;	//录入成绩文本框的标签
	
	JTextField jtf_stuName;		//录入成绩文本框
	JTextField jtf_courseID;	
	JTextField jtf_score;	
	
	//新建课程
	JLabel jl_new_courseID;
	JLabel jl_new_courseName;
	JLabel jl_new_courseCredit;
	JLabel jl_new_totalStuNum;
	JLabel jl_new_availableStuNum;
	JLabel jl_new_classroom;
	JTextField jtf_new_courseID;	
	JTextField jtf_new_courseName;
	JTextField jtf_new_courseCredit;
	JTextField jtf_new_totalStuNum;
	JTextField jtf_new_availableStuNum;
	JTextField jtf_new_classroom;
	JButton jb_new_addcourse;
	JButton jb_new_view;
	JButton jb_new_delete;

	
	
	String teacherID=null;
	
	ManagementWindow(String teacherID){
		this.teacherID=teacherID;
		
		//录入成绩框
		jl_stuName=new JLabel("学生姓名:");
		jl_courseID=new JLabel("课程名:");	
		jl_score=new JLabel("最终成绩:");	
		jtf_stuName=new JTextField(10);
		jtf_courseID=new JTextField(10);
		jtf_score=new JTextField(10);
		
		//面板	
		JTabbedPane tab = new JTabbedPane(JTabbedPane.TOP); 
				
		JPanel jp_stuInfo=new JPanel();
		jp_manageScore=new JPanel();
		jp_addCourse=new JPanel();
		jp_new_addCourse=new JPanel();
		jp_addCourse.add(jp_new_addCourse);
		JButton jb_updateScore=new JButton("录入成绩");
		JButton jb_find=new JButton("查询信息");
		
		tab.add(jp_stuInfo,"学生信息");
		tab.add(jp_manageScore,"成绩录入");
		tab.add(jp_addCourse,"新建课程");
		
		
		//添加课程控件初始化
		jl_new_courseID=new JLabel("课程号：");
		jl_new_courseName=new JLabel("课程名：");
		jl_new_courseCredit=new JLabel("学分：");
		jl_new_totalStuNum=new JLabel("学生总数：");
		jl_new_availableStuNum=new JLabel("可选人数：");
		jl_new_classroom=new JLabel("教室号：");
	    jtf_new_courseID=new JTextField(10);	
		jtf_new_courseName=new JTextField(20);
		jtf_new_courseCredit=new JTextField(10);
		jtf_new_totalStuNum=new JTextField(10);
		jtf_new_availableStuNum=new JTextField(10);
		jtf_new_classroom=new JTextField(10);
		jb_new_addcourse=new JButton("创建课程");
		jb_new_view=new JButton("现有课程");
		jb_new_delete=new JButton("删除课程");
		
		//各个面板添加信息
		String []tablehead;
		String [][]content;
		SqlHelper SQL=new SqlHelper();
		
		//教师学生信息
		String sql_text="select st.*,c.CourseID,c.CourseName from CourseInfo c,StudentInfo st,SelectInfo se,TeacherInfo t where se.CourseID=c.CourseID and se.StuID=st.StuID and se.CourseID=t.CourseID and t.TeacherID='"+teacherID+"'";
		tablehead=SQL.getColumnName(sql_text);
		content=SQL.getRecord(sql_text);
		table_stuInfo=new JTable(content,tablehead);
		//tablehead=null;
		//content=null;
		sql_text="select st.StuID,st.StuName,c.CourseID,c.CourseName,se.Score from StudentInfo st,SelectInfo se,CourseInfo c where se.StuID=st.StuID and se.CourseID=c.CourseID and c.TeacherID="+teacherID;
		tablehead=SQL.getColumnName(sql_text);
		content=SQL.getRecord(sql_text);
		table_manageScore=new JTable(content,tablehead);
		//加入组件
		JScrollPane jsp_stuInfo=new JScrollPane(table_stuInfo);
		jsp_manageScore=new JScrollPane(table_manageScore);
		//教师课表
		sql_text="select CourseID,CourseName,CourseCredit,TotalStuNum,AvailableStuNum,Classroom from CourseInfo where TeacherID="+teacherID;
		tablehead=SQL.getColumnName(sql_text);
		content=SQL.getRecord(sql_text);
		table_addCourse=new JTable(content,tablehead);
		jsp_addCourse=new JScrollPane(table_addCourse);
		jsp_addCourse.setBorder(null);
		
		jp_stuInfo.add(jsp_stuInfo);
		jp_manageScore.add(jsp_manageScore);
		jp_manageScore.add(jb_updateScore);
		jp_manageScore.add(jb_find);
		jp_manageScore.add(jl_stuName);
		jp_manageScore.add(jl_courseID);
		jp_manageScore.add(jl_score);
		jp_manageScore.add(jtf_stuName);
		jp_manageScore.add(jtf_courseID);
		jp_manageScore.add(jtf_score);
		jp_addCourse.add(jsp_addCourse);
/*		jp_addCourse.add(jb_new_view);
		jp_addCourse.add(jb_new_delete);*/
		
		jp_new_addCourse.add(jl_new_courseID);
		jp_new_addCourse.add(jtf_new_courseID);
		jp_new_addCourse.add(jl_new_courseName);
		jp_new_addCourse.add(jtf_new_courseName);
		jp_new_addCourse.add(jl_new_courseCredit);
		jp_new_addCourse.add(jtf_new_courseCredit);
		jp_new_addCourse.add(jl_new_totalStuNum);
		jp_new_addCourse.add(jtf_new_totalStuNum);
		jp_new_addCourse.add(jl_new_availableStuNum);
		jp_new_addCourse.add(jtf_new_availableStuNum);
		jp_new_addCourse.add(jl_new_classroom);
		jp_new_addCourse.add(jtf_new_classroom);
		jp_new_addCourse.add(jb_new_addcourse);
		
		
		//添加监听
		jb_updateScore.addActionListener(this);
		jb_updateScore.setActionCommand("update");
		jb_find.addActionListener(this);
		jb_find.setActionCommand("find");
		jb_new_addcourse.addActionListener(this);
		jb_new_addcourse.setActionCommand("add");
		jb_new_view.addActionListener(this);
		jb_new_view.setActionCommand("view");
		jb_new_delete.addActionListener(this);
		jb_new_delete.setActionCommand("delete");
		
		//加入面板
		this.add(tab);
		
		//布局设置
		this.setLayout(null);
		jp_stuInfo.setLayout(null);
		jp_manageScore.setLayout(null);
		jp_addCourse.setLayout(null);
		jp_new_addCourse.setLayout(null);
		
		tab.setBounds(0, 0, 600, 600);
		
		//显示成绩面板
		jp_stuInfo.setBounds(0, 0, 600, 600);
		jsp_stuInfo.setBounds(0, 0, 600, 600);
		
		//面板录入成绩
		jp_manageScore.setBounds(0, 0, 600, 600);
		jsp_manageScore.setBounds(0,200, 600, 400);
		jb_updateScore.setBounds(450, 50, 100, 20);
		jb_find.setBounds(450, 10, 100, 20);
		jl_stuName.setBounds(10, 10, 60, 20);
		jtf_stuName.setBounds(80, 10, 100, 20);
		jl_courseID.setBounds(200, 10, 50, 20);
		jtf_courseID.setBounds(260, 10, 150, 20);
		jl_score.setBounds(10, 50, 70, 20);
		jtf_score.setBounds(80, 50, 100, 20);
		
		//添加课程面板
		jp_addCourse.setBounds(0, 0, 600, 600);
		jsp_addCourse.setBounds(0, 300, 600, 300);
		jp_new_addCourse.setBounds(0, 0, 600, 230);
		jl_new_courseID.setBounds(10, 10, 80, 20);
		jl_new_courseName.setBounds(270, 10, 80, 20);
		jl_new_courseCredit.setBounds(10, 70, 80, 20);
		jl_new_totalStuNum.setBounds(270, 70, 80, 20);
		jl_new_availableStuNum.setBounds(10, 130, 80, 20);
		jl_new_classroom.setBounds(270, 130, 80, 20);
		jtf_new_courseID.setBounds(80, 10, 120, 20);	
		jtf_new_courseName.setBounds(340, 10, 140, 20);
		jtf_new_courseCredit.setBounds(80, 70, 120, 20);
		jtf_new_totalStuNum.setBounds(340, 70, 140, 20);
		jtf_new_availableStuNum.setBounds(80, 130, 120, 20);
		jtf_new_classroom.setBounds(340, 130, 140, 20);
	    jb_new_addcourse.setBounds(230, 200, 100, 20);
/*	    jb_new_delete.setBounds(130, 500, 100, 20);
	    jb_new_delete.setBounds(360, 500, 100, 20);*/
		
		//底层窗口设置
		this.setTitle("教师管理界面");
		this.setSize(618, 647);
		this.setLocationRelativeTo(null);		//在屏幕中间显示(居中显示)  
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		//退出关闭JFrame  
		this.setVisible(true);		//显示窗体  
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO 自动生成的方法存根
		if(e.getActionCommand().equals("find")) {
			String stuName=jtf_stuName.getText().trim();
			String courseName=jtf_courseID.getText().trim();
			String sql_text="select st.StuID,st.StuName,c.CourseID,c.CourseName,se.Score from StudentInfo st,SelectInfo se,CourseInfo c where se.StuID=st.StuID and se.CourseID=c.CourseID and st.StuName='"+stuName+"' and c.CourseName='"+courseName+"'";
			String []tablehead;
			String [][]content=null;
			SqlHelper SQL=new SqlHelper();
			tablehead=SQL.getColumnName(sql_text);
			content=SQL.getRecord(sql_text);
			table_manageScore=new JTable(content,tablehead);
			//System.out.println(content);
			this.jsp_manageScore.removeAll();
			jsp_manageScore=new JScrollPane(table_manageScore);
			jp_manageScore.add(jsp_manageScore);
			jsp_manageScore.setBounds(0,200, 600, 400);
			
			//jp_manageScore.repaint();
			this.update(getGraphics());
			//jp_manageScore.setVisible(true);
		}
		if(e.getActionCommand().equals("update")) {
			String stuName=jtf_stuName.getText().trim();
			String courseName=jtf_courseID.getText().trim();
			String score=jtf_score.getText().trim();
			String sql_text="update SelectInfo set Score='"+score+"' from StudentInfo st,SelectInfo se,CourseInfo c where se.StuID=st.StuID and se.CourseID=c.CourseID and st.StuName='"+stuName+"' and c.CourseName='"+courseName+"'";
			SqlHelper SQL=new SqlHelper();
			SQL.insertScore(sql_text);
			sql_text="select st.StuID,st.StuName,c.CourseID,c.CourseName,se.Score from StudentInfo st,SelectInfo se,CourseInfo c where se.StuID=st.StuID and se.CourseID=c.CourseID and st.StuName='"+stuName+"' and c.CourseName='"+courseName+"'";
			String []tablehead;
			String [][]content=null;
			tablehead=SQL.getColumnName(sql_text);
			content=SQL.getRecord(sql_text);
			table_manageScore=new JTable(content,tablehead);
			jsp_manageScore.removeAll();
			jsp_manageScore=new JScrollPane(table_manageScore);
			jp_manageScore.add(jsp_manageScore);
			jsp_manageScore.setBounds(0,200, 600, 400);
			this.update(getGraphics());
			JOptionPane.showMessageDialog(null, "成功录入成绩", "成功", JOptionPane.WARNING_MESSAGE);
		}
		if(e.getActionCommand().equals("add")) {
			String courseID=null;
			String courseName=null;
			String courseCredit=null;
			String totalStuNum=null;
			String availableStuNum=null;
			String classroom=null;
			courseID=jtf_new_courseID.getText().trim();
			courseName=jtf_new_courseName.getText().trim();
			courseCredit=jtf_new_courseCredit.getText().trim();
			totalStuNum=jtf_new_totalStuNum.getText().trim();
			availableStuNum=jtf_new_availableStuNum.getText().trim();
			classroom=jtf_new_classroom.getText().trim();
			if(courseID.length()!=0&&courseName.length()!=0&&courseCredit.length()!=0&&totalStuNum.length()!=0&&availableStuNum.length()!=0&&classroom.length()!=0) {
				String sql_text="insert into CourseInfo values('"+courseID+"','"+courseName+"','"+teacherID+"','"+courseCredit+"','"+totalStuNum+"','"+availableStuNum+"','"+classroom+"')";
				SqlHelper SQL=new SqlHelper();
				SQL.insertScore(sql_text);
/*				this.setVisible(false);
				new ManagementWindow(teacherID);*/
				String []tablehead;
				String [][]content=null;
				sql_text="select CourseID,CourseName,CourseCredit,TotalStuNum,AvailableStuNum,Classroom from CourseInfo where TeacherID="+teacherID;
				tablehead=SQL.getColumnName(sql_text);
				content=SQL.getRecord(sql_text);
				table_addCourse=new JTable(content,tablehead);
				jsp_addCourse.removeAll();
				jsp_addCourse=new JScrollPane(table_addCourse);
				jp_addCourse.add(jsp_addCourse);
				jsp_addCourse.setBounds(0, 300, 600, 300);
				this.update(getGraphics());
				JOptionPane.showMessageDialog(null, "成功添加课程"+courseName, "成功添加课程", JOptionPane.WARNING_MESSAGE);
			}
			else {
				JOptionPane.showMessageDialog(null, "输入信息每一格都不能为空！", "信息有误", JOptionPane.WARNING_MESSAGE);
			}
		}
	}

	
}
