package com.application.blog.service.Impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.application.blog.service.FileService;
import com.application.blog.exception.FileNotSupportedException;

@Service
public class FileServiceImpl implements FileService {

	@Override
	public String uploadImage(MultipartFile file, String path) throws IOException {
		
		//File name
		String fileName=file.getOriginalFilename();
		
		//File Size 
		long fileSize=file.getSize();
		
		//File content type
		String contentType=file.getContentType();
		
		//Extension
		String extension=fileName.substring(fileName.lastIndexOf("."));
		
		//Check file content type 
		System.out.println(contentType);
		if(!(contentType.equals("image/png")) && !(contentType.equals("image/jpeg")) && !(contentType.equals("image/jpg"))) {
			throw new FileNotSupportedException("File type not supported !!");
		}
		
		//Generate random file name
		String randomId=UUID.randomUUID().toString();
		String randomFileName=randomId.concat(extension);
		
		//Create path if not exists
		File f=new File(path);
		if(!f.exists()) {
			f.mkdir();
		}
			
		//Full path
		String fullPath= path + File.separator + randomFileName;
		
		//Copy file
		Files.copy(file.getInputStream(), Paths.get(fullPath));
		
		return randomFileName;
	}

	@Override
	public InputStream serveImage(String fileName, String path) throws FileNotFoundException {
		String fullPath = path + File.separator + fileName;
		InputStream is = new FileInputStream(fullPath);
		return is;
	}
	
	/*
	 * Notes: File Exception handling 
	 * 1)IOException - could not upload file, false -Api Response 
	 * 2)FileNotFoundException - File not found, false - Api Response
	 * 3)FileNotSupportedException(file.getContentType()) - File type not supported, false - Api Response 
	 * 4)MaxUploadSizeExceededException(file.getSize()) - File size too large, false - Api Response
	 *  ->NoException - File Uploaded
	 * Sucessfully, true, post - postResponse
	 */
	
	/*
	 * Path sourcePath = Paths.get("source.txt");
	 * Path destPath = Paths.get("destination.txt");
	 * 
	 * try { 
	 *    Files.copy(sourcePath, destPath);
	 *    System.out.println("File copied successfully!"); 
	 * } catch (IOException e) {
	 *    System.err.println("Error occurred during file copy: " + e.getMessage());
	 * }
	 */

}
