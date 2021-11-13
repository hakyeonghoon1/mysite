package com.douzone.mysite.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.mysite.vo.GuestbookVo;


@Repository
public class GuestbookRepository {
	
	@Autowired
	private DataSource datasource;
	
	@Autowired
	private SqlSession sqlSession;
	
	public List<GuestbookVo> findList() throws RuntimeException {
		
		return sqlSession.selectList("guestbook.findList");
		
	}
	
	public boolean insert(GuestbookVo vo) {
		
		int count = sqlSession.insert("guestbook.insert",vo);
		System.out.println(vo);
		return count ==1;
	}
	
	public boolean delete(GuestbookVo vo) {
		
		int count = sqlSession.delete("guestbook.delete", vo);
	
		return count ==1;
	}

	public List<GuestbookVo> getRecentList(Long lastNo) {
		
		return sqlSession.selectList("guestbook.getRecentList", lastNo);
	}

	public List<GuestbookVo> getSPAList(Long startNo) {
		Map<String,Long> map = new HashMap<>();
		map.put("startNo",startNo);
		return sqlSession.selectList("guestbook.getSPAList", map);
	}

	public GuestbookVo getCheckDelete(String password, Long no) {
		Map<String,Object> map = new HashMap<>();
		map.put("password", password);
		map.put("no", no);
		return sqlSession.selectOne("guestbook.getCheckDelete", map);
	}
	

}
