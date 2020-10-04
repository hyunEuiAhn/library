package Client;


import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class UserDAO {

    private String driver = "oracle.jdbc.driver.OracleDriver";
    private String url = "jdbc:oracle:thin:@192.168.51.30:1521:xe";
    private String user = "library";
    private String password = "library";
    
    

    private String id;
    private int rentCount;

    private static UserDAO instance;

    public static UserDAO getInstance() {

        synchronized (UserDAO.class) {
            if (instance == null) {
                instance = new UserDAO();
            }
            return instance;
        }

    } //getInstance

    public UserDAO() {

        try {
            Class.forName(driver);
            System.out.println("����̹��ε� ����");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    public Connection getConnection() {
        Connection conn = null;

        try {
            conn = DriverManager.getConnection(url,user,password);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return conn;


    }

    public void getUserInfo(UserDTO userDTO) {
        id = userDTO.getId();
        System.out.println(id);

        Connection conn = getConnection();
        PreparedStatement pstmt = null;

        ResultSet rs = null;
        String sql = "select*from Client where ID = ?";

        try {

            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, id);

           // pstmt.setString(1, userDTO.getId());
            rs = pstmt.executeQuery( );


            while (rs.next()) {
                System.out.println("����info �۵��� ");
                userDTO.setName(rs.getString("name"));
                userDTO.setPhone(rs.getString("callnum"));
                userDTO.setMail(rs.getString("email"));
                userDTO.setRank(rs.getInt("latefee"));
               // userDTO.setPP(rs.getString("pic"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            try{
                if(rs!=null)rs.close();
                if(pstmt!=null)pstmt.close();
                if(conn!=null)conn.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }

        //System.out.println(userDTO.getName()); //null �� ����

    }//getUserInfo


    public ArrayList<RentDTO> getRentList(){


        ArrayList<RentDTO> rentList = new ArrayList<RentDTO>();
        Connection conn = getConnection();
        PreparedStatement pstmt= null;
        
        Date day = new Date();
      	SimpleDateFormat dateF = new SimpleDateFormat("yyMMdd"); 
      	int today = Integer.parseInt(dateF.format(day));

        ResultSet rs = null;
        String sql = "Select*from book where RENTID = ? order by returndate asc ";

        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, id);
            rs = pstmt.executeQuery();
            System.out.println("���Ϲ� �۵���");

            while (rs.next()) {

                RentDTO rentDTO = new RentDTO();
                rentDTO.setBook(rs.getString("book"));
                rentDTO.setReturndate(rs.getString("returndate"));
                rentList.add(rentDTO);
                
                /*
                if(today<Integer.parseInt(rs.getString("returndate"))) {
                	 rentList.add(rentDTO);
                     // System.out.println("��Ʈ����Ʈ �۵��� "+rs.getString("book"));
                }
                */
               
            }

        }catch(NullPointerException e){
        	e.printStackTrace();
        	
        }catch(SQLException e){
            e.printStackTrace();
            rentList = null;
        }finally{
            try{
                if(rs!=null) rs.close();
                if(pstmt!=null)pstmt.close();
                if(conn!=null)conn.close();
            }catch(SQLException e){
                e.printStackTrace();
            }
        }

        return rentList;

    }//��Ʈ ����Ʈ

    public ArrayList<BookDTO> typeSearch(int select){
        int type = select;
        String sbook = null;
        if(type ==0) {
            sbook = "�Ҽ�";
        }else if(type ==1){
            sbook = "�н�";
        }

        ArrayList<BookDTO> typeList = new ArrayList<BookDTO>();

        Connection conn = getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        String sql = "Select*from book where GENRE =?";

        try{
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,sbook);
            rs=pstmt.executeQuery();
            while(rs.next()){
                BookDTO bookDTO = new BookDTO();
                bookDTO.setBookName(rs.getString("book"));
                bookDTO.setType(rs.getString("Genre"));
                bookDTO.setReturndate(rs.getString("returndate"));
                bookDTO.setRentid(rs.getString("rentID"));//���̵� �߰� --------------------->Ȯ�οϷ�
                bookDTO.setBookPic(rs.getString("pic"));
                typeList.add(bookDTO);

            }
        }catch(SQLException e){
            e.printStackTrace();
            typeList = null;
        }finally{
            try{
                if(rs!=null) rs.close();
                if(pstmt!=null)pstmt.close();
                if(conn!=null)conn.close();
            }catch(SQLException e){
                e.printStackTrace();
            }
        }
        return typeList;

    }

    public ArrayList<BookDTO> titleSearch(String title) {

        ArrayList<BookDTO> titleList = new ArrayList<BookDTO>();
        Connection conn = getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        String sql = "Select*from book where book like ?";
        try{
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,"%"+title+"%");
            rs=pstmt.executeQuery();
            while(rs.next()){
                BookDTO bookDTO = new BookDTO();
                bookDTO.setBookName(rs.getString("book"));
                bookDTO.setType(rs.getString("Genre"));
                bookDTO.setReturndate(rs.getString("returndate"));
                bookDTO.setRentid(rs.getString("rentID"));//���̵� �߰� --------------------->Ȯ�οϷ�
                bookDTO.setBookPic(rs.getString("pic"));
                titleList.add(bookDTO);

            }
        }catch(SQLException e){
            e.printStackTrace();
            titleList = null;
        }finally{
            try{
                if(rs!=null) rs.close();
                if(pstmt!=null)pstmt.close();
                if(conn!=null)conn.close();
            }catch(SQLException e){
                e.printStackTrace();
            }
        }
        return titleList;
    }
    
    public BookDTO userClicked(String title){
    	
    	//BookDTO userClicked = new BookDTO();
    	
    	BookDTO userClick = new BookDTO();
    	Connection conn = getConnection();
    	PreparedStatement pstmt = null;
    	ResultSet rs = null;
    	String sql = "Select*from book where book=?";
    	
    	try {
    		pstmt = conn.prepareStatement(sql);
    		pstmt.setString(1,title);
    		rs=pstmt.executeQuery();
    		while(rs.next()){
    			
    			userClick.setBookName(rs.getString("book")); 	
    			
    			userClick.setbookDis("�� �Ҽ��� ������ ������ ���� /r ǥ��� �� �Դϴ�. /r �� �� �ϼſ�~ ");
    			userClick.setbookPub(rs.getString("publisher"));
    			System.out.println(rs.getString("publisher"));
    			userClick.setBookPic(rs.getString("pic"));
    			System.out.println(rs.getString("pic"));
    			
    		}
    	}catch(SQLException e){
            e.printStackTrace();
            userClick = null;
        }finally{
            try{
                if(rs!=null) rs.close();
                if(pstmt!=null)pstmt.close();
                if(conn!=null)conn.close();
            }catch(SQLException e){
                e.printStackTrace();
            }
        }
    	
    	return userClick;
    	
    
    
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
