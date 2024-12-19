package bookmall.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bookmall.vo.CartVo;
import bookmall.vo.UserVo;

public class UserDao {

   public Boolean insert(UserVo vo) {
      
      boolean result = false;
      Connection conn = null;
      PreparedStatement pstmt =  null;
      
      try {
         conn = getConnection();
         
         // 3. Statement 준비하기
         String sql = "insert into users values(null, ?, ?, ?, ?)";
         pstmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
         
         // 4. Parameter binding
         pstmt.setString(1, vo.getName());
         pstmt.setString(2, vo.getPhone());
         pstmt.setString(3, vo.getEmail());
         pstmt.setString(4, vo.getPassword());
         
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
   
   public List<UserVo> findAll() {
      
      List<UserVo> result = new ArrayList<>();
      Connection conn = null;
      PreparedStatement pstmt =  null;
      ResultSet rs = null;
      
      try {
         conn = getConnection();
         
         // 3. Statement 준비하기
         String sql = "select id, name, phone, email, password from users;";
         pstmt = conn.prepareStatement(sql);
         
         rs = pstmt.executeQuery();
         while(rs.next()) {
            Integer id = rs.getInt(1);
            String name = rs.getString(2);
            String phone = rs.getString(3);
            String email = rs.getString(4);
            String password = rs.getString(5);
            
            UserVo vo = new UserVo(name, email, password, phone);
            vo.setNo(id);
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

   public boolean deleteByNo(Integer no) {
      boolean result = false;
      Connection conn = null;
      PreparedStatement pstmt =  null;
      
      try {
         
         conn = getConnection();
         
         // 3. Statement 준비하기
         String sql = "delete from users where id = ?";
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
