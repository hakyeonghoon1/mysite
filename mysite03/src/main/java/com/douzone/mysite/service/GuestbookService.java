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

	public List<GuestbookVo> getRecentList(Long lastNo) {
		
		return guestbookRepository.getRecentList(lastNo);
	}

	public List<GuestbookVo> getSPAList(Long startNo) {
		
		return guestbookRepository.getSPAList(startNo);
	}

	public GuestbookVo getCheckDelete(String password, Long no) {
		
		return guestbookRepository.getCheckDelete(password, no);
	}
}
