package com.douzone.mysite.repository;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.mysite.vo.SiteVo;

@Repository
public class AdminRepository {

	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private SqlSession sqlSession;


	public void insert(SiteVo vo) {
		sqlSession.insert("admin.insert",vo);
		
	}
	
	public SiteVo select() {
		
		return sqlSession.selectOne("admin.select");
	}
	
}
