package librarySystem;

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

public class UserResist extends JFrame implements ActionListener{

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
	
	private Login login;
	
	public void start(Login login) {
		this.login = login;
		confirm = Integer.toString((int)(Math.random()*999999));
		System.out.println(confirm);
		getContentPane().setLayout(null);

		idL = new JLabel("아이디 : ", JLabel.LEFT);
		idT = new JTextField(10);
		idT.setEditable(false);
		idT.setHorizontalAlignment(JTextField.LEFT);
		idP = new JPanel();
		idConfirmB = new JButton("중복확인");
		idP.add(idL);
		idP.add(idT);
		idP.add(idConfirmB);
		
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
		String[] number={"010", "011", "017", "019"};
		telB = new JComboBox<String>(number);
		hyphen1 = new JLabel("-");
		hyphen2 = new JLabel("-");
		callnumT = new JTextField(5);
		callnum2T = new JTextField(5);
		callnumP = new JPanel();
		callnumP.add(callnumL);
		callnumP.add(telB);
		callnumP.add(hyphen1);
		callnumP.add(callnumT);
		callnumP.add(hyphen2);
		callnumP.add(callnum2T);
			
			
		emailL = new JLabel("E-mail : ");
		emailT = new JTextField(20);
		emailP = new JPanel();
		emailP.add(emailL);
		emailP.add(emailT);
		
		
		confirmL = new JLabel("인증번호 확인 : ");
		confirmT = new JTextField(10);
		confirmP = new JPanel();
		confirmB = new JButton("인증번호 발송");
		confirmP.add(confirmL);
		confirmP.add(confirmT);
		confirmP.add(confirmB);
		
		createB = new JButton("생성");
		buttonP = new JPanel();
		buttonP.add(createB);
		
		
		Container con = this.getContentPane();
		con.setLayout(new GridLayout(7,0));
		
		con.add(idP);
		con.add(passwordP);
		con.add(nameP);
		con.add(callnumP);
		con.add(emailP);
		con.add(confirmP);
		con.add(buttonP);
		
		
		setTitle("회원가입");
		setBounds(100,100,400,500);
		setVisible(true);
		setResizable(false);
		event();
		this.addWindowListener(new WindowAdapter(){//종료후 다시 로그인 
			@Override
			public void windowClosing(WindowEvent e){
				dispose();	//현재창 끄기
				login.setVisible(true);
			}
		});

	}
	
	public void event() {
		createB.addActionListener(this);
		idConfirmB.addActionListener(this);
		confirmB.addActionListener(this);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==idConfirmB) {
				idconfirm();
		}	//아이디 확인 버튼을 눌렀을 때
		
		
		if(e.getSource()==confirmB) {
			if(emailT.getText().length()>0) {
				sendEmail();
			}else 	JOptionPane.showMessageDialog(this, "이메일을 입력해 주세요.");
		}	//이메일 확인 버튼을 눌렀을 때

		
		
		if(e.getSource()==createB) {			//중복 아이디가 없고, 이메일 인증 번호가 맞아야 성공!
			
			if (!(idT.getText().length()>0)) {
				JOptionPane.showMessageDialog(this, "아이디를 입력해 주세요.");
				return;
			}
			else if (!(passwordT.getText().length()>0)) {
				JOptionPane.showMessageDialog(this, "비밀번호를 입력해 주세요.");
				return;
			}
			else if (!(callnumT.getText().length()>0)||!(callnum2T.getText().length()>0)) {
				JOptionPane.showMessageDialog(this, "전화번호를 입력해 주세요.");
				return;
			}
			else if (!(emailT.getText().length()>0)) {
				JOptionPane.showMessageDialog(this, "이메일을 입력해 주세요.");
				return;
			}
			else if (!(confirmT.getText().length()>0)) {
				JOptionPane.showMessageDialog(this, "인증번호를 입력해 주세요.");
				return;
			}
			else if(!(confirm.equals(confirmT.getText()))){
				JOptionPane.showMessageDialog(this, "인증번호가 틀렸습니다요.");
			}
			else if(confirm.equals(confirmT.getText())) {
				create();
				JOptionPane.showMessageDialog(this, "회원가입이 완료 되었습니다!.");
				dispose();	//현재창 끄기
				login.setVisible(true);
			}
		}	
	}
		
	
	
	
	public void sendEmail() {	//인증번호 메일 발송
		
		System.out.println(confirm);
		String host     = "smtp.naver.com";
		  final String user   = "javatest14@naver.com";
		  final String password  = "helloworld12";

		  String to  = emailT.getText();
		  System.out.println(to);
		  
		  // Get the session object
		  Properties props = new Properties();
		  props.put("mail.smtp.host", host);
		  props.put("mail.smtp.auth", "true");

		  Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
		   protected PasswordAuthentication getPasswordAuthentication() {
		    return new PasswordAuthentication(user, password);
		   }
		  });

		  // Compose the message
		  try {
		   MimeMessage message = new MimeMessage(session);
		   message.setFrom(new InternetAddress(user));
		   message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

		   // Subject
		   message.setSubject("도서관 회원가입 인증번호^^");
		   
		   // Text
		   message.setText(confirm+"");

		   // send the message
		   Transport.send(message);
		   System.out.println("message sent successfully...");
		   JOptionPane.showMessageDialog(this, "이메일로 인증번호를 전송하였습니다.");

		  } catch (MessagingException e) {
		   //e.printStackTrace();
		   JOptionPane.showMessageDialog(this, "전송을 실패하였습니다.");
		  }
	}
	
	public void idconfirm() {
		Connection conn = getConnection();
		PreparedStatement pstmt=null;
		ResultSet rs = null;

		String id = JOptionPane.showInputDialog(this, "아이디를 입력하세요");

		if(id.length()>0) {
			id.toLowerCase();
			String sql = "select * from client where id='"+id+"'";

			try {
				pstmt = conn.prepareStatement(sql);
				rs = pstmt.executeQuery();
				
				
				while(rs.next()) {	
					System.out.println(rs.getString("id"));
					JOptionPane.showMessageDialog(this, "이미 존재하는 아이디입니다.");
					return;
				}
			
				JOptionPane.showMessageDialog(this, "사용 가능한 아이디입니다.");
				idT.setText(id);
						
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void create() {
		Connection conn = getConnection();
		PreparedStatement pstmt=null;
		
		String phone = (String) telB.getSelectedItem()+"-"+callnumT.getText()+"-"+callnum2T.getText();
		
		String id = idT.getText().toLowerCase();
		
		String sql = "insert into client values(?,?,?,?,?,null,null)";
		try {
			
			
			System.out.println(id);
			System.out.println(passwordT.getText());
			System.out.println(nameT.getText());
			System.out.println(phone);
			System.out.println(emailT.getText());

			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,id);
			pstmt.setString(2,passwordT.getText());
			pstmt.setString(3,nameT.getText());
			pstmt.setString(4,phone);
			pstmt.setString(5,emailT.getText());

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
