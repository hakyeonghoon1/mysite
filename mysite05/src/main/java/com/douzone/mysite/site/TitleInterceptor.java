package com.douzone.mysite.site;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.douzone.mysite.service.AdminService;
import com.douzone.mysite.vo.SiteVo;

public class TitleInterceptor extends HandlerInterceptorAdapter{

	@Autowired
	ServletContext servletContext;
	
	@Autowired
	private AdminService adminService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		SiteVo vo = (SiteVo)servletContext.getAttribute("siteVo");
		
		if(vo == null) {
			vo = adminService.select();
			servletContext.setAttribute("siteVo", vo);
		}
		servletContext.setAttribute("siteVo", vo);
		
		return true;
	}

}
