package com.shopme.admin;

import java.io.IOException;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.web.multipart.MultipartFile;

public class FileUploadUtil {
	
	public static void saveFile(String uploadDir,String fileName,MultipartFile multipartFile) throws IOException {
		Path uploadPath = Paths.get(uploadDir); 
		//create path from given url 
		if(!Files.exists(uploadPath)) {
			 
			Files.createDirectories(uploadPath);
		}
		
		try(InputStream insputStream = multipartFile.getInputStream() ){
			Path filePath = uploadPath.resolve(fileName);
			Files.copy(insputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
		}catch (IOException ex) {
			throw new IOException("Could not save file " + fileName,ex);
		
		
	}
	}
}
