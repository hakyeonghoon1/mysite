package com.douzone.mysite.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Repository;

import com.douzone.mysite.exception.UserRepositoryException;
import com.douzone.mysite.vo.UserVo;

@Repository
public class UserRepository {
	
	public UserVo findByEmailAndPassword(String email, String password){
		UserVo vo = null;
		
		PreparedStatement pstmt = null;
		Connection conn = null;
		ResultSet rs = null;
		try {
			
			conn = getConnection();
			
			//3.SQL문 준비
			String sql = "select no, name from user where email=? and password=?";
			pstmt = conn.prepareStatement(sql);
			
			//4.바인딩(binding)
			pstmt.setString(1, email);
			pstmt.setString(2, password);
			
			//5.SQL 실행			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				Long no = rs.getLong(1);
				String name = rs.getString(2);
							
				vo = new UserVo();
				vo.setNo(no);
				vo.setName(name);
	
			}
	
		} catch (SQLException e) {
			System.out.println("error:"+e);
		} finally {
			try {
				//clean up
				if(rs != null) {
					rs.close();
				}
				if(pstmt != null) {
					pstmt.close();
				}
				if(conn != null) {
					conn.close();
				}		
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return vo;
	}	
	public boolean insert(UserVo vo) {
		
		boolean result = false;
		PreparedStatement pstmt = null;
		Connection conn = null;
		
		try {
			
			conn = getConnection();
			
			//3.SQL문 준비
			String sql = "insert into user values(null,?,?,?,?,now())";
			pstmt = conn.prepareStatement(sql);
			
			//4.바인딩(binding)
			pstmt.setString(1, vo.getName());
			pstmt.setString(2, vo.getEmail());
			pstmt.setString(3, vo.getPassword());
			pstmt.setString(4, vo.getGender());
			
			//5.SQL 실행			
			int count = pstmt.executeUpdate();
			
			result = count == 1;
		} catch (SQLException e) {
			System.out.println("error:"+e);
		} finally {
			try {
				//clean up
				if(pstmt != null) {
					pstmt.close();
				}
				if(conn != null) {
					conn.close();
				}		
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}
	
	private Connection getConnection() throws SQLException{
		Connection conn =null;
		
		try {
			//1.JDBC DRIVER 로딩
			Class.forName("org.mariadb.jdbc.Driver");
			
			//2.연결하기
			String  url ="jdbc:mysql://127.0.0.1:3306/webdb?charset=utf8";			
			conn = DriverManager.getConnection(url, "webdb", "webdb");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
			
		return conn;
	}
	public UserVo findByNo(Long rcvNo) throws UserRepositoryException {
		UserVo vo = null;
		
		PreparedStatement pstmt = null;
		Connection conn = null;
		ResultSet rs = null;
		try {
			
			conn = getConnection();
			
			//3.SQL문 준비
			String sql = "select no, name, email, gender from user where no =? ";
			pstmt = conn.prepareStatement(sql);
			
			//4.바인딩(binding)
			pstmt.setLong(1, rcvNo);

			
			//5.SQL 실행			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				Long no = rs.getLong(1);
				String name = rs.getString(2);
				String email = rs.getString(3);
				String gender = rs.getString(4);
							
				vo = new UserVo();
				vo.setNo(no);
				vo.setName(name);
				vo.setEmail(email);
				vo.setGender(gender);
	
			}
	
		} catch (SQLException e) {
			//System.out.println("error:"+e);
			throw new UserRepositoryException(e.toString());
		} finally {
			try {
				//clean up
				if(rs != null) {
					rs.close();
				}
				if(pstmt != null) {
					pstmt.close();
				}
				if(conn != null) {
					conn.close();
				}		
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return vo;
	}
	public boolean update(UserVo vo) {
		
		boolean result = false;
		PreparedStatement pstmt = null;
		Connection conn = null;
		
		try {
			
			conn = getConnection();
			
			if("".equals(vo.getPassword())) {
				//3.SQL문 준비
				String sql = "update user "
						+ "set name = ?, "
						+ "    gender = ? "
						+ "where no = ? ";
				pstmt = conn.prepareStatement(sql);
				
				//4.바인딩(binding)
				pstmt.setString(1, vo.getName());
				pstmt.setString(2, vo.getGender());
				pstmt.setLong(3, vo.getNo());
			} else {
				//3.SQL문 준비
				String sql = "update user "
						+ "set name = ?, "
						+ "password = ?, "
						+ "    gender = ? "
						+ "where no = ? ";
				pstmt = conn.prepareStatement(sql);
				
				//4.바인딩(binding)
				pstmt.setString(1, vo.getName());
				pstmt.setString(2, vo.getPassword());
				pstmt.setString(3, vo.getGender());
				pstmt.setLong(4, vo.getNo());
			}
			

			
			//5.SQL 실행			
			int count = pstmt.executeUpdate();
			
			result = count == 1;
		} catch (SQLException e) {
			System.out.println("error:"+e);
		} finally {
			try {
				//clean up
				if(pstmt != null) {
					pstmt.close();
				}
				if(conn != null) {
					conn.close();
				}		
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}
	
}
