package srs;

import java.awt.GridLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import java.sql.*;
import java.util.Random;

public class LoginWindow extends JFrame implements ActionListener{
	JPanel jp_id,jp_psw,jp_but;		//面板
	JLabel jl_userid,jl_psw;	//标签
	JTextField jtf_userid;		//文本
	JPasswordField jpf_psw;		//密码
	JButton jb_login,jb_quit,jb_register;
	
//	Random ran=new Random();
	
	public static void main(String agrs[]) {
		new LoginWindow();
	}
	
	//构造函数
	LoginWindow(){
		jp_id=new JPanel();
		jp_psw=new JPanel();
		jp_but=new JPanel();
		
		jl_userid=new JLabel("学号/工号");
		jl_psw=new JLabel("密码");
		
		jtf_userid=new JTextField(10);
		
		jpf_psw=new JPasswordField(10);
		
		jb_login=new JButton("登陆");
		jb_quit=new JButton("退出");
		jb_register=new JButton("注册");
		//添加监听
		jb_login.addActionListener(this);
		jb_login.setActionCommand("login");
		jb_quit.addActionListener(this);
		jb_quit.setActionCommand("quit");
		jb_register.addActionListener(this);
		jb_register.setActionCommand("register");
		
		
		
		//布局设置
		this.setLayout(null);
		jp_id.setBounds(140, 70, 200, 50);
		jp_psw.setBounds(150, 120, 200, 50);
		jp_but.setBounds(150, 200, 200, 50);
		
		//加入组件
		jp_id.add(jl_userid);
		jp_id.add(jtf_userid);
		jp_psw.add(jl_psw);
		jp_psw.add(jpf_psw);
		jp_but.add(jb_login);
		jp_but.add(jb_quit);
		jp_but.add(jb_register);
		
		//加入JFrame
		this.add(jp_id);
		this.add(jp_psw);
		this.add(jp_but);
		
		//窗体设置
		this.setTitle("学生选课管理系统");
		this.setSize(500, 300);
		this.setLocationRelativeTo(null);		//在屏幕中间显示(居中显示)  
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		//退出关闭JFrame  
        this.setVisible(true);		//显示窗体  
	}

	//监听
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO 自动生成的方法存根

		//响应登陆
		if(e.getActionCommand().equals("login")) {
			//获取输入的账号密码
			String userid=jtf_userid.getText().trim();
			String userpwd=new String(jpf_psw.getPassword());
			if((userid.length()!=0)&&(userpwd.length()!=0)) {					//账号密码不能为空
				SqlHelper SQL=new SqlHelper();			//创建查询工具对象
				String password=SQL.querypwd(userid);
				int flag=SQL.queryflag(userid);
				//int flag=SqlHelper.queryflag(userid);
				//String password=SqlHelper.querypwd(userid);
				//System.out.println(flag);
				if(password!=null) {					//判断账号是否存在，若不存在方法返回值为null
					//int flag=SQL.queryflag(userid);
					if(flag==1) {			//登陆账号为学生
						if(userpwd.equals(password)) {		//密码是否正确
							new CourseSelectionWindow(userid);
							this.setVisible(false);
						}
						else {
							JOptionPane.showMessageDialog(null, "密码错误，请确认后重新输入！", "输入信息有误", JOptionPane.WARNING_MESSAGE);
						}
					}
					else {			//登陆账户为教师
						if(userpwd.equals(password)) {		//密码是否正确
							new ManagementWindow(userid);
							this.setVisible(false);
						}
						else {
							JOptionPane.showMessageDialog(null, "密码错误，请确认后重新输入！", "输入信息有误", JOptionPane.WARNING_MESSAGE);
						}
					}
				}
				else {
					JOptionPane.showMessageDialog(null, "输入账号不存在，请确认后重新输入！", "输入信息有误", JOptionPane.WARNING_MESSAGE);
				}
			}
			else {
				JOptionPane.showMessageDialog(null, "用户账号或密码不能为空，请重新输入！", "输入信息有误", JOptionPane.WARNING_MESSAGE);
			}
		}
		//响应退出
		if(e.getActionCommand().equals("quit")) {
			this.dispose();
		}
	}
}
