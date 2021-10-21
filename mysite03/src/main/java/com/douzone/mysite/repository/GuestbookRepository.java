package com.douzone.mysite.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.mysite.exception.GuestbookRepositoryException;
import com.douzone.mysite.vo.GuestbookVo;


@Repository
public class GuestbookRepository {
	
	@Autowired
	private DataSource datasource;
	
	@Autowired
	private SqlSession sqlSession;
	
	public List<GuestbookVo> findList() throws RuntimeException {
		
		return sqlSession.selectList("guestbook.findAll");
		
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
	

}
