package Client;

import librarySystem.*;

import java.awt.Color;
import java.awt.Container;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

	public class Client extends JFrame implements ActionListener,MouseListener{
		public Client() {
		}
    private JLabel nameL, phoneL, emailL, addL, myL, idL, booksrcL, booknameL, isbnL, searchedbookL, companyL, writerL, rentL, bookName, bookAuth, bookType, bookin;
    private JLabel infoL, name, phone, mail, add;
    private JTextField bookInfo;
    private JTextField bookSearch;

    private JComboBox<String> typeBox;
    private JButton rentBT, typeBT, searchBT, infoBT, logoutBT;

    //å�˻� JTble �� �ۼ�
    private DefaultTableModel bookModel;
    private JTable bookModelT;
    //�뿩���
    private DefaultTableModel rentModel;
    private JTable rentModelT;

    private DefaultTableModel lateModel;
    private JTable lateModelT;

    private String id;
    public Login login;
    
    private String pp;
    private String bookPic;
    
    private  JLabel bookBox ;
    private ImageIcon bookImg;
    
    private JLabel ImgBox;
    private ImageIcon img;
  
    

    public void clientMain(Login login, String id) {
    	this.login = login;
        this.id = id;

        getContentPane().setLayout(null);

        //---con ���� ��ġ -----------------------------------------
        myL = new JLabel("�����");
        idL = new JLabel("ID : ");
        infoBT = new JButton("ȸ������");
        logoutBT = new JButton("�α׾ƿ�");
        //-----P �г�  �׸� ----------------------------------------------
        nameL = new JLabel("�̸�");
        phoneL = new JLabel("��ȭ");
        emailL = new JLabel("����");
        addL = new JLabel("�ּ�");

        name = new JLabel("���»�");
        phone = new JLabel("010 - 1111 -1111 ");
        mail = new JLabel("test@testmail.com");
        add = new JLabel("���� ��� ���� ��ġ ");

        //----p1 �г� �׸� ----------------------------------------
        bookName = new JLabel("");
        bookAuth = new JLabel("");
        bookType = new JLabel("");
        bookin = new JLabel("");  //--------------------���ǻ�� ����
        //-----p2 �г� �׸�------------------------------------------------
        bookInfo = new JTextField();

        //-----p3 �г� �׸�--------------------------------------------

        rentBT = new JButton("�뿩����");
        //-----p4a �г� �׸�--------------------------------------------------
        String[] rented = {"�뿩���", "�ݳ���"};


        rentModel = new DefaultTableModel(rented,100) {
            public boolean isCellEditable(int rowindex, int mCollIndex) {
                return false;
            }
        };


        rentModelT = new JTable(rentModel);
        rentModelT.getColumnModel().getColumn(0).setPreferredWidth(150);

        rentModelT.getTableHeader().setReorderingAllowed(false);
        rentModelT.getTableHeader().setResizingAllowed(false);

        JScrollPane rp = new JScrollPane(rentModelT);
        rp.setBounds(0, 0, 200, 220);



        //---p4b �г� �׸�--------------------------------------------------

        String[] late = {"��ü���", "��ü��"};
        lateModel = new DefaultTableModel(late, 100) {
            public boolean isCellEditable(int rowindex, int mCollIndes) {
                return false;
            }
        };


        lateModelT = new JTable(lateModel);

       // lateModel.insertRow(1,new Object[]{"�׽�Ʈ","������"});


        lateModelT.getColumnModel().getColumn(0).setPreferredWidth(250);

        lateModelT.getTableHeader().setReorderingAllowed(false);
        lateModelT.getTableHeader().setResizingAllowed(false);

        JScrollPane lp = new JScrollPane(lateModelT);
        lp.setBounds(0, 0, 290, 220);





        //--//---p5 �ϴ� �׸�------------------------------------------------------
        String[] select = {"�Ҽ�", "�н�"};
        typeBox = new JComboBox<String>(select);
        typeBT = new JButton("�帣�˻�");
        bookSearch = new JTextField(20);
        searchBT = new JButton("����˻�");
        booksrcL = new JLabel("�����˻�");
        booknameL = new JLabel("å�̸�");
        isbnL = new JLabel("ISBN");
        searchedbookL = new JLabel("����ã�� å �̸� ");
        companyL = new JLabel("���ǻ�");
        writerL = new JLabel("����");
        rentL = new JLabel("�뿩����");
        infoL = new JLabel("");

        //-----------�ϰ˻� ------P5---JTable model --------------
        String[] n = {"�帣", "������", "������Ȳ", "�ݳ���"};

        bookModel = new DefaultTableModel(n, 100) {
        	public boolean isCellEditable(int rowindex, int mCollIndes) {
                return false;
            }
        };

        bookModelT = new JTable(bookModel);

        bookModelT.getColumnModel().getColumn(0).setPreferredWidth(10);
        bookModelT.getColumnModel().getColumn(1).setPreferredWidth(200);
        bookModelT.getColumnModel().getColumn(2).setPreferredWidth(15);
        bookModelT.getColumnModel().getColumn(3).setPreferredWidth(15);

        bookModelT.getTableHeader().setResizingAllowed(false);

        JScrollPane jp = new JScrollPane(bookModelT);
        //bookModelT.setBounds(0, 0, 500, 220);
        jp.setBounds(0, 0, 500, 220);

//------------�ݷ��� �ϳ��ۿ� �ȵ� -------JLIst model -------------
/*       	testModel = new DefaultListModel <BookDTO>();
        	testList = new JList<BookDTO>(testModel);
        	testList.setBounds(0,0,200,220);

        	BookDTO bookDTO = new BookDTO();
        	testModel.addElement(bookDTO);

*/
//-----�гξƿ����� ----------------------------------------------------------------------------
        
       
        
        Panel p = new Panel();
        
        
        //pp
        img = new ImageIcon(pp);
          
        ImgBox = new JLabel(img);
        ImgBox.setBounds(0,0,110,110);
        p.setBackground(new Color(209,209,209));
        p.setBounds(570, 50, 110, 110);
        
        System.out.println(pp);
        
        
        Panel p1 = new Panel(null);
       // p1.setBackground(Color.orange);
        p1.setBounds(570, 180, 280, 140);
        
        //String bpic; 
        //ImageIcon bookImg = new ImageIcon(bpic);
        
        //ImageIcon bookImg = new ImageIcon("image/b1.jpg");
        bookPic="";
        bookImg = new ImageIcon(bookPic);

        bookBox = new JLabel(bookImg);
        bookBox.setBounds(0,0,100,140);
        Panel bookPic = new Panel(null);
        //bookPic.setBackground(Color.red);
        bookPic.setBounds(0,0,100,140);
        
        
        Panel p2 = new Panel(null);
        p2.setBackground(Color.black);
        p2.setBounds(570, 330, 280, 160);
        
        

        Panel p3 = new Panel(null);
        p3.setBackground(Color.RED);
        p3.setBounds(570, 500, 280, 30);

        Panel p4a = new Panel(null);
        p4a.setBackground(Color.PINK);
        p4a.setBounds(50, 50, 200, 220);

        Panel p4b = new Panel(null);
        p4b.setBackground(Color.blue);
        p4b.setBounds(260, 50, 290, 220);

        Panel p5 = new Panel(null);
        p5.setBackground(Color.green);
        p5.setBounds(50, 310, 500, 220);

//-----�����ġ ----------------------------------------------------------------------------------
        idL.setBounds(690, 48, 150, 20);
        myL.setBounds(605, 15, 50, 20);
        infoBT.setBounds(660, 15, 90, 18);
        infoBT.setBackground(new Color(228,228,228));
        
        logoutBT.setBounds(760, 15, 90, 18);        
        logoutBT.setBackground(new Color(228,228,228));
//-----p ����ġ ----------------------------------------------------------------------------------
        infoL.setBounds(50, 15, 900, 20);
        
        
        nameL.setBounds(690, 78, 30, 20);
        phoneL.setBounds(690, 108, 30, 20); //���ڴ� 15������
        emailL.setBounds(690, 138, 45, 20);
        addL.setBounds(690, 128, 30, 20);

        name.setBounds(730, 78, 45, 20);
        phone.setBounds(730, 108, 250, 20);
        mail.setBounds(730, 138, 250, 20);
        add.setBounds(730, 128, 250, 20);

//---p1 �� ��ġ --------------------------------------------------
        bookName.setBounds(110, 10, 250, 20);
        bookAuth.setBounds(110, 40, 100, 20);
       // bookType.setBounds(110, 70, 45, 20);
        bookin.setBounds(110, 70, 250, 20);
//--p2 �ؽ�Ʈ �ʵ� ��ġ (�ٰŸ�)--------------------------------------------------
        bookInfo.setBounds(0, 0, 280, 160);
//---p3 ��ư ��ġ ----------------------------------------------------------------
        rentBT.setBounds(0, 0, 280, 30);
        rentBT.setBackground(new Color(210,210,210));
//---p4 �ϴ� ��ƿ ��ġ----------------------------------------------------------------
        typeBox.setBounds(50, 280, 70, 20);
        typeBT.setBounds(130, 280, 90, 20);
        typeBox.setBackground(new Color(228,228,228));
        typeBT.setBackground(new Color(210,210,210));

        bookSearch.setBounds(250, 280, 200, 21);
        searchBT.setBounds(460, 280, 90, 20);
        searchBT.setBackground(new Color(210,210,210));
//----p5---------------------------------------------------------------


//-------------------------------------------------------------------
        Container con = getContentPane();

        con.add(infoL);

        con.add(idL);
        con.add(myL);
        con.add(infoBT);
        con.add(logoutBT);

        con.add(nameL);
        con.add(name);
        con.add(phoneL);
        con.add(phone);
        con.add(emailL);
        con.add(mail);
       // con.add(addL);  //-------------�ּһ���
       // con.add(add);

        con.add(p);
        p.add(ImgBox);//-------------�۾���

        con.add(p1);
        bookPic.add(bookBox);//-------------����???
        p1.add(bookName);
        p1.add(bookAuth);
        //p1.add(bookType);
        p1.add(bookin);
        p1.add(bookPic); //���� ��ġ 
        

        con.add(p2);
       
        p2.add(bookInfo);

        con.add(p3);
        p3.add(rentBT);

        con.add(p4a);
        //p4a.add(testList);
        p4a.add(rp);

        con.add(p4b);
        p4b.add(lp);

        con.add(typeBox);
        con.add(typeBT);
        con.add(bookSearch);
        con.add(searchBT);

        con.add(p5);
        // p5.add(bookModelT);
        p5.add(jp);

        //name.setText(id);

        login();
        event();


        setVisible(true);
        setResizable(true);
        setBounds(10, 10, 900, 600);
        
        bookModelT.addMouseListener(this);
        
        this.addWindowListener(new WindowAdapter(){//������ �ٽ� �α��� 
			@Override
			public void windowClosing(WindowEvent e){
				JOptionPane.showMessageDialog(login, "���α׷��� �����մϴ�.");
				dispose();	//����â ����
				login.setVisible(true);
			}
		});
        

    }

    public void event() {

        rentBT.addActionListener(this); //�뿩����

        typeBT.addActionListener(this); //�帣�˻�
        searchBT.addActionListener(this); // ����˻�

        infoBT.addActionListener(this); // ȸ������ --> Ŭ����ȣ��
        logoutBT.addActionListener(this);//�α׾ƿ�

    }//��ư �׼� �ֱ�

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == logoutBT) {
            //�α׾ƿ�â ȣ��
			JOptionPane.showMessageDialog(login, "���α׷��� �����մϴ�.");
            dispose();
            login.setVisible(true);
            //Login.login.setVisible(true);
        } else if (e.getSource() == infoBT) {
        	
        	
        	  	
        	InfoUpdate infoUpdate = new InfoUpdate();
        	infoUpdate.start(Client.this, id);
			//setVisible(false);
            //�������� ������ ȣ��
			
					
			
        } else if (e.getSource() == typeBT) {
            //�帣 �˻� ��ư
            //typeSearch ȣ��
            typeSearch();
            System.out.println("�帣�˻� ��ư");

        } else if (e.getSource() == searchBT) {
            //titleSearch ȣ��
            titleSearch();
            System.out.println("����˻� ��ư");
        } else if (e.getSource() == rentBT) {
        	
        	
        	bookPic= "image/b5.jpg";
        	
        	bookImg = new ImageIcon(bookPic);

            //bookBox = new JLabel(bookImg);
            
            bookBox.setIcon(bookImg);
            
            bookName.setText("");
            bookAuth.setText("");
            bookType.setText("");
            bookin.setText(""); 
            bookInfo.setText("");
			JOptionPane.showMessageDialog(this, "�����ڿ��� �޼����� �����Ͽ����ϴ�.");							
        }

    }//��ư ���

    public void login()  {

        UserDTO userDTO = new UserDTO();
        userDTO.setId(id);

        UserDAO userDAO = UserDAO.getInstance();
        userDAO.getUserInfo(userDTO);

        idL.setText("ID : "+userDTO.getId());
        name.setText(userDTO.getName());
        phone.setText(userDTO.getPhone());
        mail.setText(userDTO.getMail());
        myL.setText(userDTO.getRank()+"���");
        
       // pp = userDTO.getPP();//----------�۾��� 
        
        int rankPic = userDTO.getRank();
        System.out.println(rankPic);
        
        
        
        if(rankPic == 0) {pp = "image/lateface0.jpg" ;}
        else if(rankPic == 1) {pp = "image/lateface1.jpg" ;}
        else if(rankPic == 2) {pp = "image/lateface2.jpg" ;}
        else if(rankPic == 3) {pp = "image/lateface3.jpg" ;}
        else if(rankPic == 4) {pp = "image/lateface4.jpg" ;}
        else if(rankPic > 4) {
        	pp = "image/lateface5.jpg" ;
        	rentBT.setEnabled(false);
        	
        	bookInfo.setText("��üȽ�� �ʰ��� �뿩�� �Ұ��� �մϴ�.");
        }
        
        
       // private JLabel ImgBox;
        //private ImageIcon img;
        img = new ImageIcon(pp);
        ImgBox.setIcon(img);
        
        
        
        //�뿩����Ʈ�� �޾ƿ�

        ArrayList<RentDTO> rentList = userDAO.getRentList();
        int rent =0;
        int late =0;
        
        
        
        Date day = new Date();
    	SimpleDateFormat dateF = new SimpleDateFormat("yyMMdd"); 
    	int today = Integer.parseInt(dateF.format(day));
        
    	
    	
        for(RentDTO rentDTO : rentList){
            
        	if(today<Integer.parseInt(rentDTO.getReturndate())) {
	        	rentModel.insertRow(rent,new Object[]{ rentDTO.getBook(),rentDTO.getReturndate()});
	            rent++;
            }
        	
            int fee = 100;
            
            if(today > Integer.parseInt(rentDTO.getReturndate())){
            	//���� ��¥ �޾ƿ���
            	
                int rentday = today - Integer.parseInt(rentDTO.getReturndate());

                lateModel.insertRow(late,new Object[]{rentDTO.getBook(),rentday*fee+"��"});
                late++;
               
            }

            
        }
        
        
        //���� ������ ----------------------------------------------------------
       
        //if(rentModel.getValueAt(1,0).
        if(rent !=0) {
	        String title = rentModel.getValueAt(0,0).toString();
	        SimpleDateFormat sd =new SimpleDateFormat("yyMMdd");
	        Date a = null;
	        Date b = null;
	        
			try {
				a = sd.parse((String) rentModel.getValueAt(0,1));
				String num = String.valueOf(today);
		        b= sd.parse(num);
		        System.out.println(num);
		        
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
			long cal =a.getTime() - b.getTime();
			long cal2 = cal / ( 24*60*60*1000);
			cal2 = Math.abs(cal2);

	        int remainDay = Integer.parseInt(rentModel.getValueAt(0,1).toString()) - today;
	        //System.out.println(title);
	        //System.out.println(cal2);
	        
	        infoL.setText("'"+title+"'" + "  �� �뿩����   "+cal2+" ��   ���ҽ��ϴ�.");
        }
        
        
        //------------------------------------------------------------
        

    } //�α��� �޼ҵ�

    public void typeSearch(){
        bookModel.setRowCount(0);

       // String[] n = {"�帣", "������", "������Ȳ", "�ݳ���"};
        int select = typeBox.getSelectedIndex();

        System.out.println(select);
        UserDAO userDAO = UserDAO.getInstance();

        ArrayList<BookDTO> typeList = userDAO.typeSearch(select);
        int number = 0;
        for(BookDTO bookDTO : typeList){
        	String bookStatus = "�뿩����";//-----------�뿩��Ȳ
        	if(bookDTO.getRentid()!=null) {//---------�뿩��Ȳ if �� 
        		bookStatus = "�뿩��";
        	}
            bookModel.insertRow(number,new Object[]{bookDTO.getType(),bookDTO.getBookName(),bookStatus,bookDTO.getReturndate()});

        }

    }

    public void titleSearch(){

        bookModel.setRowCount(0);

        String title = bookSearch.getText();
        System.out.println(title);

        UserDAO userDAO = UserDAO.getInstance();
        ArrayList<BookDTO> titleList = userDAO.titleSearch(title);
        int number=0;
        for(BookDTO bookDTO : titleList){
        	String bookStatus = "�뿩����";//-----------�뿩��Ȳ
        	if(bookDTO.getRentid()!=null) {//---------�뿩��Ȳ if �� 
        		bookStatus = "�뿩��";
        	}
            bookModel.insertRow(number,new Object[]{bookDTO.getType(),bookDTO.getBookName(),bookStatus,bookDTO.getReturndate()});
        }
    }
    
   public void	mouseClicked(MouseEvent e) {
	   if(bookModelT.getSelectedRow()==-1) {
		   return;
	   }
	   int row = bookModelT.getSelectedRow();
	   System.out.println(row);
	   
	   String title = bookModelT.getValueAt(row,1).toString();
	   
	   
	   UserDAO userDAO = UserDAO.getInstance();
	   BookDTO userClick = userDAO.userClicked(title);
	  	  
	   bookName.setText(userClick.getBookName());
	   bookAuth.setText(userClick.getbookPub());
	   bookin.setText(userClick.getbookDis());
	   
	   //null �� ���� 
	   System.out.println(userClick.getBookPic());
	   
	   bookPic=userClick.getBookPic();
	   bookImg = new ImageIcon(bookPic);
	   bookBox.setIcon(bookImg);	   

   }
    
   public void	mouseEntered(MouseEvent e) {}
    
   public void	mouseExited(MouseEvent e) {}
    
   public void	mousePressed(MouseEvent e) {}
    
   public void	mouseReleased(MouseEvent e) {}
    

    
    
    
    
    
    
    
    

}
