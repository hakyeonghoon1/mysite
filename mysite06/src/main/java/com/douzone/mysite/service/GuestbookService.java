package com.douzone.mysite.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.mysite.repository.GuestbookRepository;
import com.douzone.mysite.vo.GuestbookVo;

@Service
public class GuestbookService {

	@Autowired
	private GuestbookRepository guestbookRepository;

	public List<GuestbookVo> getList() {
		List<GuestbookVo> list = guestbookRepository.findList();
		
		return list;
	}

	public void add(GuestbookVo vo) {
		guestbookRepository.insert(vo);
		
	}

	public void delete(GuestbookVo vo) {
		guestbookRepository.delete(vo);
		
	}
}
