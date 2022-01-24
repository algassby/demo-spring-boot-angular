package com.barry.spring.service;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.barry.spring.exception.FileUploadException;
import com.barry.spring.model.FileInfo;
import com.barry.spring.repository.FileRepository;



@Service
@Transactional
public class FileService {

	@Autowired
	private FileRepository fileRepository;
	@Autowired 
	ServletContext context;
    @Value("${upload.path}")
    private String uploadPath;

    private Logger logger = LoggerFactory.getLogger(FileService.class);
    public FileInfo save(MultipartFile file) throws FileUploadException {
    	
    	
        try {
        	FileInfo fileInfo = new FileInfo();
            Path root = Paths.get(uploadPath);
            Path resolve = root.resolve(file.getOriginalFilename());
          
            if (resolve.toFile()
                       .exists()) {
                throw new FileUploadException("File already exists: " + file.getOriginalFilename());
            }
            logger.info(resolve.toString());
            fileInfo.setFileName(resolve.getFileName().toString());
        	fileInfo.setFileUrl(resolve.toString());
        	
        	logger.info(fileInfo.toString());
            Files.copy(file.getInputStream(), resolve);
           return fileRepository.save(fileInfo);
        	//System.out.println(resolve.getRoot().getFileName());
        } catch (Exception e) {
            throw new FileUploadException("Could not store the file. Error: " + e.getMessage());
        }
    }
    public byte[] getPhoto(String fileId) throws Exception{
    	FileInfo fileInfo = null;
    	//Path root = Paths.get(uploadPath);
        
    	if(fileRepository.existsById(fileId)) {
    		 fileInfo   = fileRepository.findById(fileId).get();
    		 
    	}
    	//URL url = getClass().getResource(uploadPath);
    	//File file = new File(url.getPath());
    	 String encodeImage = Base64.getEncoder().withoutPadding().encodeToString(Files.readAllBytes(Paths.get(String.join("",uploadPath,new StringBuffer().append(File.separatorChar).append(fileInfo.getFileName()))))); 
    	
    	//Path resolve = root.resolve(fileInfo.getOriginalFilename());
    	//FileInfo fileInfo   = fileRepository.findById(fileId).get();
		// return Files.readAllBytes(Paths.get(String.join("", context.getRealPath(uploadPath),fileInfo.getFileName())));
    	logger.info(String.join("", Paths.get(uploadPath).toString(),new StringBuffer().append(File.separatorChar),fileInfo.getFileName()));
    	logger.info(Files.readAllBytes(Paths.get(String.join("",uploadPath,new StringBuffer().append(File.separatorChar).append(fileInfo.getFileName())))).toString());
		 return Files.readAllBytes(Paths.get(String.join("",uploadPath,new StringBuffer().append(File.separatorChar).append(fileInfo.getFileName()))));
		
    	
	 }
    public byte[] getImage(String fileName) throws Exception{
//    	FileInfo fileInfo = null;
//    	
    	//URL url = getClass().getResource(uploadPath);
    	//File file = new File(url.getPath());
    	// String encodeImage = Base64.getEncoder().withoutPadding().encodeToString(Files.readAllBytes(Paths.get(String.join("",uploadPath,new StringBuffer().append(File.separatorChar).append(fileInfo.getFileName()))))); 
    	
    	//Path resolve = root.resolve(fileInfo.getOriginalFilename());
    	//FileInfo fileInfo   = fileRepository.findById(fileId).get();
		// return Files.readAllBytes(Paths.get(String.join("", context.getRealPath(uploadPath),fileInfo.getFileName())));
//    	logger.info(String.join("", Paths.get(uploadPath).toString(),new StringBuffer().append(File.separatorChar),fileInfo.getFileName()));
//    	logger.info(Files.readAllBytes(Paths.get(String.join("",uploadPath,new StringBuffer().append(File.separatorChar).append(fileInfo.getFileName())))).toString());
		 return Files.readAllBytes(Paths.get(String.join("",uploadPath,new StringBuffer().append(File.separatorChar).append(fileName))));
		
    	
	 }
}