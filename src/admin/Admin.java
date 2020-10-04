package admin;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import librarySystem.Login;


public class Admin extends JFrame implements ActionListener, MouseListener {
	private Panel bookAddp,bookAddp2,bookAddp3,userAddp,userAddp2,userAddp3,
		rentAddp,rentAddp2;
	private JButton bookSearchB,bookAddB,bookDeleteB,bookUpdateB,bookRentB,bookReturnB,bookresetB,
		userSearchB,userAllB,userAddB,userDeleteB,userUpdateB,todayB,overdueB,userEraseB,
		moneySearchB,ClosingB, clientSearchB;
	private JLabel book,genre,genre2,book2,isbn,publisher,rent,rentDate,rentUser,
		bookL,userL,user,id,password,name,callNum,email,overdue,
		rentAllL,moneyTitle,moneyUser,moneyDate,moneyDate2,Closing,ClosingL;
	private JTextField bookT,bookT2,isbnT,publisherT,rentT,rentDateT,rentUserT,
		userT,idT,passwordT,nameT,callNumT,emailT,overdueT,moneyUserT,moneyDateT,
		moneyDateT2,ClosingT;
	private JTabbedPane tp;
	private DefaultTableModel modelBook,modelUser,modelAll,modelToday,modelMoney,modelClosing;
	private JTable tableBookList,tableUserList,modelAllList,modelTodayList,modelMoneyList,modelClosingList;
	private JComboBox<String> genreName,genreName2;
	
