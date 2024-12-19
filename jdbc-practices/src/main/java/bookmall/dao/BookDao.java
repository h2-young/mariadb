package bookmall.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import bookmall.vo.BookVo;

public class BookDao {
	
	public boolean insert(BookVo vo) {
		
		boolean result = false;
	    Connection conn = null;
	    PreparedStatement pstmt =  null;
	      
	    try {
	    	conn = getConnection();
	        // no title price 외래키
	        // 3. Statement 준비하기
	        String sql = "insert into book values(null, ?, ?, ?)";
	        pstmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
	         
	        // 4. Parameter binding
	        pstmt.setString(1, vo.getTitle());
	        pstmt.setInt(2, vo.getPrice());
	        pstmt.setInt(3, vo.getCategoryNo());
	         
	        // 5. SQL 실행
	        int count = pstmt.executeUpdate();
	         
	        ResultSet rs = pstmt.getGeneratedKeys();
	        if (rs.next()) {
	        	vo.setNo(rs.getInt(1));
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

	public boolean deleteByNo(int no) {
		boolean result = false;
	      Connection conn = null;
	      PreparedStatement pstmt =  null;
	      
	      try {
	         
	         conn = getConnection();
	         
	         // 3. Statement 준비하기
	         String sql = "delete from book where no = ?";
	         pstmt = conn.prepareStatement(sql);
	         
	         // 4. Parameter binding
	         pstmt.setInt(1, no);
	         
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
}
