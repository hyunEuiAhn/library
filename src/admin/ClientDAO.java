package admin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class ClientDAO {
	private String driver = "oracle.jdbc.driver.OracleDriver";
	private String url = "jdbc:oracle:thin:@192.168.51.30:1521:xe";
	private String user = "library";
	private String password = "library";
	
	
	private static ClientDAO instance;	//new로 생성하지 않음
	
	public static ClientDAO getInstance() {
		synchronized(ClientDAO.class){	
		if(instance==null){
				instance = new ClientDAO();
			}
		}
		return instance;
	}
	
	public ClientDAO() {
		//int seq = getSeq();
		//System.out.println(seq);
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public Connection getConnection() {
		Connection conn = null;

		try {
			conn = DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	public int getSeq() {
		int seq=0;
		Connection conn = getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select seq.nextval from dual";
		
		try {
			pstmt = conn.prepareStatement(sql); 
			rs = pstmt.executeQuery();
			
			rs.next();
			seq = rs.getInt(1);
			
		} catch (SQLException e) {
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
		
		return seq;
	}

	public void userSearch(Admin admin, String userName, int sw) {
		Connection conn = getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs =null;
		String sql = null;
		if(sw ==0) {
			sql = "select * from client where name like '%"+userName+"%'";
		}else if(sw ==1) {
			sql = "select * from client ";
		}
		try {

		pstmt = conn.prepareStatement(sql);
		rs = pstmt.executeQuery();
		
		
		while(rs.next()) {
			admin.getModelUser().addRow(new Object[] {rs.getString("id"),rs.getString("password"),rs.getString("name"),
					rs.getString("callnum"),rs.getString("email"),rs.getInt("latefee")});
			
			//getClientList();
			
		}//while

		} catch(SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs != null)rs.close();
				if(pstmt != null)pstmt.close();
				if(conn != null)conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void userAdd(Admin admin, String id, String email, String pw, String phone, String name, String overdue) {
		Connection conn = getConnection();
		PreparedStatement pstmt = null;		
		String sql = "insert into Client values(?,?,?,?,?,?)";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, pw);
			pstmt.setString(3, name);
			pstmt.setString(4, phone);
			pstmt.setString(5, email);
			pstmt.setString(6, overdue);
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
	
	public int conform( String conform) {	//로그인 버튼 눌렀을 때
		Connection conn = getConnection();
		PreparedStatement pstmt=null;
		ResultSet rs = null;
		int sw = 0;
		
		String sql = "select * from client where id='"+conform+"'";
		
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				System.out.println(rs.getString("id"));
				if(rs.getString("id").equals(conform)) {
					sw=1;
						
					}//아이디가 같으면
				}			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	
		return sw;
	}//conform
	
	public void userUpdate(String id, String email, String pw, String phone, String name, int overdue) {
		Connection conn = getConnection();
		PreparedStatement pstmt = null;		
		String sql = "update client SET id=?,password=?, name=?,callnum=?,email=?,latefee=? where id = '"+id+"'";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, pw);
			pstmt.setString(3, name);
			pstmt.setString(4, phone);
			pstmt.setString(5, email);
			pstmt.setInt(6, overdue);
			
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


	public void userDelete(String id) {
		Connection conn = getConnection();
		PreparedStatement pstmt = null;	
		
		String sql = "delete from client where id=?";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
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

	public void ClientOverdue(String rentId) {
		Connection conn = getConnection();
		PreparedStatement pstmt = null;		
		System.out.println(rentId);
		String sql = "update client set latefee=latefee+1 where id = '"+rentId+"'";

		try {
			pstmt = conn.prepareStatement(sql);
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

}
	

