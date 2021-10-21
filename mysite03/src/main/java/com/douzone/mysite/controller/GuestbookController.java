package com.douzone.mysite.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.douzone.mysite.service.GuestbookService;
import com.douzone.mysite.vo.GuestbookVo;

@Controller
@RequestMapping("/guestbook")
public class GuestbookController {

	@Autowired
	private GuestbookService guestbookService;
	
	@RequestMapping("")
	public String main(Model model) {
		
		List<GuestbookVo> list = guestbookService.getList();
		model.addAttribute("list", list);		
		return "guestbook/list";
	}
	
	@RequestMapping(value="/add", method=RequestMethod.POST)
	public String add(GuestbookVo vo) {
		guestbookService.add(vo);
		return "redirect:/guestbook";
	}
	
	@RequestMapping(value="/delete/{no}",method=RequestMethod.GET)
	public String delete(@PathVariable("no") Long no, Model model) {
		model.addAttribute("no", no);		
		return "guestbook/delete";
	}
	
	@RequestMapping(value="/delete",method=RequestMethod.POST)
	public String delete(Long no, String password, GuestbookVo vo ) {
		//System.out.println("no="+no+" : password="+password);
//		GuestbookVo vo = new GuestbookVo();
//		vo.setNo(no);
//		vo.setPassword(password);
		vo.setNo(no);
		vo.setPassword(password);

		guestbookService.delete(vo);

		return "redirect:/guestbook";
	}
}
