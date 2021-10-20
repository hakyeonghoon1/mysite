package com.douzone.mysite.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.mysite.repository.BoardRepository;
import com.douzone.mysite.vo.BoardListVo;
import com.douzone.mysite.vo.BoardVo;

@Service
public class BoardService {

	@Autowired
	private BoardRepository boardRepository;

	public List<BoardListVo> getList(String kwd, Long page) {
		if(kwd ==null) {
			kwd="";
		}
		if(page == 1 || page ==null) {
			page=0L;
		} else {
			page = (page-1)*10;
		}
		return boardRepository.findByTitle(kwd, page);
		
	}

	public Long getTotalQty(String kwd) {
		if(kwd ==null) {
			kwd="";
		}
		return boardRepository.findByTitleQty(kwd);
	}

	public void add(BoardVo vo) {
		boardRepository.insert(vo);
		
	}

	public void updateNo(BoardVo vo) {
		boardRepository.updateNo(vo);
		
	}

	public void insertReply(BoardVo vo) {
		boardRepository.insertReply(vo);
		
	}

	public BoardVo findByNo(Long no) {
		
		return boardRepository.findByNo(no);
	}

	public void update(BoardVo vo) {
		boardRepository.update(vo);
		
	}

	public void delete(BoardVo vo) {
		boardRepository.delete(vo);
		
	}
}
