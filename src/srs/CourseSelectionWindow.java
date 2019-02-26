package srs;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;


public class CourseSelectionWindow extends JFrame implements ActionListener{
	
	//�ؼ�
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
		//���	
		JTabbedPane tab = new JTabbedPane(JTabbedPane.TOP); 
		
		jp_coureseInfo=new JPanel();
		jp_selectedCourse=new JPanel();
		JPanel jp_courseScore=new JPanel();
		JButton jb_select;
		JButton jb_drop;
		
		tab.add(jp_coureseInfo,"ѡ��");
		tab.add(jp_selectedCourse,"�γ̱�");
		tab.add(jp_courseScore, "�γ̳ɼ�");
		 
		//������������Ϣ
		String []tablehead;
		String [][]content;
		SqlHelper SQL=new SqlHelper();
		
		String sql_text="select * from CourseInfo";
		//ѡ�ν���
		tablehead=SQL.getColumnName(sql_text);
		sql_text="select * from CourseInfo";
		content=SQL.getRecord(sql_text);
		table_coureseInfo=new JTable(content,tablehead);
		//ѡ�ν���γ̽���
		sql_text="select * from CourseInfo where CourseID in (select CourseID from SelectInfo where StuID="+userid+")";
		content=SQL.getRecord(sql_text);
		table_selectedCourse=new JTable(content,tablehead);
		//��ѯ�γ̳ɼ�����
		sql_text="select st.StuName,c.CourseName,se.Score from CourseInfo c,StudentInfo st,SelectInfo se where c.CourseID=se.CourseID and se.StuID=st.StuID and st.StuID='"+userid+"'";
		tablehead=SQL.getColumnName(sql_text);
		content=SQL.getRecord(sql_text);
		table_courseScore=new JTable(content,tablehead);
		//table.setCellSelectionEnabled(false);
		
		jb_select=new JButton("ѡ��");
		jb_drop=new JButton("��ѡ");
		
		//�������
		jsp_courseInfo=new JScrollPane(table_coureseInfo);
		jsp_selectedCourse=new JScrollPane(table_selectedCourse);
		JScrollPane jsp_courseScore=new JScrollPane(table_courseScore);
		
		jp_coureseInfo.add(jsp_courseInfo);
		jp_coureseInfo.add(jb_select);
		
		jp_selectedCourse.add(jsp_selectedCourse);
		jp_selectedCourse.add(jb_drop);
		
		jp_courseScore.add(jsp_courseScore);
		
		//��Ӽ���
		jb_select.addActionListener(this);
		jb_select.setActionCommand("select");
		jb_drop.addActionListener(this);
		jb_drop.setActionCommand("drop");
		//�������
		this.add(tab);
		
		//��������
		this.setLayout(null);
		jp_coureseInfo.setLayout(null);
		jp_selectedCourse.setLayout(null);
		jp_courseScore.setLayout(null);
		
		tab.setBounds(0, 0, 600, 600);
		
		//���ѡ��
		jp_coureseInfo.setBounds(0, 0, 600, 600);
		jsp_courseInfo.setBounds(0, 0, 600, 400);
		jb_select.setBounds(270, 500, 60, 30);
		
		//���鿴�γ�
		jp_selectedCourse.setBounds(0, 0, 600, 600);
		jsp_selectedCourse.setBounds(0, 0, 600, 400);
		jb_drop.setBounds(270, 500, 60, 30);
		
		//��ʾ�ɼ����
		jp_courseScore.setBounds(0, 0, 600, 600);
		jsp_courseScore.setBounds(0, 0, 600, 600);
		
		//�ײ㴰������
		this.setTitle("ѧ��ѡ�ν���");
	    this.setSize(618, 647);
		this.setLocationRelativeTo(null);		//����Ļ�м���ʾ(������ʾ) 
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		//�˳��ر�JFrame  
	    this.setVisible(true);
	    
		/*
 

		this.setVisible(true);		//��ʾ����  
*/	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO �Զ����ɵķ������
		if(e.getActionCommand().equals("select")) {
			int selectedRow=table_coureseInfo.getSelectedRow();
			//int slectedCloumn=table_coureseInfo.getSelectedColumn();
			String courseID=(String) table_coureseInfo.getValueAt(selectedRow, 0);				//��ȡid
			String courseName=(String) table_coureseInfo.getValueAt(selectedRow, 1);			//��ȡname
			SqlHelper SQL=new SqlHelper();
			SQL.insertSelectInfo(this.userid, courseID);							//ѡ��
			/*//����ѡ�α�
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
			//���¿γ̱�
			sql_text="select * from CourseInfo where CourseID in (select CourseID from SelectInfo where StuID="+userid+")";;
			tablehead=SQL.getColumnName(sql_text);
			content=SQL.getRecord(sql_text);
			table_selectedCourse=new JTable(content,tablehead);
			this.jsp_selectedCourse.removeAll();
			jsp_selectedCourse=new JScrollPane(table_selectedCourse);
			jp_selectedCourse.add(jsp_selectedCourse);
			jsp_selectedCourse.setBounds(0, 0, 600, 400);
			jp_selectedCourse.update(getGraphics());
			
			JOptionPane.showMessageDialog(null, "�ɹ�ѡ��γ̣�"+courseName, "ѡ�γɹ�", JOptionPane.WARNING_MESSAGE);
			
			jp_coureseInfo.validate();
			jp_selectedCourse.validate();
			//String i=table.getValueAt(selectedRow, slectedCloumn);
			//System.out.println(this.userid);
*/		
			JOptionPane.showMessageDialog(null, "�ɹ�ѡ��γ̣�"+courseName, "ѡ�γɹ�", JOptionPane.WARNING_MESSAGE);	
		} 	
		if(e.getActionCommand().equals("drop")) {
			int selectedRow=table_selectedCourse.getSelectedRow();
			String courseID=(String) table_selectedCourse.getValueAt(selectedRow, 0);				//��ȡid
			String courseName=(String) table_selectedCourse.getValueAt(selectedRow, 1);			//��ȡname
			SqlHelper SQL=new SqlHelper();
			SQL.delete(this.userid, courseID);
			/*//���¿γ̱�
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
			//����ѡ�α�
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
			JOptionPane.showMessageDialog(null, "�ɹ���ѡ�γ̣�"+courseName, "��ѡ�ɹ�", JOptionPane.WARNING_MESSAGE);
		}
	}

}
