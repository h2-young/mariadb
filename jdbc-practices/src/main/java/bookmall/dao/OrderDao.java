package bookmall.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bookmall.vo.CartVo;
import bookmall.vo.OrderBookVo;
import bookmall.vo.OrderVo;
import bookmall.vo.UserVo;

public class OrderDao {
	
	public Boolean insert(OrderVo vo) {
	      
	      boolean result = false;
	      Connection conn = null;
	      PreparedStatement pstmt =  null;
	      
	      try {
	         conn = getConnection();
	         
	         // 3. Statement 준비하기
	         String sql = "insert into orders values(null, ?, ?, ?, ?, ?)";
	         pstmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
	         
	         // 4. Parameter binding
	         pstmt.setString(1, vo.getStatus());
	         pstmt.setInt(2, vo.getPayment());
	         pstmt.setString(3, vo.getShipping());
	         pstmt.setInt(4, vo.getUserNo());
	         pstmt.setString(5, vo.getNumber());
	         
	         // 5. SQL 실행
	         int count = pstmt.executeUpdate();
	         
	         ResultSet rs = pstmt.getGeneratedKeys();
	         if (rs.next()) {
	             vo.setNo(rs.getLong(1));
	         }
	            
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

	public boolean insertBook(OrderBookVo vo) {
		boolean result = false;
	      Connection conn = null;
	      PreparedStatement pstmt =  null;
	      
	      try {
	         conn = getConnection();
	         
	         // 3. Statement 준비하기
	         String sql = "insert into orders_book values(?, ?, ?, ?)";
	         pstmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
	         
	         // 4. Parameter binding
	         pstmt.setInt(1, vo.getBookNo());
	         pstmt.setLong(2, vo.getOrderNo());
	         pstmt.setInt(3, vo.getQuantity());
	         pstmt.setInt(4, vo.getPrice());

	         
	         // 5. SQL 실행
	         int count = pstmt.executeUpdate();
	            
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

	public List<OrderBookVo> findBooksByNoAndUserNo(Long no, int no2) {
		List<OrderBookVo> result = new ArrayList<>();
	      Connection conn = null;
	      PreparedStatement pstmt =  null;
	      ResultSet rs = null;
	      
	      try {
	         conn = getConnection();
	         
	         // 3. Statement 준비하기
	         String sql = "select a.book_no, a.orders_no, a.quantity, a.price, c.title from orders_book a join orders b on a.orders_no = b.no join book c on a.book_no = c.no where a.orders_no = " + no + " and b.users_id = " + no2 + ";";
	         
	         pstmt = conn.prepareStatement(sql);
	         
	         rs = pstmt.executeQuery();
	         while(rs.next()) {
	            Integer book_no = rs.getInt(1);
	            Long orders_no = rs.getLong(2);
	            Integer quantity = rs.getInt(3);
	            Integer price = rs.getInt(4);
	            String title = rs.getString(5);
	            
	            OrderBookVo vo = new OrderBookVo();
	            
	            vo.setBookNo(book_no);
	            vo.setOrderNo(orders_no);
	            vo.setQuantity(quantity);
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

	public boolean deleteBooksByNo(Long no) {
		boolean result = false;
	      Connection conn = null;
	      PreparedStatement pstmt =  null;
	      
	      try {
	         
	         conn = getConnection();
	         
	         // 3. Statement 준비하기
	         String sql = "delete from orders_book where orders_no = ?";
	         pstmt = conn.prepareStatement(sql);
	         
	         // 4. Parameter binding
	         pstmt.setLong(1, no);
	         
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

	public boolean deleteByNo(Long no) {
		boolean result = false;
	      Connection conn = null;
	      PreparedStatement pstmt =  null;
	      
	      try {
	         
	         conn = getConnection();
	         
	         // 3. Statement 준비하기
	         String sql = "delete from orders where no = ?";
	         pstmt = conn.prepareStatement(sql);
	         
	         // 4. Parameter binding
	         pstmt.setLong(1, no);
	         
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

	public OrderVo findByNoAndUserNo(long l, int no) {
		OrderVo result = null;
	      Connection conn = null;
	      PreparedStatement pstmt =  null;
	      ResultSet rs = null;
	      
	      try {
	         conn = getConnection();
	         
	         // 3. Statement 준비하기
	         String sql = "select no, status, payment, shipping, users_id, number from orders where no = " + l + " and users_id = " + no + ";";
	         pstmt = conn.prepareStatement(sql);
	         
	         rs = pstmt.executeQuery();
	         if(rs.next()) {
	            Long n = rs.getLong(1);
	            String status = rs.getString(2);
	            Integer payment = rs.getInt(3);
	            String shipping = rs.getString(4);
	            Integer users_id = rs.getInt(5);
	            String number = rs.getString(6);
	            
	            result = new OrderVo();
	            
	            result.setNo(n);
	            result.setStatus(status);
	            result.setPayment(payment);
	            result.setShipping(shipping);
	            result.setUserNo(users_id);
	            result.setNumber(number);
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

}
