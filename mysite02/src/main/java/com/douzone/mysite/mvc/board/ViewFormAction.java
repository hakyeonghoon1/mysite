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
		Long page = Long.parseLong(request.getParameter("page"));
		Cookie[] cookies = request.getCookies();
		int visitor = 0;

		if(cookies != null && cookies.length>0) {
			for(Cookie cookie :cookies) {

				if("visit".equals(cookie.getName())) {
					visitor=1;
					if(cookie.getValue().contains(no.toString())) {
						
					} else {
						System.out.println(cookie.getValue());
						cookie.setValue(cookie.getValue()+"-"+no.toString());
						response.addCookie(cookie);
						new BoardDao().updateHit(no);
						//System.out.println(cookie.getValue());
					}
				}
			}
		}
		System.out.println(visitor);
		if(visitor==0) {
			new BoardDao().updateHit(no);
			// 쿠키 쓰기
			Cookie cookie = new Cookie("visit", no.toString());
			cookie.setPath(request.getContextPath());
			cookie.setMaxAge(24*60*60);//1day
			response.addCookie(cookie);
		} 
		
		
		BoardVo vo = new BoardDao().findByNo(no);
		
		request.setAttribute("vo", vo);
		request.setAttribute("page", page);
		MvcUtil.forward("board/view", request, response);
	}

}
