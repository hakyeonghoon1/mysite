package com.douzone.mysite.mvc.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.douzone.mysite.dao.BoardDao;
import com.douzone.mysite.vo.BoardVo;
import com.douzone.mysite.vo.UserVo;
import com.douzone.web.mvc.Action;
import com.douzone.web.util.MvcUtil;

public class UpdateFormAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Long userNo = Long.parseLong(request.getParameter("userNo"));
		Long no = Long.parseLong(request.getParameter("no"));
		HttpSession session = request.getSession();
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		if(authUser.getNo() == userNo) {
			BoardVo vo = new BoardDao().findByNo(no);
			
			request.setAttribute("vo", vo);
			MvcUtil.forward("board/modify", request, response);
			
		} else {
			MvcUtil.redirect(request.getContextPath(), request, response);
			return;
		}
		
		

	}

}
