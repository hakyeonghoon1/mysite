package com.douzone.mysite.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.douzone.mysite.service.GalleryService;
import com.douzone.mysite.vo.GalleryVo;

@Controller
@RequestMapping("/gallery")
public class GalleryController {

	@Autowired
	private GalleryService galleryService;
	
	@RequestMapping("")
	public String index(Model model) {
//	
//		List<GalleryVo> list  = galleryService.findAll();
//		
//		model.addAttribute("lists", list);
		return "gallery/index";
	}
	
	@RequestMapping("/upload")
	public String upload(String comments, @RequestParam(value="file") MultipartFile multipartfile) {
		
		galleryService.restore(multipartfile, comments);
		return "redirect:/gallery";
	}
	
	@RequestMapping("/delete/{no}")
	public String delete(@PathVariable("no") Long no) {
		
		galleryService.delete(no);
		return "redirect:/gallery";
	}
}
