package com.barry.spring.controller;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.barry.spring.model.FileInfo;
import com.barry.spring.model.UploadResponseMessage;
import com.barry.spring.repository.FileRepository;
import com.barry.spring.service.FileService;


@RestController
@RequestMapping("/files")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class FilesController {

    private final FileService fileService;
    

    @Autowired
    public FilesController(FileService fileService) {
        this.fileService = fileService;
       
    }

    
    @PostMapping("/upload")
    public ResponseEntity<UploadResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) {
        fileService.save(file);

        return ResponseEntity.status(HttpStatus.OK)
                             .body(new UploadResponseMessage("Uploaded the file successfully: " + file.getOriginalFilename()));
    }
    @GetMapping("/{fileId}")
	 public byte[] getPhoto(@PathVariable String fileId) throws Exception{
    	return fileService.getPhoto(fileId);
	 }
    @GetMapping("/image/{fileName}")
	 public byte[] getImage(@PathVariable String fileName) throws Exception{
    	return fileService.getImage(fileName);
	 }
    
}