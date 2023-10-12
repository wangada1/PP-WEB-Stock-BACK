package com.example.ppback.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.ppback.model.DataEntry;
import com.example.ppback.service.BaseHttpResponse;
import com.example.ppback.service.DataEntryService;
import com.example.ppback.service.FileUploadService;


@CrossOrigin
@RestController
@RequestMapping("/data")
public class DataEntryController {
	@Autowired DataEntryService service;
	
	@Autowired
	private FileUploadService fileUploadParaService;
	
	@PostMapping(value = "/add")
	@ResponseStatus(HttpStatus.CREATED)
	public DataEntry creatDataEntry(@RequestBody DataEntry dataEntry){
		return service.addDataEntry(dataEntry);
			
	}
	@GetMapping
	public List<DataEntry> getEntries(){
		return service.findAllEntries();
	}
	
	
	@PostMapping(value = "/import")
	public BaseHttpResponse importDCCSStock(@RequestPart(required = true) MultipartFile file,  String yearmonth) {
		BaseHttpResponse resp = null;
		try {
			resp = fileUploadParaService.uploadPara( "dataEntry", file,  yearmonth);
		
		} catch (Exception e) {
			System.out.println(e);
			resp = new BaseHttpResponse<>();
			resp.setFailed();
			
		}
		return resp;
	}

}
