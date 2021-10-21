package com.douzone.mysite.controller;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.douzone.mysite.service.BoardService;
import com.douzone.mysite.vo.BoardVo;
import com.douzone.mysite.vo.UserVo;


@Controller
@RequestMapping("/board")
public class BoardController {

	@Autowired
	private BoardService boardService;
	
	@RequestMapping("")
	public String list(String kwd, Long page, Model model) {

		List<BoardVo> list = boardService.getList(kwd,page);
		Long totalQty = boardService.getTotalQty(kwd);
		double totalPage = Math.ceil((double)totalQty/10);
		model.addAttribute("list", list);
		model.addAttribute("totalPage", totalPage);
		model.addAttribute("kwd", kwd);
		return "board/list";
	}
	
	@RequestMapping(value="/write", method=RequestMethod.GET)
	public String write(HttpSession session) {
		// 접근제어(ACL)
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		if(authUser == null) {
			return "redirect:/";
		}
		
		return "board/write";
	}
	
	@RequestMapping(value="/write", method=RequestMethod.POST)
	public String write(HttpSession session, Long orderNo, Long groupNo, Long depth, String title, String content) {
		// 접근제어(ACL)
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		if(authUser == null) {
			return "redirect:/";
		}

		if(groupNo==null) {
			
			Long userNo = authUser.getNo();
			
			BoardVo vo = new BoardVo();
			vo.setTitle(title);
			vo.setContents(content);
			vo.setUserNo(userNo);
			
			boardService.add(vo);
			return "redirect:/board?page=1";
			
		} else if(groupNo != null){	
			
			Long userNo = authUser.getNo();
			
			BoardVo vo = new BoardVo();

			vo.setGroupNo(groupNo);
			vo.setOrderNo(orderNo);
			vo.setDepth(depth);
			vo.setTitle(title);
			vo.setContents(content);
			vo.setUserNo(userNo);
			
			//new BoardDao().updateNo(vo);
			boardService.updateNo(vo);
			//new BoardDao().insertReply(vo);
			boardService.insertReply(vo);

			return "redirect:/board?page=1";
		}

		return "redirect:/board?page=1";
	}
	
	@RequestMapping("/view/{no}/{page}")
	public String view(@PathVariable("no") Long no, @PathVariable("page") Long page,
			Model model, HttpServletRequest request, HttpServletResponse response) {
		
		Cookie[] cookies = request.getCookies();
		int count = 0;
		System.out.println(count);
		if(cookies != null && cookies.length>0) {
			for(Cookie cookie :cookies) {
				System.out.println(cookie.getName());
				if(("HIT"+no).equals(cookie.getName())) {
					count=1;
				}
			}
		}

		if(count==0) {
			boardService.updatehit(no);
			// 쿠키 쓰기
			Cookie cookie = new Cookie("HIT"+no, String.valueOf(1));
			cookie.setPath(request.getContextPath());
			cookie.setMaxAge(24*60*60);//1day
			response.addCookie(cookie);
		} 
		
		BoardVo vo = boardService.findByNo(no);
		
		model.addAttribute("vo", vo);
		model.addAttribute("page", page);
		return "board/view";
	}
	
	@RequestMapping(value="/update/{no}/{userNo}",method=RequestMethod.GET)
	public String update(@PathVariable("no") Long no, @PathVariable("userNo") Long userNo, HttpSession session, Model model) {
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		
		if(authUser.getNo() == userNo) {
			BoardVo vo = boardService.findByNo(no);
			model.addAttribute("vo", vo);
			return "board/modify";
		} else {
			return "redirect:/board?page=1";
		}
	}
	
	@RequestMapping(value="/update",method=RequestMethod.POST)
	public String update(HttpSession session, Model model, String title, String content,Long no ) {
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		
		if(authUser == null) {
			return "redirect:/board?page=1";
		}
		BoardVo vo = new BoardVo();
		vo.setNo(no);
		vo.setTitle(title);
		vo.setContents(content);
		
		boardService.update(vo);
		
		return "redirect:/board/view/"+no;
	}
	
	@RequestMapping("/delete/{no}")
	public String delete(@PathVariable("no") Long no, HttpSession session) {
	
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		if(authUser == null) {
			
			return "redirect:/board?page=1";
		}
		BoardVo boardVo =boardService.findByNo(no);
		if(boardVo.getUserNo() == authUser.getNo()) {
			BoardVo vo = new BoardVo();
			vo.setNo(no);
			boardService.delete(vo);
		} else {
			return "redirect:/board?page=1";
		}
		
		return "redirect:/board?page=1";
	}
	
	@RequestMapping("/reply/{groupNo}/{orderNo}/{depth}/{no}")
	public String reply(@PathVariable("groupNo") Long groupNo, @PathVariable("orderNo") Long orderNo,
						@PathVariable("depth") Long depth, @PathVariable("no") Long no, Model model, HttpSession session) {
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		BoardVo boardVo =boardService.findByNo(no);
		if(boardVo.getUserNo() == authUser.getNo()) {
			model.addAttribute("groupNo", groupNo);
			model.addAttribute("orderNo", orderNo);
			model.addAttribute("depth", depth);

			return "board/write";
		} else {
			return "redirect:/board?page=1";
		}
		
	}
}
