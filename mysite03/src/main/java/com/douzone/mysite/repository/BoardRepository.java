package com.douzone.mysite.repository;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.mysite.vo.BoardVo;

@Repository
public class BoardRepository {

	@Autowired
	private DataSource dataSource;
	@Autowired
	private SqlSession sqlSession;
	
	public BoardVo findByNo(Long no) {
	
		return sqlSession.selectOne("board.findByNo", no);
		
	}
	
	public List<BoardVo> findByTitle(String keyword, Long page) {
		
		Map<String, Object> map = new HashMap<>();
		map.put("keyword", keyword);
		map.put("page", page);
		return sqlSession.selectList("board.findByTitle", map);
	
	}
	
	public Long findByTitleQty(String keyword) {
		
		return sqlSession.selectOne("board.findByTitleQty", keyword);
		
	}
	
	public boolean insert(BoardVo vo) {
		
		int count = sqlSession.insert("board.insert",vo);
		return count ==1;
	
	}
	
	public boolean insertReply(BoardVo vo) {
		
		int count = sqlSession.insert("board.insertReply", vo);
		return count ==1;
		
	}
	
	public boolean update(BoardVo vo) {
		
		int count = sqlSession.update("board.update",vo);
		return count ==1;
		
	}
	
	public boolean updateHit(Long no) {
		
		int count = sqlSession.update("board.updateHit",no);
		return count ==1;
		
	}
	
	public boolean delete(BoardVo vo) {

		int count = sqlSession.delete("board.delete",vo);
		return count ==1;
	
	}

	public boolean updateNo(BoardVo vo) {
		int count = sqlSession.update("board.updateNo", vo);
		return count==1;
	
	}
	
}
