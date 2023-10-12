package com.example.ppback.service;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.ppback.service.BaseHttpResponse;

import java.io.IOException;


public interface FileUploadService {
	  BaseHttpResponse uploadPara(String fileType, MultipartFile file,String para ) throws Exception;
}


