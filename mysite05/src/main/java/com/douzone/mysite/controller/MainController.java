package com.douzone.mysite.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.douzone.mysite.service.AdminService;
import com.douzone.mysite.vo.SiteVo;


@Controller
public class MainController {

	@RequestMapping({"","/main"})
	public String index(Model model) {

		return "main/index";
	}
	
	@ResponseBody
	@RequestMapping("/msg01")
	public String message01() {
		
		return "안녕";
	}
	
	@ResponseBody
	@RequestMapping("/msg02")
	public Object message02(/*HttpServletResponse res*/) throws Exception{
	
//		res.setContentType("application/json; charset=UTF-8");
//		res.getWriter().print("{\"message\":\"Hello World\"}");
		Map<String, Object> map = new HashMap<>();
		map.put("message", "hello world");
		
		return map;
	}
}
