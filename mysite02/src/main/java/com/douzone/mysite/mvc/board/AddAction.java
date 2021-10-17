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

public class AddAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		if(authUser == null) {
			MvcUtil.redirect(request.getContextPath(), request, response);
			return;
		}
		
		if("".equals(request.getParameter("groupNo"))) {

			String title = request.getParameter("title");
			String contents =request.getParameter("content");
			
			Long userNo = authUser.getNo();
			
			BoardVo vo = new BoardVo();
			vo.setTitle(title);
			vo.setContents(contents);
			vo.setUserNo(userNo);
			
			new BoardDao().insert(vo);
			MvcUtil.redirect(request.getContextPath()+"/board?page=1", request, response);
			
		} else if(request.getParameter("groupNo") != null){	
			
			Long groupNo = Long.parseLong(request.getParameter("groupNo"));
			Long orderNo = Long.parseLong(request.getParameter("orderNo"));
			Long depth = Long.parseLong(request.getParameter("depth"));
			String title = request.getParameter("title");
			String contents =request.getParameter("content");
			Long userNo = authUser.getNo();
			
			BoardVo vo = new BoardVo();

			vo.setGroupNo(groupNo);
			vo.setOrderNo(orderNo);
			vo.setDepth(depth);
			vo.setTitle(title);
			vo.setContents(contents);
			vo.setUserNo(userNo);
			
			new BoardDao().updateNo(vo);
			
			new BoardDao().insertReply(vo);
			
			MvcUtil.redirect(request.getContextPath()+"/board?page=1", request, response);
		}
		

	}

}
