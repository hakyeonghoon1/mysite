package com.douzone.mysite.mvc.board;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.douzone.mysite.dao.BoardDao;
import com.douzone.mysite.vo.BoardListVo;
import com.douzone.web.mvc.Action;
import com.douzone.web.util.MvcUtil;

public class ListAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String keyword = request.getParameter("kwd");

		if(keyword==null) {
			keyword="";
		}
		Long page = Long.parseLong(request.getParameter("page"));

		if(page == 1 || page==null) {
			page=0L;
		} else {
			page = (page-1)*10;
		}
		
		List<BoardListVo> list = new BoardDao().findByTitle(keyword, page);
		Long totalQty = new BoardDao().findByTitleQty(keyword);
		//System.out.println(totalQty);
		double totalPage =Math.ceil((double)totalQty /10);
		//System.out.println(totalPage);

		request.setAttribute("kwd", keyword);
		request.setAttribute("totalPage", totalPage);
		request.setAttribute("list", list);
		MvcUtil.forward("board/list", request, response);

	}

}
