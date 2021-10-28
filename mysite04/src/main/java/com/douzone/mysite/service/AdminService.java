package com.douzone.mysite.service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.douzone.mysite.repository.AdminRepository;
import com.douzone.mysite.vo.SiteVo;

@Service
public class AdminService {
	
	@Autowired
	private AdminRepository adminRepository;
	
	private static String SAVE_PATH = "/gallery-images/";
	private static String URL_BASE = "/images";
	
	public void insert(String title, String welcomeMessage, String description, MultipartFile multipartFile) {
		String url =null;
		try {
			
			
			String originFilename = multipartFile.getOriginalFilename();
			String extName = originFilename.substring(originFilename.lastIndexOf(".")+1);
			String saveFilename = generateSaveFilename(extName);
			
			byte[] data = multipartFile.getBytes();
			OutputStream os = new FileOutputStream(SAVE_PATH+"/"+saveFilename);
			os.write(data);
			os.close();
			
			url = URL_BASE+"/"+saveFilename;
			
			if(multipartFile.isEmpty()) {
				url =null;
			}
			
			SiteVo vo = new SiteVo();
			vo.setTitle(title);
			vo.setWelcome(welcomeMessage);
			vo.setDescription(description);
			vo.setProfile(url);
			adminRepository.insert(vo);
			System.out.println(vo);
		} catch(IOException e) {
			throw new RuntimeException("file upload error : "+e);
		}

	}
	
	private String generateSaveFilename(String extName) {
		String filename ="";
		Calendar calendar = Calendar.getInstance();
		filename += calendar.get(Calendar.YEAR);
		filename += calendar.get(Calendar.MONTH);
		filename += calendar.get(Calendar.DATE);
		filename += calendar.get(Calendar.HOUR);
		filename += calendar.get(Calendar.MINUTE);
		filename += calendar.get(Calendar.SECOND);
		filename += calendar.get(Calendar.MILLISECOND);
		filename += ("."+extName);
		return filename;
	}
	
	public SiteVo select() {
		
		return adminRepository.select();
	}
	
}
