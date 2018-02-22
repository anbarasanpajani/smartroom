package com.paypal.smroom.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

public class StorageService {

	Logger log = LoggerFactory.getLogger(this.getClass().getName());
	private final Path rootLocation = Paths.get("upload-dir");

	public void store(MultipartFile file){
		try {
            Files.copy(file.getInputStream(), this.rootLocation.resolve(file.getOriginalFilename()));
            System.out.println("File saved !!");
        } catch (Exception e) {
        	throw new RuntimeException("FAIL!");
        }
	}
	
	   public void init() {
	        try {
	            Files.createDirectory(rootLocation);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }

}
