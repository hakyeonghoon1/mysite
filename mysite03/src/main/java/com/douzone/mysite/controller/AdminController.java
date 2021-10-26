package com.douzone.mysite.controller;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.douzone.mysite.security.Auth;
import com.douzone.mysite.service.AdminService;
import com.douzone.mysite.vo.SiteVo;

@Auth(role="ADMIN")
@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	ServletContext servletContext;
	
	@Autowired
	private AdminService adminService;
	
	//@Auth(role="ADMIN")
	@RequestMapping("")
	public String main(Model model) {
		
		SiteVo siteVo = adminService.select();
		servletContext.setAttribute("siteVo", siteVo);
		model.addAttribute("siteVo", siteVo);
		return "admin/main";
	}
	
	//@Auth(role="ADMIN")
	@RequestMapping("/update")
	public String update(String title, String welcomeMessage, String description, 
						@RequestParam(value="file1") MultipartFile multipartFile) {
		
		adminService.insert(title,welcomeMessage,description,multipartFile);
		return "redirect:/admin";
	}
	
	//@Auth(role="ADMIN")
	@RequestMapping("/guestbook")
	public String guestbook() {
		
		return "admin/guestbook";
	}
	
	//@Auth(role="ADMIN")
	@RequestMapping("/board")
	public String board() {
		
		return "admin/board";
	}
	
	//@Auth(role="ADMIN")
	@RequestMapping("/user")
	public String user() {
		
		return "admin/user";
	}
}