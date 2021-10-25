package com.douzone.mysite.repository;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.mysite.exception.UserRepositoryException;
import com.douzone.mysite.vo.UserVo;

@Repository
public class UserRepository {
	
	@Autowired
	private DataSource datasource;
	
	@Autowired
	private SqlSession sqlSession;
		
	public UserVo findByEmailAndPassword(String email, String password) throws UserRepositoryException {
		
		Map<String, String> map = new HashMap<>();
		map.put("e", email);
		map.put("p", password);
		UserVo vo = sqlSession.selectOne("user.findByEmailAndPassword", map);

		return vo;
	}	
	public boolean insert(UserVo vo) throws UserRepositoryException {
		
		int count = sqlSession.insert("user.insert",vo);
		
		return count ==1;
	}
	

	public UserVo findByNo(Long rcvNo) throws UserRepositoryException {
			
		return sqlSession.selectOne("user.findByNo", rcvNo);
	}
	public boolean update(UserVo vo) {
		
		int count = sqlSession.update("user.update",vo);
	
		return count ==1;
	}
	
}
