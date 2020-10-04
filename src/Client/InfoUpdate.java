package Client;


import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class InfoUpdate extends JFrame implements ActionListener{
	private JLabel idL, passwordL, nameL, callnumL, emailL, hyphen1, hyphen2, confirmL, addressL;
	private JTextField idT, passwordT, nameT, callnumT, callnum2T, emailT, confirmT, addressT;
	private JComboBox<String> telB;
	private JButton createB,idConfirmB, confirmB;
	private JPanel idP, passwordP, nameP, callnumP, emailP, confirmP, buttonP, addressP;
	private String confirm;
	
	private String driver = "oracle.jdbc.driver.OracleDriver";
	private String url = "jdbc:oracle:thin:@192.168.51.30:1521:xe";
	private String user = "library";
	private String password = "library";
	
	private Client client;
	private String id;
	
	
	public void start(Client client, String id) {
		this.client = client;
		this.id = id;
		setLayout(null);

		idL = new JLabel("아이디 : ", JLabel.LEFT);
		idT = new JTextField(id);
		idT.setEditable(false);
		idT.setHorizontalAlignment(JTextField.LEFT);
		idP = new JPanel();
		idP.add(idL);
		idP.add(idT);

		
		passwordL = new JLabel("비밀번호 : ");
		passwordT = new JTextField(10);
		passwordP = new JPanel();
		passwordP.add(passwordL);
		passwordP.add(passwordT);
		
		nameL = new JLabel("이름 : ");
		nameT = new JTextField(10);
		nameP = new JPanel();
		nameP.add(nameL);
		nameP.add(nameT);
		
		callnumL = new JLabel("전화번호 : ");
		callnumT = new JTextField(15);
		callnumP = new JPanel();
		callnumP.add(callnumL);
		callnumP.add(callnumT);
		
		
		emailL = new JLabel("E-mail : ");
		emailT = new JTextField(20);
		emailP = new JPanel();
		emailP.add(emailL);
		emailP.add(emailT);
		
		createB = new JButton("수정");
		buttonP = new JPanel();
		buttonP.add(createB);
		
		Container con = this.getContentPane();
		con.setLayout(new GridLayout(6,0));
		
		con.add(idP);
		con.add(passwordP);
		con.add(nameP);
		con.add(callnumP);
		con.add(emailP);
		con.add(buttonP);
		
		setTitle("정보수정");
		setBounds(100,100,400,500);
		setVisible(true);
		setResizable(false);
		event();
		select();
		this.addWindowListener(new WindowAdapter(){//종료후 다시 로그인 
			@Override
			public void windowClosing(WindowEvent e){
				dispose();	//현재창 끄기
				//client.setVisible(true);
			}
		});

	}
	
	public void event() {
		createB.addActionListener(this);		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
					
			if (!(passwordT.getText().length()>0)) {
				JOptionPane.showMessageDialog(this, "비밀번호를 입력해 주세요.");
				return;
			}
			else if (!(callnumT.getText().length()>0)) {
				JOptionPane.showMessageDialog(this, "전화번호를 입력해 주세요.");
				return;
			}
			else if (!(emailT.getText().length()>0)) {
				JOptionPane.showMessageDialog(this, "이메일을 입력해 주세요.");
				return;
			}
			else {
				update();
				JOptionPane.showMessageDialog(this, "수정이 완료 되었습니다!.");
				dispose();	//현재창 끄기
				//client.setVisible(true);
			}
		
	}
	public void select() {
		Connection conn = getConnection();
		PreparedStatement pstmt=null;
		ResultSet rs = null;
		
		String sql = "select name, password, callnum, email from client  where id = '"+id+"'";
		
		
		try {
			
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				String name = rs.getString("name");
				String pw = rs.getString("password");
				String phone = rs.getString("callnum");
				String email = rs.getString("email");
				
				nameT.setText(name);
				passwordT.setText(pw);
				emailT.setText(email);
				callnumT.setText(phone);

			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				if(rs!=null) rs.close();
				if(pstmt!=null) pstmt.close();
				if(conn!=null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}
	
	public void update() {
		Connection conn = getConnection();
		PreparedStatement pstmt=null;
		
		String sql = "update client SET password=?, name=?,callnum=?,email=? where id = '"+id+"'";
		
		try {
			
			System.out.println(id);
			System.out.println(passwordT.getText());
			System.out.println(nameT.getText());
			System.out.println(callnumT.getText());
			System.out.println(emailT.getText());
			
			pstmt = conn.prepareStatement(sql);
				
			pstmt.setString(1,passwordT.getText());
			pstmt.setString(2,nameT.getText());
			pstmt.setString(3,callnumT.getText());
			pstmt.setString(4,emailT.getText());

			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(pstmt!=null) pstmt.close();
				if(conn!=null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public Connection getConnection() {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url, user, password);
			System.out.println("접속 성공!");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}


	
	
	/*
	public static void main(String[] args) {
		UserResist userResist = new UserResist();
		userResist.start();
	}
	*/
}
