package com.douzone.mysite.controller.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.douzone.mysite.dto.JsonResult;
import com.douzone.mysite.service.GuestbookService;
import com.douzone.mysite.vo.GuestbookVo;

@Controller("guestbookApiController")
@RequestMapping("/guestbook/api")
public class GuestbookController {

	@Autowired
	private GuestbookService guestbookService;
	
	@ResponseBody
	@RequestMapping("/list")
	public JsonResult guestbookList() {
		
		List<GuestbookVo> list = guestbookService.getList();
		
		return JsonResult.success(list);
	}
	
	@ResponseBody
	@RequestMapping("/pastList")
	public JsonResult guestbookPastList(Long startNo) {
		
		List<GuestbookVo> list = guestbookService.getSPAList(startNo);
		
		return JsonResult.success(list);
	}
	
	@ResponseBody
	@RequestMapping("/add")
	public JsonResult add(String name, String password, String content, Long lastNo) {
		
		GuestbookVo vo = new GuestbookVo();
		vo.setName(name);
		vo.setPassword(password);
		vo.setMessage(content);
		guestbookService.add(vo);
		List<GuestbookVo> list = guestbookService.getRecentList(lastNo);
		return JsonResult.success(list);
	}
	
	@ResponseBody
	@RequestMapping("delete")
	public Object delete(String password,Long no) {
		
		GuestbookVo vo= guestbookService.getCheckDelete(password,no);
		if(vo!=null) {
			guestbookService.delete(vo);
		}
		
		return JsonResult.success(vo);
	}
}
