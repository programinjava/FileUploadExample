package com.programinjava.learn.controller;

import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.programinjava.learn.service.StorageService;

@Controller
public class UploadController {
	
	@Autowired
	StorageService storageService;
	
//	CHANGE IT ACCORDING TO YOUR LOCATION
	private final String UPLOAD_FILE_LOCATION="C:\\programinjava_workspace";
	
	@PostMapping("/upload")
	public String singleFileUpload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {

		if (file.isEmpty()) {
			redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
			return "redirect:uploadStatus";
		}
		String UploadedFolderLocation = UPLOAD_FILE_LOCATION+"/";
		String fileName = null;
//			this is done to work on IE as well
		String pattern = Pattern.quote(System.getProperty("file.separator"));
		String[] str = file.getOriginalFilename().split(pattern);
		if (str.length > 0)
			fileName = str[str.length - 1];
		else
			fileName = str[0];
		if(!storageService.store(file, fileName,UPLOAD_FILE_LOCATION))
		{
			redirectAttributes.addFlashAttribute("message", "Error occurred while uploading the file");
			redirectAttributes.addFlashAttribute("status", "false");
			return "redirect:/uploadStatus";
		}
		redirectAttributes.addFlashAttribute("message", "You successfully uploaded '" + fileName + "'");
		redirectAttributes.addFlashAttribute("status", "true");
		return "redirect:/uploadStatus";
	}
	
	@GetMapping("/uploadStatus")
	public String uploadStatus(ModelMap m) {
		return "Homepage";
	}
	
	@GetMapping("/upload")  
	public String displayHomePageForAlarm() {
		return "Homepage";
	}

}
