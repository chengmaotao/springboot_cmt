package com.app.controller;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class FileUploadController {

	private static Logger logger = LoggerFactory.getLogger(FileUploadController.class);
	
	@RequestMapping("/fileupload")
	public String fileUpload(MultipartFile file, String comment) {

		logger.info(comment);
		
		logger.info("file size" + file.getSize());
		
		try {
			file.transferTo(new File("D:\\nr.jpg"));
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "好了";
	}
}
