package bookmall.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bookmall.vo.CartVo;

public class CartDao {
	
	public Boolean insert(CartVo vo) {
	      
	      boolean result = false;
	      Connection conn = null;
	      PreparedStatement pstmt =  null;
	      
	      try {
	         conn = getConnection();
	         
	         // 3. Statement 준비하기
	         String sql = "insert into cart values(?, ?, ?)";
	         pstmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
	         
	         // 4. Parameter binding
	         pstmt.setInt(1, vo.getQuantity());
	         pstmt.setInt(2, vo.getUserNo());
	         pstmt.setInt(3, vo.getBookNo());
	         
	         System.out.println(vo.getQuantity());
	         System.out.println(vo.getUserNo());
	         System.out.println(vo.getBookNo());
	         
	         // 5. SQL 실행
	         int count = pstmt.executeUpdate();
	         
//	         ResultSet rs = pstmt.getGeneratedKeys();
//	         if (rs.next()) {
//	        	 vo.setUserNo(rs.getInt(1));
//	        	 vo.setBookNo(rs.getInt(1));
//	         }
	            
	         result = count == 1;
	         
	      } catch (SQLException e) {
	         System.out.println("error: " + e);
	      } finally {
	         try {
	            if (pstmt != null) {
	               pstmt.close();
	            }
	            if (conn != null) {
	               conn.close();
	            }
	         } catch (SQLException e) {
	            e.printStackTrace();
	         }
	      }
	      
	      return result;
	   }
	   
	   public List<CartVo> findByUserNo(int no) {
	      
	      List<CartVo> result = new ArrayList<>();
	      Connection conn = null;
	      PreparedStatement pstmt =  null;
	      ResultSet rs = null;
	      
	      try {
	         conn = getConnection();
	         
	         // 3. Statement 준비하기
	         String sql = "select quantity, users_id, book_no, price, title from cart a join book b on a.book_no = b.no where a.users_id = " + no + ";";
	         pstmt = conn.prepareStatement(sql);
	         
	         rs = pstmt.executeQuery();
	         while(rs.next()) {
	            Integer quantity = rs.getInt(1);
	            Integer users_id = rs.getInt(2);
	            Integer book_no = rs.getInt(3);
	            Integer price = rs.getInt(4);
	            String title = rs.getString(5);
	            
	            CartVo vo = new CartVo();
	            vo.setQuantity(quantity);
	            vo.setUserNo(users_id);
	            vo.setBookNo(book_no);
	            vo.setPrice(price);
	            vo.setBookTitle(title);
	            result.add(vo);
	         }
	         
	      } catch (SQLException e) {
	         System.out.println("error: " + e);
	      } finally {
	         try {
	            if (pstmt != null) {
	               pstmt.close();
	            }
	            if (conn != null) {
	               conn.close();
	            }
	         } catch (SQLException e) {
	            e.printStackTrace();
	         }
	      }
	      
	      return result;
	   }
	   
	   private Connection getConnection() throws SQLException {
	      Connection conn = null;
	      try {
	         // 1. JDBC Driver 로딩
	         Class.forName("org.mariadb.jdbc.Driver");
	         
	         // 2. 연결하기
	         String url = "jdbc:mariadb://192.168.0.22:3306/BOOKMALL";
	         conn = DriverManager.getConnection(url, "bookmall", "bookmall");
	      }
	      catch (ClassNotFoundException e) {
	         System.out.println("드라이버 로딩 실패: " + e);
	      }
	      return conn;
	   }

	public boolean deleteByUserNoAndBookNo(int userNo, int bookNo) {
	      boolean result = false;
	      Connection conn = null;
	      PreparedStatement pstmt =  null;
	      
	      try {
	         
	         conn = getConnection();
	         
	         // 3. Statement 준비하기
	         String sql = "delete from cart where users_id = ? and book_no = ?";
	         pstmt = conn.prepareStatement(sql);
	         
	         // 4. Parameter binding
	         pstmt.setInt(1, userNo);
	         pstmt.setInt(2, bookNo);
	         
	         // 5. SQL 실행
	         int count = pstmt.executeUpdate();
	         result = count == 1;
	         
	      } catch (SQLException e) {
	         System.out.println("드라이버 로딩 실패: " + e);
	      } finally {
	         try {
	            if (pstmt != null) {
	               pstmt.close();
	            }
	            if (conn != null) {
	               conn.close();
	            }
	         } catch (SQLException e) {
	            e.printStackTrace();
	         }
	      }
	      
	      return result;
	}

}
