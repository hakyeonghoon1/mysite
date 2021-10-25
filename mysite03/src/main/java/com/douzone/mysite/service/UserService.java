package com.douzone.mysite.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.mysite.repository.UserRepository;
import com.douzone.mysite.vo.UserVo;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	public void join(UserVo vo) {
		userRepository.insert(vo);	
	}

	public UserVo getUser(String email, String password) {
		UserVo vo = userRepository.findByEmailAndPassword(email, password);
		return vo;
	}
	
	public UserVo getUser(Long no) {
		UserVo vo = userRepository.findByNo(no);
		return vo;
	}
	
	public UserVo getUser(String email) {
		return userRepository.findByEmail(email);
		 
	}

	public void updateUser(UserVo userVo) {
		userRepository.update(userVo);
		
	}

}
