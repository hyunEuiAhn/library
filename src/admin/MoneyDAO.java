package admin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

public class MoneyDAO {
	private String driver = "oracle.jdbc.driver.OracleDriver";
	private String url = "jdbc:oracle:thin:@192.168.51.30:1521:xe";
	private String user = "library";
	private String password = "library";
	private ResultSet rs;
	
	private static MoneyDAO instance;	//new로 생성하지 않음
	
	public static MoneyDAO getInstance() {
		synchronized(MoneyDAO.class){	
		if(instance==null){
				instance = new MoneyDAO();
			}
		}
		return instance;
	}
	
	public MoneyDAO() {
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

	public void add(MoneyDTO md) {
		Connection conn = getConnection();
		PreparedStatement pstmt = null;		
		String sql = "insert into money values(?,?,TO_CHAR(sysdate,'YYMMDD'))";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, md.getId());
			pstmt.setDouble(2, md.getLatefee());
			pstmt.executeUpdate();
			//돈넣기
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

	public void moneySearch(Admin ad, String user2, String money, String money2) {
		 Connection conn = getConnection();
		 PreparedStatement pstmt = null;
		 rs =null;
		 String sql = "select * from money where id ='"+user2+"' or RENTDATE "
		 		+ "between to_date('"+money+"', 'YYMMDD') and to_date('"+money2+"', 'YYMMDD')";
			try {
				SimpleDateFormat sd = new SimpleDateFormat("yyyyMMdd");
				pstmt = conn.prepareStatement(sql);
				rs = pstmt.executeQuery();
					while(rs.next()) {
						ad.getModelMoney().addRow(new Object[] {rs.getString("ID"),
								rs.getInt("LATEFEE"),sd.format(rs.getDate("RENTDATE"))  });
					}
				
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
	
	public void userSearch(Admin ad, String user2) {
		 Connection conn = getConnection();
		 PreparedStatement pstmt = null;
		 rs =null;
		 String sql = "select * from money where id like '%"+user2+"%'";
			try {
				SimpleDateFormat sd = new SimpleDateFormat("yyyyMMdd");
				pstmt = conn.prepareStatement(sql);
				rs = pstmt.executeQuery();
					while(rs.next()) {
						ad.getModelMoney().addRow(new Object[] {rs.getString("ID"),
								rs.getInt("LATEFEE"),sd.format(rs.getDate("RENTDATE"))  });
					}
				
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

	public void moneySum(Admin ad, String sum) {
		Connection conn = getConnection();
		 PreparedStatement pstmt = null;
		 rs =null;
		 String sql = "select sum(LATEFEE) from MONEY where to_char(RENTDATE,'YYYYMM') "
		 		+ "BETWEEN '"+sum+"01' and '"+sum+"12' group by "+ 
		 		"to_char(RENTDATE,'MM') order by to_char(RENTDATE,'MM') asc";
			try {
				pstmt = conn.prepareStatement(sql);
				rs = pstmt.executeQuery();
				int hap[] = new int[12];
				for(int i=0;i<12;i++) {
					rs.next();
					hap[i]=rs.getInt(1);
				}
				ad.getModelClosing().addRow(new Object[] {hap[0],hap[1],hap[2],hap[3],hap[4],
						hap[5],hap[6],hap[7],hap[8],hap[9],hap[10],hap[11],});
				
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
	
	
}
