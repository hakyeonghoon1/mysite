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

public class FindByTitleAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/** 안씀. 혹시 몰라서 안 지움
		 * 
		 * 
		 * 
		String keyword = request.getParameter("kwd");
		
		Long page = 1L;

		if(page == 1 || page==null) {
			page=0L;
		} else {
			page = (page-1)*10;
		}
		
		List<BoardListVo> list = new BoardDao().findByTitle(keyword, page);
		Long totalQty = new BoardDao().findByTitleQty(keyword);

		double totalPage =Math.ceil((double)totalQty /10);
		

		request.setAttribute("totalPage", totalPage);
		request.setAttribute("list", list);
		MvcUtil.forward("board/list", request, response);
		 */
	}

}