	public void admin(Login login) {
		setTitle("도서관리프로그램 관리자");
		//setLayout(null);
		
		SimpleDateFormat sd = new SimpleDateFormat ("yyMMdd");
		Date today = new Date ();
		String sysdate = sd.format (today);//오늘날짜
		
		Font font= new Font("굴림",Font.BOLD,18);
		bookAddp = new Panel();userAddp = new Panel();
		bookAddp2 = new Panel();userAddp2 = new Panel();
		bookAddp3 = new Panel();userAddp3 = new Panel();
		rentAddp = new Panel();rentAddp2 = new Panel();
		//패널생성
		
		bookL= new JLabel("도서정보(도서 편집, 도서 대여)");
		book = new JLabel("책이름 :");bookT = new JTextField("");
		genre = new JLabel("장르 :");
		book2 = new JLabel("책이름 :");bookT2 = new JTextField("");
		genre2 = new JLabel("장르 :");
		rent = new JLabel("대여날짜 :");rentT = new JTextField("");
		rentT.setEnabled(false);
		rentDate = new JLabel("반납날짜 :");rentDateT = new JTextField("");
		rentDateT.setEnabled(false);
		rentUser = new JLabel("대여회원 :");rentUserT = new JTextField("");
		rentUserT.setEnabled(false);
		isbn = new JLabel("ISBN :");isbnT = new JTextField("");
		publisher = new JLabel("출판사 :");publisherT = new JTextField("");
		//도서관리라벨,텍스트필드생성

		
		userL = new JLabel("회원정보");
		user = new JLabel("회원 이름:");userT = new JTextField("");
		id = new JLabel("회원 아이디 :");idT = new JTextField("");
		password = new JLabel("회원 비밀번호 :");passwordT = new JTextField("");
		name = new JLabel("회원 이름 :");nameT = new JTextField("");
		callNum = new JLabel("회원 전화번호 :");callNumT = new JTextField("");
		email = new JLabel("회원 이메일 :");emailT = new JTextField("");
		overdue = new JLabel("연체 횟수 :");overdueT = new JTextField("");
		//유저관리라벨,텍스트필드생성
		
		moneyTitle = new JLabel("정산검색");
		moneyUser = new JLabel("회원아이디 :");moneyUserT = new JTextField("");
		moneyDate = new JLabel("납입날짜 :");moneyDateT = new JTextField(sysdate);
		moneyDate2 = new JLabel(" ~ ");moneyDateT2 = new JTextField(sysdate);
		Closing = new JLabel("정산년도 :");ClosingT = new JTextField("");
		clientSearchB = new JButton("검색");
		ClosingL= new JLabel("");
		//정산관리라베,텍스트필드생성
		
		
		rentAllL = new JLabel("대여 현황");
		//대여관리 라벨
		
		bookresetB = new JButton("도서지우기");
		bookSearchB = new JButton("도서검색"); userSearchB = new JButton("회원검색");
		bookAddB = new JButton("도서등록");userAddB = new JButton("회원등록");
		bookUpdateB = new JButton("도서수정");userUpdateB = new JButton("회원수정");
		bookDeleteB = new JButton("도서삭제");userDeleteB = new JButton("회원삭제");
		bookRentB = new JButton("도서대여");userAllB = new JButton("전회원");
		bookReturnB = new JButton("도서반납");todayB = new JButton("오늘반납자");
		overdueB = new JButton("연체자");userEraseB = new JButton("지우기");
		moneySearchB = new JButton("검색");ClosingB = new JButton("검색");
		
		//버튼생성
		String month[] = {"1월","2월","3월","4월","5월","6월","7월","8월","9월","10월","11월","12월",};
		String userdate[] = {"아이디","비밀번호","이름","전화번호","이메일","연체횟수"};
		String bookdate[] = {"도서명","ISBN","장르","출판사","아이디","대여날짜","반납날짜"};
		String genreData[] = {"소설","학습"};
		String moneydata[] = {"아이디","납입금액","납입날짜"};
		//테이블 타이틀
		
		genreName = new JComboBox<String>(genreData);
		genreName2 = new JComboBox<String>(genreData);
		//콤보박스생성
		
		modelBook =new DefaultTableModel(bookdate,0) {
			public boolean isCellEditable(int r, int c) {
                return false;
			}
		};
		tableBookList = new JTable(modelBook);
		tableBookList.getTableHeader().setReorderingAllowed(false);
		tableBookList.getTableHeader().setReorderingAllowed(false);
		tableBookList.getColumnModel().getColumn(0).setPreferredWidth(200);
		tableBookList.getColumnModel().getColumn(1).setPreferredWidth(100);
		tableBookList.getColumnModel().getColumn(2).setPreferredWidth(70);
		tableBookList.getColumnModel().getColumn(3).setPreferredWidth(120);
		tableBookList.getColumnModel().getColumn(4).setPreferredWidth(80);
		tableBookList.getColumnModel().getColumn(5).setPreferredWidth(80);
		tableBookList.getColumnModel().getColumn(6).setPreferredWidth(80);
		JScrollPane scrollBookList = new JScrollPane(tableBookList);//도서관리테이블생성
		
		modelUser =new DefaultTableModel(userdate,0) {
			public boolean isCellEditable(int r, int c) {
                return false;
			}
		};
		tableUserList = new JTable(modelUser);
		tableUserList.getTableHeader().setReorderingAllowed(false);
		tableUserList.getColumnModel().getColumn(0).setPreferredWidth(20);
		tableUserList.getColumnModel().getColumn(1).setPreferredWidth(20);
		tableUserList.getColumnModel().getColumn(2).setPreferredWidth(20);
		tableUserList.getColumnModel().getColumn(3).setPreferredWidth(100);
		tableUserList.getColumnModel().getColumn(4).setPreferredWidth(100);
		tableUserList.getColumnModel().getColumn(5).setPreferredWidth(20);
		JScrollPane scrollUserList = new JScrollPane(tableUserList);//회원관리테이블생성
		
		modelAll =new DefaultTableModel(bookdate,0) {
			public boolean isCellEditable(int r, int c) {
                return false;
			}
		};
		modelAllList = new JTable(modelAll);
		modelAllList.getTableHeader().setReorderingAllowed(false);
		modelAllList.getColumnModel().getColumn(0).setPreferredWidth(200);
		modelAllList.getColumnModel().getColumn(1).setPreferredWidth(100);
		modelAllList.getColumnModel().getColumn(2).setPreferredWidth(70);
		modelAllList.getColumnModel().getColumn(3).setPreferredWidth(120);
		modelAllList.getColumnModel().getColumn(4).setPreferredWidth(80);
		modelAllList.getColumnModel().getColumn(5).setPreferredWidth(80);
		modelAllList.getColumnModel().getColumn(6).setPreferredWidth(80);

		JScrollPane scrollAllList = new JScrollPane(modelAllList);//전체대여테이블생성
		
		modelToday =new DefaultTableModel(bookdate,0) {
			public boolean isCellEditable(int r, int c) {
                return false;
			}
		};
		modelTodayList = new JTable(modelToday);
		modelTodayList.getTableHeader().setReorderingAllowed(false);
		modelTodayList.getTableHeader().setReorderingAllowed(false);
		modelTodayList.getColumnModel().getColumn(0).setPreferredWidth(200);
		modelTodayList.getColumnModel().getColumn(1).setPreferredWidth(100);
		modelTodayList.getColumnModel().getColumn(2).setPreferredWidth(70);
		modelTodayList.getColumnModel().getColumn(3).setPreferredWidth(120);
		modelTodayList.getColumnModel().getColumn(4).setPreferredWidth(80);
		modelTodayList.getColumnModel().getColumn(5).setPreferredWidth(80);
		modelTodayList.getColumnModel().getColumn(6).setPreferredWidth(80);
		JScrollPane scrollTodayList = new JScrollPane(modelTodayList);//대여테이블생성
		
		
		modelMoney =new DefaultTableModel(moneydata,0) {
			public boolean isCellEditable(int r, int c) {
                return false;
			}
		};
		modelMoneyList = new JTable(modelMoney);
		modelMoneyList.getTableHeader().setReorderingAllowed(false);
		modelMoneyList.getColumnModel().getColumn(0).setPreferredWidth(20);
		JScrollPane scrollMoneyList = new JScrollPane(modelMoneyList);//머니테이블생성
		//수정한부분
		
		modelClosing =new DefaultTableModel(month,0) {
			public boolean isCellEditable(int r, int c) {
                return false;
			}
		};
		modelClosingList = new JTable(modelClosing);
		modelClosingList.getTableHeader().setReorderingAllowed(false);
		//modelClosingList.getColumnModel().getColumn(0).setPreferredWidth(20);
		JScrollPane scrollClosingList = new JScrollPane(modelClosingList);//결산테이블생성
		//수정한부분
		
		bookAddp2.setLayout(null);
		bookAddp3.setLayout(null);
		userAddp2.setLayout(null);
		userAddp3.setLayout(null);
		rentAddp.setLayout(null);
		rentAddp2.setLayout(null);
		
		book.setBounds(10,230,50,25);bookT.setBounds(60,230,90,25);
		genre.setBounds(270,230,50,25);genreName.setBounds(320,228,70,30);
		//genreName.setBackground(new Color(177, 184, 183));
		bookL.setBounds(10,0,400,40);bookL.setFont(font);
		book2.setBounds(10,50,50,25);bookT2.setBounds(60,50,290,25);
		
		isbn.setBounds(10,90,50,25);isbnT.setBounds(60,90,100,25);
		publisher.setBounds(10,130,50,25);publisherT.setBounds(60,130,100,25);
		genre2.setBounds(10,170,50,25);genreName2.setBounds(60,170,100,25);
		rent.setBounds(180,90,70,25);rentT.setBounds(250,90,100,25);
		rentDate.setBounds(180,130,70,25);rentDateT.setBounds(250,130,100,25);
		rentUser.setBounds(180,170,70,25);rentUserT.setBounds(250,170,100,25);//도서관리 등록항목위치
		
		bookSearchB.setBounds(160,228,90,30);
		bookAddB.setBounds(40,230,90,30);bookUpdateB.setBounds(150,230,90,30);
		bookDeleteB.setBounds(260,230,90,30);bookresetB.setBounds(370,230,100,30);
		bookRentB.setBounds(410,100,90,30);bookReturnB.setBounds(410,140,90,30);
		bookSearchB.setBackground(new Color(177, 184, 183));bookAddB.setBackground(new Color(177, 184, 183));
		bookUpdateB.setBackground(new Color(177, 184, 183));bookDeleteB.setBackground(new Color(177, 184, 183));
		bookresetB.setBackground(new Color(177, 184, 183));bookRentB.setBackground(new Color(177, 184, 183));
		bookReturnB.setBackground(new Color(177, 184, 183));
		//도서관리 버튼위치
		
		bookAddp2.add(book);bookAddp2.add(bookT);
		bookAddp2.add(genre);bookAddp2.add(genreName);
		bookAddp3.add(bookL);
		bookAddp3.add(book2);bookAddp3.add(bookT2);                 
		bookAddp3.add(isbn);bookAddp3.add(isbnT);
		bookAddp3.add(publisher);bookAddp3.add(publisherT);
		bookAddp3.add(genre2);bookAddp3.add(genreName2);
		bookAddp3.add(rent);bookAddp3.add(rentT);
		bookAddp3.add(rentDate);bookAddp3.add(rentDateT);
		bookAddp3.add(rentUser);bookAddp3.add(rentUserT);
		bookAddp3.add(bookAddB);bookAddp3.add(bookUpdateB);
		bookAddp3.add(bookDeleteB);bookAddp3.add(bookReturnB);
		bookAddp3.add(bookRentB);bookAddp3.add(bookresetB);
		bookAddp2.add(bookSearchB);//도서관리추가
		
		
		userL.setBounds(10,0,80,40);userL.setFont(font);
		user.setBounds(10,230,60,25);userT.setBounds(75,230,90,25);
		id.setBounds(10,50,90,25);idT.setBounds(100,50,90,25);
		password.setBounds(10,90,90,25);passwordT.setBounds(100,90,90,25);
		name.setBounds(10,130,90,25);nameT.setBounds(100,130,90,25);
		callNum.setBounds(200,90,90,25);callNumT.setBounds(290,90,90,25);
		email.setBounds(200,50,90,25);emailT.setBounds(290,50,90,25);
		overdue .setBounds(200,130,90,25);overdueT.setBounds(290,130,90,25);
		//유저관리등록항목위치
		
		userAddB.setBounds(40,230,90,30);userUpdateB.setBounds(150,230,90,30);
		userDeleteB.setBounds(260,230,90,30);userSearchB.setBounds(170,230,90,25);
		userAllB.setBounds(280,230,90,25);userEraseB.setBounds(370,230,100,30);
		userAddB.setBackground(new Color(177, 184, 183));userUpdateB.setBackground(new Color(177, 184, 183));
		userDeleteB.setBackground(new Color(177, 184, 183));userSearchB.setBackground(new Color(177, 184, 183));
		userAllB.setBackground(new Color(177, 184, 183));userEraseB.setBackground(new Color(177, 184, 183));
		//유저관리버튼항목위치
		
		userAddp3.add(userL);
		userAddp2.add(user);userAddp2.add(userT);
		userAddp3.add(id);userAddp3.add(idT);
		userAddp3.add(password);userAddp3.add(passwordT);
		userAddp3.add(name);userAddp3.add(nameT);
		userAddp3.add(callNum);userAddp3.add(callNumT);
		userAddp3.add(email);userAddp3.add(emailT);
		userAddp3.add(overdue);userAddp3.add(overdueT);
		userAddp3.add(userAddB);userAddp3.add(userUpdateB);
		userAddp3.add(userDeleteB);userAddp2.add(userSearchB);
		userAddp2.add(userAllB);userAddp3.add(userEraseB);//유저관리추가
		
		rentAllL.setBounds(10,0,100,40);rentAllL.setFont(font);
		todayB.setBounds(10,255,100,40);overdueB.setBounds(120,255,100,40);
		todayB.setBackground(new Color(177, 184, 183));overdueB.setBackground(new Color(177, 184, 183));
		//대여관리항목위치
		
		rentAddp.add(rentAllL);rentAddp.add(todayB);rentAddp.add(overdueB);
		//대여관리추가
		
		
		moneyTitle.setBounds(100,10,80,40);moneyTitle.setFont(font);
		moneyUser.setBounds(10,50,80,20);moneyUserT.setBounds(100,50,80,20);
		moneyDate.setBounds(10,90,80,20);moneyDateT.setBounds(100,90,80,20);moneyDate2.setBounds(180,90,20,20);moneyDateT2.setBounds(200,90,80,20);
		moneySearchB.setBounds(300,90,80,20);Closing.setBounds(10,130,80,20);
		ClosingT.setBounds(100,130,80,20);ClosingB.setBounds(200,130,80,20);
		ClosingL.setBounds(0,420,120,30);clientSearchB.setBounds(200, 50, 80, 20);
		moneySearchB.setBackground(new Color(177, 184, 183));ClosingB.setBackground(new Color(177, 184, 183));
		//정산관리항목위치
		
		
		rentAddp2.add(moneyTitle);
		rentAddp2.add(moneyUser);rentAddp2.add(moneyUserT);
		rentAddp2.add(moneyDate);rentAddp2.add(moneyDateT);rentAddp2.add(moneyDate2);rentAddp2.add(moneyDateT2);
		rentAddp2.add(moneySearchB);rentAddp2.add(Closing);rentAddp2.add(ClosingT);rentAddp2.add(ClosingB);
		rentAddp2.add(ClosingL);rentAddp2.add(clientSearchB);
		//정산관리추가
		
		scrollBookList.setBounds(10,10,570,200);
		scrollBookList.setBackground(Color.cyan);
		bookAddp2.add(scrollBookList);//도서관리테이블 추가
		
		scrollUserList.setBounds(10,10,570,200);
		scrollUserList.setBackground(Color.cyan);
		userAddp2.add(scrollUserList);//회원관리테이블 추가
		
		scrollAllList.setBounds(10,45,570,200);
		scrollAllList.setBackground(Color.cyan);
		rentAddp.add(scrollAllList);//전체대여테이블 추가
		
		scrollTodayList.setBounds(10,300,570,250);
		scrollTodayList.setBackground(Color.cyan);
		rentAddp.add(scrollTodayList);//대여테이블 추가
		
		scrollMoneyList.setBounds(10,160,380,250);
		scrollMoneyList.setBackground(Color.cyan);
		rentAddp2.add(scrollMoneyList);//머니테이블 추가
		//수정한부분
		
		scrollClosingList.setBounds(0,450,590,39);
		scrollClosingList.setBackground(Color.cyan);
		rentAddp2.add(scrollClosingList);//머니테이블 추가
		//수정한부분
		
		bookAddp2.setBackground(new Color(209, 210, 209));
		bookAddp3.setBackground(new Color(209, 210, 209));
		bookAddp.add(bookAddp2);bookAddp.add(bookAddp3);//도서관리패널에 두개의 패널넣기
		bookAddp.setLayout(new GridLayout(2,1));//도서관리패널 색상지정및정렬
		
		userAddp2.setBackground(new Color(209, 210, 209));
		userAddp3.setBackground(new Color(209, 210, 209));
		userAddp.add(userAddp2);userAddp.add(userAddp3);//회원관리패널에 두개의 패널넣기
		userAddp.setLayout(new GridLayout(2,1));//회원관리패널 색상지정및정렬
		
		rentAddp.setBackground(new Color(209, 210, 209));
		rentAddp2.setBackground(new Color(209, 210, 209));
		playBook();
		
		
		
		
		
		tp=new JTabbedPane(JTabbedPane.NORTH);
		tp.add(bookAddp,"도서관리");
		tp.add(userAddp,"회원관리");
		tp.add(rentAddp,"대여관리");
		tp.add(rentAddp2,"정산관리");
		add(tp);
		
		bookAddB.setEnabled(true);
		bookUpdateB.setEnabled(false);
		bookDeleteB.setEnabled(false);
		bookRentB.setEnabled(false);
		bookReturnB.setEnabled(false);
		bookresetB.setEnabled(false);
		
		userAddB.setEnabled(true);
		userUpdateB.setEnabled(false);
		userDeleteB.setEnabled(false);
		
		setResizable(false);
		setBounds(10,80,600,600);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		
		event();
	}

