package com.example.ppback.service;
import org.springframework.web.multipart.MultipartFile;


public interface FileUploadService {
	  BaseHttpResponse uploadPara(String fileType, MultipartFile file,String para ) throws Exception;
}


