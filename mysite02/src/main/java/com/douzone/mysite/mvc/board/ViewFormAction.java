package com.douzone.mysite.mvc.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.douzone.mysite.dao.BoardDao;
import com.douzone.mysite.vo.BoardVo;
import com.douzone.web.mvc.Action;
import com.douzone.web.util.MvcUtil;

public class ViewFormAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//System.out.println("viewform");
		
		Long no = Long.parseLong(request.getParameter("no"));
		
		Cookie[] cookies = request.getCookies();
		int count = 0;

		if(cookies != null && cookies.length>0) {
			for(Cookie cookie :cookies) {

				if(("HIT"+no).equals(cookie.getName())) {
					count=1;
				}
			}
		}

		if(count==0) {
			new BoardDao().updateHit(no);
			// 쿠키 쓰기
			Cookie cookie = new Cookie("HIT"+no, String.valueOf(1));
			cookie.setPath(request.getContextPath());
			cookie.setMaxAge(24*60*60);//1day
			response.addCookie(cookie);
		} 
		
		
		BoardVo vo = new BoardDao().findByNo(no);
		
		request.setAttribute("vo", vo);
		MvcUtil.forward("board/view", request, response);
	}

}
