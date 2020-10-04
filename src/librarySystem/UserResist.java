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

		idL = new JLabel("���̵� : ", JLabel.LEFT);
		idT = new JTextField(10);
		idT.setEditable(false);
		idT.setHorizontalAlignment(JTextField.LEFT);
		idP = new JPanel();
		idConfirmB = new JButton("�ߺ�Ȯ��");
		idP.add(idL);
		idP.add(idT);
		idP.add(idConfirmB);
		
		passwordL = new JLabel("��й�ȣ : ");
		passwordT = new JTextField(10);
		passwordP = new JPanel();
		passwordP.add(passwordL);
		passwordP.add(passwordT);
		
		nameL = new JLabel("�̸� : ");
		nameT = new JTextField(10);
		nameP = new JPanel();
		nameP.add(nameL);
		nameP.add(nameT);
		
		callnumL = new JLabel("��ȭ��ȣ : ");
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
		
		
		confirmL = new JLabel("������ȣ Ȯ�� : ");
		confirmT = new JTextField(10);
		confirmP = new JPanel();
		confirmB = new JButton("������ȣ �߼�");
		confirmP.add(confirmL);
		confirmP.add(confirmT);
		confirmP.add(confirmB);
		
		createB = new JButton("����");
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
		
		
		setTitle("ȸ������");
		setBounds(100,100,400,500);
		setVisible(true);
		setResizable(false);
		event();
		this.addWindowListener(new WindowAdapter(){//������ �ٽ� �α��� 
			@Override
			public void windowClosing(WindowEvent e){
				dispose();	//����â ����
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
		}	//���̵� Ȯ�� ��ư�� ������ ��
		
		
		if(e.getSource()==confirmB) {
			if(emailT.getText().length()>0) {
				sendEmail();
			}else 	JOptionPane.showMessageDialog(this, "�̸����� �Է��� �ּ���.");
		}	//�̸��� Ȯ�� ��ư�� ������ ��

		
		
		if(e.getSource()==createB) {			//�ߺ� ���̵� ����, �̸��� ���� ��ȣ�� �¾ƾ� ����!
			
			if (!(idT.getText().length()>0)) {
				JOptionPane.showMessageDialog(this, "���̵� �Է��� �ּ���.");
				return;
			}
			else if (!(passwordT.getText().length()>0)) {
				JOptionPane.showMessageDialog(this, "��й�ȣ�� �Է��� �ּ���.");
				return;
			}
			else if (!(callnumT.getText().length()>0)||!(callnum2T.getText().length()>0)) {
				JOptionPane.showMessageDialog(this, "��ȭ��ȣ�� �Է��� �ּ���.");
				return;
			}
			else if (!(emailT.getText().length()>0)) {
				JOptionPane.showMessageDialog(this, "�̸����� �Է��� �ּ���.");
				return;
			}
			else if (!(confirmT.getText().length()>0)) {
				JOptionPane.showMessageDialog(this, "������ȣ�� �Է��� �ּ���.");
				return;
			}
			else if(!(confirm.equals(confirmT.getText()))){
				JOptionPane.showMessageDialog(this, "������ȣ�� Ʋ�Ƚ��ϴٿ�.");
			}
			else if(confirm.equals(confirmT.getText())) {
				create();
				JOptionPane.showMessageDialog(this, "ȸ�������� �Ϸ� �Ǿ����ϴ�!.");
				dispose();	//����â ����
				login.setVisible(true);
			}
		}	
	}
		
	
	
	
	public void sendEmail() {	//������ȣ ���� �߼�
		
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
		   message.setSubject("������ ȸ������ ������ȣ^^");
		   
		   // Text
		   message.setText(confirm+"");

		   // send the message
		   Transport.send(message);
		   System.out.println("message sent successfully...");
		   JOptionPane.showMessageDialog(this, "�̸��Ϸ� ������ȣ�� �����Ͽ����ϴ�.");

		  } catch (MessagingException e) {
		   //e.printStackTrace();
		   JOptionPane.showMessageDialog(this, "������ �����Ͽ����ϴ�.");
		  }
	}
	
	public void idconfirm() {
		Connection conn = getConnection();
		PreparedStatement pstmt=null;
		ResultSet rs = null;

		String id = JOptionPane.showInputDialog(this, "���̵� �Է��ϼ���");

		if(id.length()>0) {
			id.toLowerCase();
			String sql = "select * from client where id='"+id+"'";

			try {
				pstmt = conn.prepareStatement(sql);
				rs = pstmt.executeQuery();
				
				
				while(rs.next()) {	
					System.out.println(rs.getString("id"));
					JOptionPane.showMessageDialog(this, "�̹� �����ϴ� ���̵��Դϴ�.");
					return;
				}
			
				JOptionPane.showMessageDialog(this, "��� ������ ���̵��Դϴ�.");
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
			System.out.println("���� ����!");
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
