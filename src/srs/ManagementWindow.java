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
	
	JLabel jl_stuName,jl_courseID,jl_score;	//¼��ɼ��ı���ı�ǩ
	
	JTextField jtf_stuName;		//¼��ɼ��ı���
	JTextField jtf_courseID;	
	JTextField jtf_score;	
	
	//�½��γ�
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
		
		//¼��ɼ���
		jl_stuName=new JLabel("ѧ������:");
		jl_courseID=new JLabel("�γ���:");	
		jl_score=new JLabel("���ճɼ�:");	
		jtf_stuName=new JTextField(10);
		jtf_courseID=new JTextField(10);
		jtf_score=new JTextField(10);
		
		//���	
		JTabbedPane tab = new JTabbedPane(JTabbedPane.TOP); 
				
		JPanel jp_stuInfo=new JPanel();
		jp_manageScore=new JPanel();
		jp_addCourse=new JPanel();
		jp_new_addCourse=new JPanel();
		jp_addCourse.add(jp_new_addCourse);
		JButton jb_updateScore=new JButton("¼��ɼ�");
		JButton jb_find=new JButton("��ѯ��Ϣ");
		
		tab.add(jp_stuInfo,"ѧ����Ϣ");
		tab.add(jp_manageScore,"�ɼ�¼��");
		tab.add(jp_addCourse,"�½��γ�");
		
		
		//��ӿγ̿ؼ���ʼ��
		jl_new_courseID=new JLabel("�γ̺ţ�");
		jl_new_courseName=new JLabel("�γ�����");
		jl_new_courseCredit=new JLabel("ѧ�֣�");
		jl_new_totalStuNum=new JLabel("ѧ��������");
		jl_new_availableStuNum=new JLabel("��ѡ������");
		jl_new_classroom=new JLabel("���Һţ�");
	    jtf_new_courseID=new JTextField(10);	
		jtf_new_courseName=new JTextField(20);
		jtf_new_courseCredit=new JTextField(10);
		jtf_new_totalStuNum=new JTextField(10);
		jtf_new_availableStuNum=new JTextField(10);
		jtf_new_classroom=new JTextField(10);
		jb_new_addcourse=new JButton("�����γ�");
		jb_new_view=new JButton("���пγ�");
		jb_new_delete=new JButton("ɾ���γ�");
		
		//������������Ϣ
		String []tablehead;
		String [][]content;
		SqlHelper SQL=new SqlHelper();
		
		//��ʦѧ����Ϣ
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
		//�������
		JScrollPane jsp_stuInfo=new JScrollPane(table_stuInfo);
		jsp_manageScore=new JScrollPane(table_manageScore);
		//��ʦ�α�
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
		
		
		//��Ӽ���
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
		
		//�������
		this.add(tab);
		
		//��������
		this.setLayout(null);
		jp_stuInfo.setLayout(null);
		jp_manageScore.setLayout(null);
		jp_addCourse.setLayout(null);
		jp_new_addCourse.setLayout(null);
		
		tab.setBounds(0, 0, 600, 600);
		
		//��ʾ�ɼ����
		jp_stuInfo.setBounds(0, 0, 600, 600);
		jsp_stuInfo.setBounds(0, 0, 600, 600);
		
		//���¼��ɼ�
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
		
		//��ӿγ����
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
		
		//�ײ㴰������
		this.setTitle("��ʦ�������");
		this.setSize(618, 647);
		this.setLocationRelativeTo(null);		//����Ļ�м���ʾ(������ʾ)  
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		//�˳��ر�JFrame  
		this.setVisible(true);		//��ʾ����  
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO �Զ����ɵķ������
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
			JOptionPane.showMessageDialog(null, "�ɹ�¼��ɼ�", "�ɹ�", JOptionPane.WARNING_MESSAGE);
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
				JOptionPane.showMessageDialog(null, "�ɹ���ӿγ�"+courseName, "�ɹ���ӿγ�", JOptionPane.WARNING_MESSAGE);
			}
			else {
				JOptionPane.showMessageDialog(null, "������Ϣÿһ�񶼲���Ϊ�գ�", "��Ϣ����", JOptionPane.WARNING_MESSAGE);
			}
		}
	}

	
}
