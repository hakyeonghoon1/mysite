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

public class DeleteAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Access Control(보안, 인증체크)
		HttpSession session = request.getSession();
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		if(authUser == null) {
			MvcUtil.redirect(request.getContextPath(), request, response);
			return;
		}
		
		Long no = Long.parseLong(request.getParameter("no"));
		
		BoardVo vo = new BoardVo();
		vo.setNo(no);
		BoardVo boardVo = new BoardDao().findByNo(no);
		if( authUser.getNo() != boardVo.getUserNo()) {
			MvcUtil.redirect(request.getContextPath(), request, response);
			return;
		}

		new BoardDao().delete(vo);
		
		MvcUtil.redirect(request.getContextPath()+"/board?page=1", request, response);

	}

}
