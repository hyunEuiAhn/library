package librarySystem;

import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import Client.Client;
import admin.Admin;
import java.awt.Font;

	public class Login extends JFrame implements ActionListener{
	private JButton login, create;
	private JPanel loginP,selectP;
	private JLabel idL, pwL;
	private JTextField idT;
	private JPasswordField pwT;
	private JRadioButton[] jb = new JRadioButton[2];
	private ButtonGroup bg = new ButtonGroup();
	private ImageIcon icon1,icon2,icon3,icon4;
	private Image img;
	
	private String driver = "oracle.jdbc.driver.OracleDriver";
	private String url = "jdbc:oracle:thin:@192.168.51.30:1521:xe";
	private String user = "library";
	private String password = "library";
	
	public Login() {
		try {
			Class.forName(driver);
			System.out.println("접속 완료");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}	//DB드라이버 경로 실행
		//------------------------------버튼 이미지 불러오기
		 icon1 = new ImageIcon("image/1.png"); 
		 icon2 = new ImageIcon("image/2.png"); 
		 icon3 = new ImageIcon("image/3.png"); 
		 icon4 = new ImageIcon("image/4.png"); 
		 img = Toolkit.getDefaultToolkit().getImage("image/5.png");
		 
		 
		 //-------------------------------	로그인 

		loginP = new JPanel();
		loginP.setBounds(50,80,300, 180);
		loginP.setBackground(new Color(255,255,255));
        getContentPane().add(loginP);
        
        //-------------------------------	client, admin체크

        selectP = new JPanel(new GridLayout(0,2));
        selectP.setBounds(0, 86, 300, 45);
        selectP.setBackground(Color.WHITE);
     
		jb[0]= new JRadioButton(icon2,true);	
		jb[0].setSelectedIcon(icon3);
		jb[0].setBackground(Color.WHITE);
		jb[1]= new JRadioButton(icon1);
		jb[1].setSelectedIcon(icon4);
		jb[1].setBackground(Color.WHITE);

	 	//-------------------------------	버튼그룹 추가
        bg.add(jb[0]);
        bg.add(jb[1]);
        loginP.setLayout(null);
     
        //-------------------------------	client, admin체크
        
        selectP.add(jb[0]);
        selectP.add(jb[1]);
        loginP.add(selectP);
        
            //-------------------------------	id panel

            idL = new JLabel("아이디");
            idL.setForeground(new Color(0, 0, 0));
            idL.setFont(new Font("굴림", Font.BOLD, 12));
            idL.setBounds(27, 22, 58, 22);
            loginP.add(idL);
            idT = new JTextField(10);
            idT.setBounds(97, 22, 100, 22);
            loginP.add(idT);
            
            //-------------------------------	pw panel

            pwL = new JLabel("비밀번호 ");
            pwL.setBackground(new Color(0, 0, 0));
            pwL.setForeground(new Color(0, 0, 0));
            pwL.setFont(new Font("굴림", Font.BOLD, 12));
            pwL.setBounds(22, 54, 69, 22);
            loginP.add(pwL);
            pwT = new JPasswordField(10);
            pwT.setBounds(97, 54, 100, 22);
            loginP.add(pwT);
            pwT.selectAll();
            pwT.setEchoChar('*');
            login = new JButton("로그인");
            login.setForeground(new Color(255, 255, 255));
            login.setBounds(210, 26, 80, 45);
            loginP.add(login);
            login.setBackground(new Color(51, 204, 51));
            create = new JButton("회원가입");
            create.setForeground(new Color(0, 102, 0));
            create.setBackground(Color.WHITE);
            create.setBounds(100, 150, 100, 22);
            loginP.add(create);
        
     	//-------------------------------	library배경 이미지
        JPanel background = new JPanel() {		
			public void paintComponent(Graphics g) {
			    g.drawImage(img, 0,0, 400, 275,0,30,554,516,this);
			    setOpaque(false);
			    super.paintComponents(g);
			    this.setLayout(null);
			   }
			};
		background.setBounds(0,0,400,300);
		
		//-------------------------------	
		Container con = getContentPane();
		con.setLayout(null);
        con.add(background);
        
        
		con.setBackground(new Color(255,255,255));//전체배경 흰 
		setBounds(100,100,400,300);
		setVisible(true);
		setResizable(false);
		setTitle("도서 관리 프로그램");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		event();
	}

	public void event() {			//액션리스너 
		login.addActionListener(this);
		create.addActionListener(this);
		jb[0].addActionListener(this);
		jb[1].addActionListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {	//버튼 실행
		if(e.getSource()==jb[1]) create.setEnabled(false);
		else if (e.getSource()==jb[0])	create.setEnabled(true);
		if(e.getSource()==login) {
			login();
		}else if(e.getSource()==create) {
			UserResist userResist = new UserResist();
			userResist.start(Login.this);			
			setVisible(false);
		}
		
	}
	

	public void login() {	//로그인 버튼 눌렀을 때
		Connection conn = getConnection();
		PreparedStatement pstmt=null;
		ResultSet rs = null;
		
		String id = idT.getText().toLowerCase();
		String pw = pwT.getText().toLowerCase();
		
		
		String sql = "select * from client where id='"+id+"'";
		
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			int sw=0;
			while(rs.next()) {
				System.out.println(rs.getString("id"));
				if(rs.getString("id").equals(id)) {
					if (rs.getString("password").equals(pw)) {
						JOptionPane.showMessageDialog(this, "로그인");
						
						if(jb[0].isSelected()) {
							if(!(idT.getText().equals("root"))) {
								//회원모드 실행
								Client client = new Client();
								client.clientMain(Login.this, id);
								setVisible(false);
							}else if(idT.getText().equals("root")) {
								JOptionPane.showMessageDialog(this, "관리자 모드로 로그인 해주세요");							}
						}//회원 모드 로그인
						else if(jb[1].isSelected()) {
							if(!(idT.getText().equals("root"))) {
								JOptionPane.showMessageDialog(this, "사용자 모드로 로그인 해주세요");
							}else if(idT.getText().equals("root")) {
								//관리자 모드 실행
								Admin admin = new Admin();
								admin.admin(Login.this);
								setVisible(false);
							}
						}//관리자 모드 로그인
						sw=1;
						//실행
						
					}//아이디패스워드가 맞았을 때
				}//아이디가 같으면
			}
			
			if(sw==0) {
				JOptionPane.showMessageDialog(this, "아이디 패스워드를 확인해 주세요.");
			}//sw가 0이라는 것은 아이디가 없다는 것.			  
			 
			 
		} catch (SQLException e) {
			e.printStackTrace();
		}
	
		
	}//login()
	
	public Connection getConnection() {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url, user, password);
			System.out.println("접속 성공!");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}	//데이터베이스 주소, 사용자, 비번


	public static void main(String[] args) {
		new Login();
		
	}


}