	private void playBook() {
		BookDAO bookDAO = new BookDAO();
		bookDAO.bookRentlist(this);
	}



	public void event() {
		bookSearchB.addActionListener(this);
		bookAddB.addActionListener(this);
		bookDeleteB.addActionListener(this);
		bookUpdateB.addActionListener(this);
		bookRentB.addActionListener(this);
		bookReturnB.addActionListener(this);
		bookresetB.addActionListener(this);

		userSearchB.addActionListener(this);
		userAllB.addActionListener(this);
		userAddB.addActionListener(this);
		userDeleteB.addActionListener(this);
		userUpdateB.addActionListener(this);
		userEraseB.addActionListener(this);
		todayB.addActionListener(this);
		overdueB.addActionListener(this);
		genreName.addActionListener(this);
		
		moneySearchB.addActionListener(this);
		ClosingB.addActionListener(this);
		clientSearchB.addActionListener(this);
		
		tableBookList.addMouseListener(this);
		tableUserList.addMouseListener(this);
		
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==bookSearchB)bookSearch();
		else if(e.getSource()==bookAddB)bookAdd();
		else if(e.getSource()==bookDeleteB)bookDelete();
		else if(e.getSource()==bookUpdateB)bookUpdate();
		else if(e.getSource()==bookRentB)bookRent();
		else if(e.getSource()==bookReturnB)bookReturn();
		else if(e.getSource()==bookresetB)bookReset();
		else if(e.getSource()==userSearchB) {
			int sw = 0;		//Print select name
			userSearch(sw);
		}
		else if(e.getSource()==userAllB) {
			int sw = 1;		//Print all people
			userSearch(sw);
		}
		else if(e.getSource()==userAddB)userAdd();
		else if(e.getSource()==userDeleteB)userDelete();
		else if(e.getSource()==userUpdateB)userUpdate();
		else if(e.getSource()==userEraseB)userErase();
		else if(e.getSource()==todayB)today();
		else if(e.getSource()==overdueB)overdue();
		else if(e.getSource()==moneySearchB)moneySearch();
		else if(e.getSource()==ClosingB)moneySum();
		else if(e.getSource()==genreName)bookSearchCombo();
		else if(e.getSource()==clientSearchB)userSearch();

	}
	
	private void userSearch() {
		modelMoney.setNumRows(0);
		MoneyDAO moneyDAO = MoneyDAO.getInstance();
		
		String user = moneyUserT.getText();
		
		moneyDAO.userSearch(this,user);
		
	}

	private void moneySum() {
		modelClosing.setNumRows(0);
		MoneyDAO moneyDAO = MoneyDAO.getInstance();
		String sum = ClosingT.getText();
		if(sum.equals(""))return;
		ClosingL.setText(sum+"년 단위(원)");
		moneyDAO.moneySum(this,sum);
		ClosingT.setText("");
	}

	private void moneySearch() {
		modelMoney.setNumRows(0);
		MoneyDAO moneyDAO = MoneyDAO.getInstance();
		
		String user = moneyUserT.getText();
		String money = moneyDateT.getText();
		String money2 = moneyDateT2.getText();
		
		moneyDAO.moneySearch(this,user,money,money2);
		
	}

	private void overdue() {
		modelToday.setNumRows(0);
		BookDAO bookDAO = BookDAO.getInstance();
		bookDAO.bookOverdue(this);
	}

	private void today() {
		modelToday.setNumRows(0);
		BookDAO bookDAO = BookDAO.getInstance();
		bookDAO.bookToday(this);
	}

	private void userSearch(int sw) {
		modelUser.setNumRows(0);
		String userName = userT.getText();
		
		if(userT.getText().length()<1&&sw==0) {	//if TextBox is null 
			JOptionPane.showMessageDialog(this, "검색할 이름을 입력해 주세요");		
			return;
		}

		ClientDAO clientDAO = new ClientDAO();
		clientDAO.userSearch(this,userName,sw);	
	}
	
	private void userErase() {
		idT.setText("");
		idT.setEditable(true);
		passwordT.setText("");
		nameT.setText("");
		callNumT.setText("");
		emailT.setText("");
		overdueT.setText("");

		userAddB.setEnabled(true);
		userUpdateB.setEnabled(false);
		userDeleteB.setEnabled(false);
	}

	private void userUpdate() {
		String id = idT.getText().toLowerCase();
		String email = emailT.getText();
		String pw = passwordT.getText();
		String phone = callNumT.getText();
		String name = nameT.getText();
		int overdue = Integer.parseInt(overdueT.getText());
		if(idT.getText().length()>0&&emailT.getText().length()>0&&passwordT.getText().length()>0&&callNumT.getText().length()>0&&nameT.getText().length()>0&&overdueT.getText().length()>0) {
			ClientDAO clientDAO = ClientDAO.getInstance();
			clientDAO.userUpdate(id, email, pw, phone, name, overdue);
			JOptionPane.showMessageDialog(this, "정보 수정 완료");	
			userErase();
			modelUser.setNumRows(0);
		}else	JOptionPane.showMessageDialog(this, "모든 값을 입력해 주세요");	
	}

	private void userDelete() {
		String id = idT.getText().toLowerCase();
		ClientDAO clientDAO = ClientDAO.getInstance();
		clientDAO.userDelete(id);
		
		userErase();
		modelUser.setNumRows(0);
		JOptionPane.showMessageDialog(this, "삭제 완료");		

	}

	public void userAdd() {
		userAddB.setEnabled(true);
		userDeleteB.setEnabled(false);
		userUpdateB.setEnabled(false);
		
		String id = idT.getText().toLowerCase();
		String email = emailT.getText();
		String pw = passwordT.getText();
		String phone = callNumT.getText();
		String name = nameT.getText();
		String overdue = overdueT.getText();
		
		ClientDAO clientDAO = new ClientDAO();
		if(idT.getText().length()>0&&emailT.getText().length()>0&&passwordT.getText().length()>0&&callNumT.getText().length()>0&&nameT.getText().length()>0&&overdueT.getText().length()>0) {
			clientDAO.conform(id);
			System.out.println(clientDAO.conform(id));
			if(clientDAO.conform(id)==0) {
				clientDAO.userAdd(this,id, email, pw, phone, name, overdue);	
				JOptionPane.showMessageDialog(this, "생성완료");	
				userErase();
				modelUser.setNumRows(0);
			}else JOptionPane.showMessageDialog(this, "이미 있는 아이디입니다.");	
		}else 	JOptionPane.showMessageDialog(this, "모든 값을 입력해 주세요");	
		
	}

	private void bookReturn() {
		String book = bookT2.getText();
		double isbn = Double.parseDouble(isbnT.getText());
		String genre = (String) genreName2.getSelectedItem();
		String publisher = publisherT.getText();
		String rentId = rentUserT.getText();
		String rentDate = rentT.getText();
		String returnDate = rentDateT.getText();
		Date returndate2=null;
		BookDTO bookDTO = new BookDTO();
		try {
			SimpleDateFormat sd = new SimpleDateFormat ("yyMMdd");
			Date today = new Date ();	
			returndate2 = sd.parse(returnDate);
			System.out.println(today);
			System.out.println(returndate2);
			String msg = Check(returndate2,today,rentId);
			JOptionPane.showMessageDialog(null, msg,
					"반납", JOptionPane.PLAIN_MESSAGE);
			rentId="";
			rentDate="";
			returnDate="";
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		bookDTO.setBook(book);
		bookDTO.setIsbn(isbn);
		bookDTO.setGenre(genre);
		bookDTO.setPublisher(publisher);
		bookDTO.setRentId(rentId);
		bookDTO.setRentDate(rentDate);
		bookDTO.setReturnDate(returnDate);
		
		BookDAO bookDAO = BookDAO.getInstance();
		ClientDAO clientDAO = ClientDAO.getInstance();
		
		bookDAO.bookReturn(bookDTO);
		
		BookErase();
		modelBook.setNumRows(0);
		modelAll.setNumRows(0);
		bookDAO.bookRentlist(this);
		
	}
	
	public String Check(Date a,Date b, String rentId){
		long cal =a.getTime() - b.getTime();
		long cal2 = cal / ( 24*60*60*1000);
		cal2 = Math.abs(cal2)*200;
		
		if(a.compareTo(b) > 0){
            return "반납완료되었습니다.";
        }else if(a.compareTo(b) < 0){
        	ClientDAO clientDAO = ClientDAO.getInstance();
        	clientDAO.ClientOverdue(rentId); 	
        	SimpleDateFormat sd = new SimpleDateFormat ("yyMMdd");
        	MoneyDAO moneyDAO = MoneyDAO.getInstance();
    		MoneyDTO moneyDTO = new MoneyDTO();
    		moneyDTO.setId(rentId);
    		moneyDTO.setLatefee(cal2);
    		System.out.println(rentId);
    		moneyDAO.add(moneyDTO);
        	
            return "연체 되었습니다."+cal2+"원 연체금액입니다.";
        }else{
            return "반납완료되었습니다.";
        }
	}

	private void bookRent() {
		String rentdate = JOptionPane.showInputDialog("대여일수");
		int rentdate2 = Integer.parseInt(rentdate);
		String rentuser = JOptionPane.showInputDialog("대여회원아이디");
		String book = bookT2.getText();
		double isbn = Double.parseDouble(isbnT.getText());
		String genre = (String) genreName2.getSelectedItem();
		String publisher = publisherT.getText();
		String rentId = rentuser;
		
		ClientDAO clientDAO = ClientDAO.getInstance();
		int sw= clientDAO.conform(rentuser);
		System.out.println(sw);
		if(sw==0) {
			JOptionPane.showMessageDialog(null, "존재하지 않는 회원입니다",
					"반납", JOptionPane.PLAIN_MESSAGE);
			return;
		}//수정한 부분입니다.
		
		
		SimpleDateFormat sd = new SimpleDateFormat ("yyMMdd");
		Date today = new Date ();
		String sysdate = sd.format (today);
		Date to = null;
		String returnDate = null;
		long money = today.getTime();
		Date money2 = null;
		try {
			to = sd.parse(sysdate);
			Calendar cal = Calendar.getInstance();
			cal.setTime(to);
			cal.add(Calendar.DATE,rentdate2);
			returnDate = sd.format(cal.getTime());
			money2 = cal.getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		long money3 = Math.abs( (money-money2.getTime())/ ( 24*60*60*1000) );
		long money4 = (money3+1)*100;
		MoneyDAO moneyDAO = MoneyDAO.getInstance();
		MoneyDTO moneyDTO = new MoneyDTO();
		moneyDTO.setId(rentId);
		moneyDTO.setLatefee(money4);
		moneyDAO.add(moneyDTO);
		
		BookDTO bookDTO = new BookDTO();
		
		bookDTO.setBook(book);
		bookDTO.setIsbn(isbn);
		bookDTO.setGenre(genre);
		bookDTO.setPublisher(publisher);
		bookDTO.setRentId(rentId);
		bookDTO.setRentDate(sysdate);
		bookDTO.setReturnDate(returnDate);
		
		BookDAO bookDAO = BookDAO.getInstance();
		int seq =bookDAO.findSeq(bookDTO);
		bookDTO.setSeq(seq);
		bookDAO.bookRent(bookDTO);
		
		JOptionPane.showMessageDialog(null, "대여 완료되었습니다",
				"대여", JOptionPane.PLAIN_MESSAGE);
		BookErase();
		modelBook.setNumRows(0);
		modelAll.setNumRows(0);
		bookDAO.bookRentlist(this);
	}

	private void bookUpdate() {
		String book = bookT2.getText();
		double isbn = Double.parseDouble(isbnT.getText());
		String genre = (String) genreName2.getSelectedItem();
		String publisher = publisherT.getText();
		String rentId = rentUserT.getText();
		String rentDate = rentT.getText();
		String returnDate = rentDateT.getText();
		
		BookDTO bookDTO = new BookDTO();
		bookDTO.setBook(book);
		bookDTO.setIsbn(isbn);
		bookDTO.setGenre(genre);
		bookDTO.setPublisher(publisher);
		bookDTO.setRentId(rentId);
		bookDTO.setRentDate(rentDate);
		bookDTO.setReturnDate(returnDate);
		
		BookDAO bookDAO = BookDAO.getInstance();
		
		bookDAO.bookUpdate(bookDTO);
		
		BookErase();
		modelBook.setNumRows(0);
		bookDAO.bookRentlist(this);
	}
	
	private void bookDelete() {
		String isbn = isbnT.getText();
		BookDAO bookDAO = BookDAO.getInstance();
		bookDAO.bookDelete(isbn);
		BookErase();
		bookDAO.bookRentlist(this);
	}

	private void bookAdd() {
		String book = bookT2.getText();
		
		if(book.equals("")) {
			return;
		}//수정한부분
		
		int isbn = Integer.parseInt(isbnT.getText());
		String genre = (String) genreName2.getSelectedItem();
		String publisher = publisherT.getText();
		String rentId = rentUserT.getText();
		String rentDate = rentT.getText();
		String returnDate = rentDateT.getText();
		
		BookDTO bookDTO = new BookDTO();
		bookDTO.setBook(book);
		bookDTO.setIsbn(isbn);
		bookDTO.setGenre(genre);
		bookDTO.setPublisher(publisher);
		bookDTO.setRentId(rentId);
		bookDTO.setRentDate(rentDate);
		bookDTO.setReturnDate(returnDate);
		
		BookDAO bookDAO = BookDAO.getInstance();
		int bookSeq = bookDAO.getBookSeq();
		bookDTO.setSeq(bookSeq);
		bookDAO.insert(bookDTO);
		
		BookErase();
		
	}

	private void BookErase() {
		isbnT.setText("");
		bookT2.setText("");
		publisherT.setText("");
		genreName2.setSelectedItem("소설");
		rentUserT.setText("");
		rentT.setText("");
		rentDateT.setText("");
		
		bookAddB.setEnabled(true);
		bookUpdateB.setEnabled(false);
		bookDeleteB.setEnabled(false);
		bookRentB.setEnabled(false);
		bookReturnB.setEnabled(false);
		rentT.setEnabled(false);
		rentDateT.setEnabled(false);
		rentUserT.setEnabled(false);
	}
	
	private void bookReset() {
		isbnT.setText("");
		bookT2.setText("");
		publisherT.setText("");
		genreName2.setSelectedItem("소설");
		rentUserT.setText("");
		rentT.setText("");
		rentDateT.setText("");
		bookAddB.setEnabled(true);
		bookUpdateB.setEnabled(false);
		bookDeleteB.setEnabled(false);
		bookRentB.setEnabled(false);
		bookReturnB.setEnabled(false);
		bookresetB.setEnabled(true);
		rentT.setEnabled(false);
		rentDateT.setEnabled(false);
		rentUserT.setEnabled(false);
	}

	private void bookSearchCombo() {
		modelBook.setNumRows(0);
		String genre = genreName.getSelectedItem().toString();
		
		BookDAO bookDAO = new BookDAO();
		bookDAO.bookSearchCombo(this,genre);
		genreName.getSelectedIndex();

	}

	private void bookSearch() {
		modelBook.setNumRows(0);
		String bookname = bookT.getText();
		if(bookT.getText().length()<1)return;
		BookDAO bookDAO = new BookDAO();
		bookDAO.bookSearch(this,bookname);	
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getSource()==tableBookList) {
			if(tableBookList.getSelectedRow() == -1) {
				return;
			}
			int rowAll = tableBookList.getSelectedRow();
			String book = tableBookList.getValueAt(rowAll, 0).toString();
			String isbn = tableBookList.getValueAt(rowAll, 1).toString();
			String genre = tableBookList.getValueAt(rowAll, 2).toString();
			String publisher =String.valueOf(tableBookList.getValueAt(rowAll, 3));
			String rentuser = String.valueOf(tableBookList.getValueAt(rowAll, 4));
			String rentdate = String.valueOf(tableBookList.getValueAt(rowAll, 5));
			String returndate = String.valueOf(tableBookList.getValueAt(rowAll, 6));

			bookT2.setText(book);
			isbnT.setText(isbn);
			genreName2.setSelectedItem(genre);
			publisherT.setText(publisher);
			rentUserT.setText(rentuser);
			rentT.setText(rentdate);
			rentDateT.setText(returndate);
			bookAddB.setEnabled(false);
			bookUpdateB.setEnabled(true);
			bookDeleteB.setEnabled(true);
			bookRentB.setEnabled(true);
			bookReturnB.setEnabled(true);
			bookresetB.setEnabled(true);
			rentT.setEnabled(false);
			rentDateT.setEnabled(false);
			rentUserT.setEnabled(false);
		}
		
		if(e.getSource()==tableUserList) {
			if(tableUserList.getSelectedRow()==-1) {
				return;
			}
			int row = tableUserList.getSelectedRow();
			idT.setText(tableUserList.getValueAt(row,0).toString());
			idT.setEditable(false);
			passwordT.setText(tableUserList.getValueAt(row,1).toString());
			nameT.setText(tableUserList.getValueAt(row,2).toString());
			callNumT.setText(tableUserList.getValueAt(row,3).toString());
			emailT.setText(tableUserList.getValueAt(row,4).toString());
			overdueT.setText((tableUserList.getValueAt(row,5).toString()));
			
			userAddB.setEnabled(false);
			userUpdateB.setEnabled(true);
			userDeleteB.setEnabled(true);
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {		
	}

	public DefaultTableModel getModelAll() {
		return modelAll;
	}

	public void setModelAll(DefaultTableModel modelAll) {
		this.modelAll = modelAll;
	}
	
	public DefaultTableModel getModelBook() {
		return modelBook;
	}

	public void setModelBook(DefaultTableModel modelBook) {
		this.modelBook = modelBook;
	}
	
	public DefaultTableModel getModelUser() {
		return modelUser;
	}

	public void setModelUser(DefaultTableModel modelUser) {
		this.modelUser = modelUser;
	}

	public DefaultTableModel getModelToday() {
		return modelToday;
	}

	public DefaultTableModel getModelMoney() {
		return modelMoney;
	}

	public void setModelMoney(DefaultTableModel modelMoney) {
		this.modelMoney = modelMoney;
	}

	public void setModelToday(DefaultTableModel modelToday) {
		this.modelToday = modelToday;
	}
	
	public DefaultTableModel getModelClosing() {
		return modelClosing;
	}

	public void setModelClosing(DefaultTableModel modelClosing) {
		this.modelClosing = modelClosing;
	}



}
