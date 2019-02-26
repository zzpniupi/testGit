package srs;

import java.awt.GridLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import java.sql.*;
import java.util.Random;

public class LoginWindow extends JFrame implements ActionListener{
	JPanel jp_id,jp_psw,jp_but;		//���
	JLabel jl_userid,jl_psw;	//��ǩ
	JTextField jtf_userid;		//�ı�
	JPasswordField jpf_psw;		//����
	JButton jb_login,jb_quit,jb_register;
	
//	Random ran=new Random();
	
	public static void main(String agrs[]) {
		new LoginWindow();
	}
	
	//���캯��
	LoginWindow(){
		jp_id=new JPanel();
		jp_psw=new JPanel();
		jp_but=new JPanel();
		
		jl_userid=new JLabel("ѧ��/����");
		jl_psw=new JLabel("����");
		
		jtf_userid=new JTextField(10);
		
		jpf_psw=new JPasswordField(10);
		
		jb_login=new JButton("��½");
		jb_quit=new JButton("�˳�");
		jb_register=new JButton("ע��");
		//��Ӽ���
		jb_login.addActionListener(this);
		jb_login.setActionCommand("login");
		jb_quit.addActionListener(this);
		jb_quit.setActionCommand("quit");
		jb_register.addActionListener(this);
		jb_register.setActionCommand("register");
		
		
		
		//��������
		this.setLayout(null);
		jp_id.setBounds(140, 70, 200, 50);
		jp_psw.setBounds(150, 120, 200, 50);
		jp_but.setBounds(150, 200, 200, 50);
		
		//�������
		jp_id.add(jl_userid);
		jp_id.add(jtf_userid);
		jp_psw.add(jl_psw);
		jp_psw.add(jpf_psw);
		jp_but.add(jb_login);
		jp_but.add(jb_quit);
		jp_but.add(jb_register);
		
		//����JFrame
		this.add(jp_id);
		this.add(jp_psw);
		this.add(jp_but);
		
		//��������
		this.setTitle("ѧ��ѡ�ι���ϵͳ");
		this.setSize(500, 300);
		this.setLocationRelativeTo(null);		//����Ļ�м���ʾ(������ʾ)  
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		//�˳��ر�JFrame  
        this.setVisible(true);		//��ʾ����  
	}

	//����
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO �Զ����ɵķ������

		//��Ӧ��½
		if(e.getActionCommand().equals("login")) {
			//��ȡ������˺�����
			String userid=jtf_userid.getText().trim();
			String userpwd=new String(jpf_psw.getPassword());
			if((userid.length()!=0)&&(userpwd.length()!=0)) {					//�˺����벻��Ϊ��
				SqlHelper SQL=new SqlHelper();			//������ѯ���߶���
				String password=SQL.querypwd(userid);
				int flag=SQL.queryflag(userid);
				//int flag=SqlHelper.queryflag(userid);
				//String password=SqlHelper.querypwd(userid);
				//System.out.println(flag);
				if(password!=null) {					//�ж��˺��Ƿ���ڣ��������ڷ�������ֵΪnull
					//int flag=SQL.queryflag(userid);
					if(flag==1) {			//��½�˺�Ϊѧ��
						if(userpwd.equals(password)) {		//�����Ƿ���ȷ
							new CourseSelectionWindow(userid);
							this.setVisible(false);
						}
						else {
							JOptionPane.showMessageDialog(null, "���������ȷ�Ϻ��������룡", "������Ϣ����", JOptionPane.WARNING_MESSAGE);
						}
					}
					else {			//��½�˻�Ϊ��ʦ
						if(userpwd.equals(password)) {		//�����Ƿ���ȷ
							new ManagementWindow(userid);
							this.setVisible(false);
						}
						else {
							JOptionPane.showMessageDialog(null, "���������ȷ�Ϻ��������룡", "������Ϣ����", JOptionPane.WARNING_MESSAGE);
						}
					}
				}
				else {
					JOptionPane.showMessageDialog(null, "�����˺Ų����ڣ���ȷ�Ϻ��������룡", "������Ϣ����", JOptionPane.WARNING_MESSAGE);
				}
			}
			else {
				JOptionPane.showMessageDialog(null, "�û��˺Ż����벻��Ϊ�գ����������룡", "������Ϣ����", JOptionPane.WARNING_MESSAGE);
			}
		}
		//��Ӧ�˳�
		if(e.getActionCommand().equals("quit")) {
			this.dispose();
		}
	}
}
