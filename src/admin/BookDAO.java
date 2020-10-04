package admin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class BookDAO {
	private String driver = "oracle.jdbc.driver.OracleDriver";
	private String url = "jdbc:oracle:thin:@192.168.51.30:1521:xe";
	private String user = "library";
	private String password = "library";
	private ResultSet rs;
	
	private static BookDAO instance;
	
	public static BookDAO getInstance() {
		synchronized (BookDAO.class) {
			if(instance==null) {
				instance = new BookDAO();
			}
		}
		return instance;
	}//getInstance
	
	public BookDAO() {
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}//BookDAO
	
	
	public Connection getConnection() {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url,user,password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}//getConnection
	

	public void bookSearch(Admin ad,String bookname) {
		 Connection conn = getConnection();
		 PreparedStatement pstmt = null;
		 rs =null;
		 String sql = "select distinct book,isbn,genre,publisher,rentid,"
		 		+ "rentdate,returndate from book where book like '%"+bookname+"%'";
			try {
				pstmt = conn.prepareStatement(sql);
				rs = pstmt.executeQuery();
					while(rs.next()) {
						ad.getModelBook().addRow(new Object[] {rs.getString("BOOK"),rs.getDouble("ISBN"),rs.getString("GENRE"),
								rs.getString("PUBLISHER"),rs.getString("RENTID"),rs.getString("RENTDATE"),
								rs.getString("RETURNDATE")});
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
			return;
	}//bookSearch

	public void bookSearchCombo(Admin ad, String genre) {
		Connection conn = getConnection();
		PreparedStatement pstmt = null;
		rs = null;
		String sql = "select distinct book,isbn,genre,publisher,rentid,"
		 		+ "rentdate,returndate from book where genre like '%"+genre+"%'";
			try {
					pstmt = conn.prepareStatement(sql);
					rs = pstmt.executeQuery();
						while(rs.next()) {
							ad.getModelBook().addRow(new Object[] {rs.getString("BOOK"),rs.getDouble("ISBN"),rs.getString("GENRE"),
									rs.getString("PUBLISHER"),rs.getString("RENTID"),rs.getString("RENTDATE"),
									rs.getString("RETURNDATE")});
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
				}	return;

	}//bookSearchCombo
	
	
	public int getBookSeq() {
		int bookSeq=0;
		Connection conn = getConnection();
		PreparedStatement pstmt = null;
		String sql = "select seq_book.nextval from dual";
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			rs.next();
			bookSeq = rs.getInt(1);
		}catch (SQLException e) {
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
		return bookSeq;
	}
	
	
	public void insert(BookDTO bookDTO) {
		Connection conn = getConnection();//호출
		PreparedStatement pstmt = null;
		String sql = "insert into book(seq,book,isbn,publisher,genre) values(?,?,?,?,?)";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, bookDTO.getSeq());
			pstmt.setString(2, bookDTO.getBook());
			pstmt.setDouble(3, bookDTO.getIsbn());
			pstmt.setString(4, bookDTO.getGenre());
			pstmt.setString(5, bookDTO.getPublisher());
			//pstmt.setString(6, bookDTO.getRentId());
			//pstmt.setString(7, bookDTO.getRentDate());
			//pstmt.setString(8, bookDTO.getReturnDate());
			
			pstmt.executeUpdate();
		}catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(pstmt != null)pstmt.close();
				if(conn != null)conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}

	
	public void bookDelete(String isbn) {
		Connection conn = getConnection();//호출
		PreparedStatement pstmt = null;
		String sql = "delete from book where isbn="+isbn;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.executeUpdate();
		}catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(pstmt != null)pstmt.close();
				if(conn != null)conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}

	public void bookUpdate(BookDTO bookDTO) {
		Connection conn = getConnection();//호출
		PreparedStatement pstmt = null;
		String sql = "update book set book=?"
				+ ",isbn=?"
				+ ",genre=?"
				+ ",publisher=?"
				+ ",rentid=?"
				+ ",rentdate=?"
				+ ",returndate=? where book=?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,bookDTO.getBook());
			pstmt.setDouble(2, bookDTO.getIsbn());
			pstmt.setString(3,bookDTO.getGenre());
			pstmt.setString(4,bookDTO.getPublisher());
			pstmt.setString(5,bookDTO.getRentId());
			pstmt.setString(6,bookDTO.getRentDate());
			pstmt.setString(7,bookDTO.getReturnDate());
			pstmt.setString(8, bookDTO.getBook());
			pstmt.executeUpdate();
		}catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(pstmt != null)pstmt.close();
				if(conn != null)conn.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}

	}

	
	public void bookRent(BookDTO bookDTO) {
		Connection conn = getConnection();//호출
		PreparedStatement pstmt = null;
		String sql = "update book set book=?"
				+ ",isbn=?"
				+ ",genre=?"
				+ ",publisher=?"
				+ ",rentid=?"
				+ ",rentdate=?"
				+ ",returndate=? where seq=?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,bookDTO.getBook());
			pstmt.setDouble(2, bookDTO.getIsbn());
			pstmt.setString(3,bookDTO.getGenre());
			pstmt.setString(4,bookDTO.getPublisher());
			pstmt.setString(5,bookDTO.getRentId());
			pstmt.setString(6,bookDTO.getRentDate());
			pstmt.setString(7,bookDTO.getReturnDate());
			pstmt.setInt(8,bookDTO.getSeq());
			pstmt.executeUpdate();
		}catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(pstmt != null)pstmt.close();
				if(conn != null)conn.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
	}

	
	public void bookReturn(BookDTO bookDTO) {
		Connection conn = getConnection();//호출
		PreparedStatement pstmt = null;
		String sql = "update book set book=?"
				+ ",isbn=?"
				+ ",genre=?"
				+ ",publisher=?"
				+ ",rentid=?"
				+ ",rentdate=?"
				+ ",returndate=? where book=?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,bookDTO.getBook());
			pstmt.setDouble(2, bookDTO.getIsbn());
			pstmt.setString(3,bookDTO.getGenre());
			pstmt.setString(4,bookDTO.getPublisher());
			pstmt.setString(5,bookDTO.getRentId());
			pstmt.setString(6,bookDTO.getRentDate());
			pstmt.setString(7,bookDTO.getReturnDate());
			pstmt.setString(8,bookDTO.getBook());
			pstmt.executeUpdate();
		}catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(pstmt != null)pstmt.close();
				if(conn != null)conn.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}


	public int getSeq() {
		int seq=0;
		Connection conn = getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select seq_book.nextval from dual";
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			rs.next();
			seq = rs.getInt(1);
		}catch (SQLException e) {
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
	
	public int findSeq(BookDTO bookDTO) {
		int seq =0;
		Connection conn = getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select seq from book where book like '%"+bookDTO.getBook()+"%' and rentid is null and rownum=1";
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			rs.next();
			seq = rs.getInt(1);
			System.out.println(seq);
			pstmt.executeUpdate();
		}catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs != null)rs.close();
				if(pstmt != null)pstmt.close();
				if(conn != null)conn.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return seq;
	}
	
	public void bookRentlist(Admin ad) {
		Connection conn = getConnection();
		PreparedStatement pstmt = null;
		rs =null;
		String sql = "select * from book";
			try {
				pstmt = conn.prepareStatement(sql);
				rs = pstmt.executeQuery();
					while(rs.next()) {
						ad.getModelAll().addRow(new Object[] {rs.getString("BOOK"),rs.getDouble("ISBN"),rs.getString("GENRE"),
								rs.getString("PUBLISHER"),rs.getString("RENTID"),rs.getString("RENTDATE"),
								rs.getString("RETURNDATE")});
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

	public void bookOverdue(Admin ad) {
		Connection conn = getConnection();
		PreparedStatement pstmt = null;
		rs =null;
		String sql = "select * from book where to_date(returndate,'YYMMDD') < sysdate-1";
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
				while(rs.next()) {
					ad.getModelToday().addRow(new Object[] {rs.getString("BOOK"),rs.getDouble("ISBN"),rs.getString("GENRE"),
							rs.getString("PUBLISHER"),rs.getString("RENTID"),rs.getString("RENTDATE"),
							rs.getString("RETURNDATE")});
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

	public void bookToday(Admin ad) {
		Connection conn = getConnection();
		PreparedStatement pstmt = null;
		rs =null;
		
		String sql = "select * from book where returndate = to_char(sysdate,'YYMMDD')";
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
				while(rs.next()) {
					ad.getModelToday().addRow(new Object[] {rs.getString("BOOK"),rs.getDouble("ISBN"),rs.getString("GENRE"),
							rs.getString("PUBLISHER"),rs.getString("RENTID"),rs.getString("RENTDATE"),
							rs.getString("RETURNDATE")});
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


}
