package com.douzone.mysite.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.douzone.mysite.vo.BoardListVo;
import com.douzone.mysite.vo.BoardVo;

public class BoardDao {

	public BoardVo findByNo(Long no) {
		
		BoardVo result = null;
		
		PreparedStatement pstmt = null;
		Connection conn = null;
		ResultSet rs = null;
		try {
			
			conn = getConnection();
			
			//3.SQL문 준비
			String sql = "select no, title, contents, hit, reg_date, group_no, order_no, depth, user_no from board where no = ?";
			pstmt = conn.prepareStatement(sql);
			
			//4.바인딩(binding)
			pstmt.setLong(1, no);
			//5.SQL 실행			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				//Long no = rs.getLong(1);
				String title = rs.getString(2);
				String contents = rs.getString(3);
				Long hit = rs.getLong(4);
				String regDate = rs.getString(5);
				Long groupNo = rs.getLong(6);
				Long orderNo = rs.getLong(7);
				Long depth = rs.getLong(8);
				Long userNo = rs.getLong(9);
							
				BoardVo vo = new BoardVo();
				vo.setNo(no);
				vo.setTitle(title);
				vo.setContents(contents);
				vo.setHit(hit);
				vo.setRegDate(regDate);
				vo.setGroupNo(groupNo);
				vo.setOrderNo(orderNo);
				vo.setDepth(depth);
				vo.setUserNo(userNo);
				
				result = vo;
	
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
		
		return result;
	}
	
	public List<BoardListVo> findByTitle(String keyword, Long page) {
		
		List<BoardListVo> result = new ArrayList<>();
		
		PreparedStatement pstmt = null;
		Connection conn = null;
		ResultSet rs = null;
		try {
			
			conn = getConnection();
			
			//3.SQL문 준비
			String sql = "select z.no, z.title, z.hit, z.reg_date, z.group_no, z.order_no, z.depth, z.user_no, z.name, z.ROWNUM "
					+ "from ( "
					+ "	select @ROWNUM := @ROWNUM + 1 AS ROWNUM, x.* from "
					+ "			(select a.no, a.title, a.hit, a.reg_date, a.group_no, a.order_no, a.depth, a.user_no, b.name "
					+ "				from board a, user b  "
					+ "				where a.user_no = b.no "
					+ " 			and a.title like concat('%', ? ,'%') "			
					+ "				order by a.group_no asc, a.order_no desc "
					+ "				limit 10000000000000000 "
					+ "			) x, (SELECT @ROWNUM := 0 ) y "
					+ ") z "
					+ "order by z.ROWNUM desc "
					+ "limit ?, 10 ";
			pstmt = conn.prepareStatement(sql);
			
			//4.바인딩(binding)
			pstmt.setString(1, keyword);
			pstmt.setLong(2, page);
			
			//5.SQL 실행			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				Long no = rs.getLong(1);
				String title = rs.getString(2);
				Long hit = rs.getLong(3);
				String regDate = rs.getString(4);
				Long groupNo = rs.getLong(5);
				Long orderNo = rs.getLong(6);
				Long depth = rs.getLong(7);
				Long userNo = rs.getLong(8);
				String userName = rs.getString(9);
				Long rownum = rs.getLong(10);
							
				BoardListVo vo = new BoardListVo();
				vo.setNo(no);
				vo.setTitle(title);
				vo.setHit(hit);
				vo.setRegDate(regDate);
				vo.setGroupNo(groupNo);
				vo.setOrderNo(orderNo);
				vo.setDepth(depth);
				vo.setUserNo(userNo);
				vo.setUserName(userName);
				vo.setRownum(rownum);
				
				result.add(vo);
	
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
		
		return result;
	}
	
	public Long findByTitleQty(String keyword) {
		
		Long result = null;
		
		PreparedStatement pstmt = null;
		Connection conn = null;
		ResultSet rs = null;
		try {
			
			conn = getConnection();
			
			//3.SQL문 준비
			String sql = "select count(*) from board where title like concat('%',?,'%') ";
			pstmt = conn.prepareStatement(sql);
			
			//4.바인딩(binding)
			pstmt.setString(1, keyword);
			//5.SQL 실행			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
	
				result = rs.getLong(1);

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
		
		return result;
	}
	
	public Long findByQty() {
		
		Long result = null;
		
		PreparedStatement pstmt = null;
		Connection conn = null;
		ResultSet rs = null;
		try {
			
			conn = getConnection();
			
			//3.SQL문 준비
			String sql = "select count(*) from board";
			pstmt = conn.prepareStatement(sql);
			
			//4.바인딩(binding)

			//5.SQL 실행			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
	
				result = rs.getLong(1);

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
		
		return result;
	}
	
	public List<BoardListVo> findBoardLsit(Long page) {
		
		List<BoardListVo> result = new ArrayList<>();
		
		PreparedStatement pstmt = null;
		Connection conn = null;
		ResultSet rs = null;
		try {
			
			conn = getConnection();
			
			//3.SQL문 준비
			String sql = "select z.no, z.title, z.hit, z.reg_date, z.group_no, z.order_no, z.depth, z.user_no, z.name, z.ROWNUM "
					+ "from ( "
					+ "	select @ROWNUM := @ROWNUM + 1 AS ROWNUM, x.* from "
					+ "			(select a.no, a.title, a.hit, a.reg_date, a.group_no, a.order_no, a.depth, a.user_no, b.name "
					+ "				from board a, user b  "
					+ "				where a.user_no = b.no "
					+ "				order by a.group_no asc, a.order_no desc "
					+ "				limit 10000000000000000 "
					+ "			) x, (SELECT @ROWNUM := 0 ) y "
					+ ") z "
					+ "order by z.ROWNUM desc "
					+ "limit ?, 10 ";
			pstmt = conn.prepareStatement(sql);
			
			//4.바인딩(binding)
			pstmt.setLong(1, page);
			//5.SQL 실행			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				Long no = rs.getLong(1);
				String title = rs.getString(2);
				Long hit = rs.getLong(3);
				String regDate = rs.getString(4);
				Long groupNo = rs.getLong(5);
				Long orderNo = rs.getLong(6);
				Long depth = rs.getLong(7);
				Long userNo = rs.getLong(8);
				String userName = rs.getString(9);
				Long rownum = rs.getLong(10);
							
				BoardListVo vo = new BoardListVo();
				vo.setNo(no);
				vo.setTitle(title);
				vo.setHit(hit);
				vo.setRegDate(regDate);
				vo.setGroupNo(groupNo);
				vo.setOrderNo(orderNo);
				vo.setDepth(depth);
				vo.setUserNo(userNo);
				vo.setUserName(userName);
				vo.setRownum(rownum);
				
				result.add(vo);
	
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
		
		return result;
	}
	
	public boolean insert(BoardVo vo) {
		
		boolean result = false;
		PreparedStatement pstmt = null;
		Connection conn = null;
		
		try {
			
			conn = getConnection();
			
			//3.SQL문 준비
			String sql = "insert into board(no, title, contents, hit, reg_date, group_no, order_no, depth, user_no) "
					+ 	 "select null , ?, ?, 0,now(), ifnull(max(group_no)+1,0), 0, 0, ? "
					+ 	 "from board";
			pstmt = conn.prepareStatement(sql);
			
			//4.바인딩(binding)
			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContents());
			pstmt.setLong(3, vo.getUserNo());

			
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
	
	public boolean insertReply(BoardVo vo) {
		
		boolean result = false;
		PreparedStatement pstmt = null;
		Connection conn = null;
		
		try {
			
			conn = getConnection();
			
			//3.SQL문 준비
			String sql = "insert into board(no, title, contents, hit, reg_date, group_no, order_no, depth, user_no) "
					+ 	 "values( null , ?, ?, 0,now(), ?, ?, ?, ?) ";

			
			pstmt = conn.prepareStatement(sql);
			
			//4.바인딩(binding)
			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContents());
			pstmt.setLong(3, vo.getGroupNo());
			pstmt.setLong(4, vo.getOrderNo()+1);
			pstmt.setLong(5, vo.getDepth()+1);
			pstmt.setLong(6, vo.getUserNo());
			

			
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
	
	public boolean update(BoardVo vo) {
		boolean result = false;
		PreparedStatement pstmt = null;
		Connection conn = null;
		
		try {
			
			conn = getConnection();
			
			//3.SQL문 준비
			String sql = "update board "
					+ 	 "set title = ?, "
					+ 	 "	contents = ? "
					+ 	 "where no = ?";
			pstmt = conn.prepareStatement(sql);
			
			//4.바인딩(binding)
			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContents());
			pstmt.setLong(3, vo.getNo());

			
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
	
	public boolean updateHit(Long no) {
		boolean result = false;
		PreparedStatement pstmt = null;
		Connection conn = null;
		
		try {
			
			conn = getConnection();
			
			//3.SQL문 준비
			String sql = "update board "
					+ 	 "set hit = hit+1 "
					+ 	 "where no = ?";
			pstmt = conn.prepareStatement(sql);
			
			//4.바인딩(binding)
			pstmt.setLong(1, no);

			
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
	
	public boolean delete(BoardVo vo) {

		boolean result = false;
		PreparedStatement pstmt = null;
		Connection conn = null;
		
		try {
			
			conn = getConnection();
			
			//3.SQL문 준비
			String sql = "delete from board where no= ?";
			pstmt = conn.prepareStatement(sql);
			
			//4.바인딩(binding)
			pstmt.setLong(1, vo.getNo());
			
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

	public boolean updateNo(BoardVo vo) {
		boolean result = false;
		PreparedStatement pstmt = null;
		Connection conn = null;
		
		try {
			
			conn = getConnection();
			
			//3.SQL문 준비
			String sql = "update board "
					+ 	 "set order_no = order_no +1 "
					+ 	 "where group_no = ? "
					+	 "and order_no >= ? +1;";
			pstmt = conn.prepareStatement(sql);
			
			//4.바인딩(binding)
			pstmt.setLong(1, vo.getGroupNo());
			pstmt.setLong(2, vo.getOrderNo());
			
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
