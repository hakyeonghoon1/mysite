package com.douzone.mysite.mvc.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.douzone.mysite.vo.UserVo;
import com.douzone.web.mvc.Action;
import com.douzone.web.util.MvcUtil;

public class WriteReplyFormAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Access Control(보안, 인증체크)
		HttpSession session = request.getSession();
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		if(authUser == null) {
			MvcUtil.redirect(request.getContextPath(), request, response);
			return;
		}
		//Long no = Long.parseLong(request.getParameter("no"));
		Long groupNo = Long.parseLong(request.getParameter("groupno"));
		Long orderNo = Long.parseLong(request.getParameter("orderno"));
		Long depth = Long.parseLong(request.getParameter("depth"));
		
		request.setAttribute("groupNo", groupNo);
		request.setAttribute("orderNo", orderNo);
		request.setAttribute("depth", depth);
		MvcUtil.forward("board/write", request, response);
		

	}

}
