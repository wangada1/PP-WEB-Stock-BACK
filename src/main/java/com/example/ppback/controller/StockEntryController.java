package com.example.ppback.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.example.ppback.service.BaseHttpResponse;
import com.example.ppback.service.FileUploadService;
import com.example.ppback.service.StockEntryService;


@CrossOrigin
@RestController
@RequestMapping("/stock")
public class StockEntryController {
	@Autowired StockEntryService service;

	@Autowired
	private FileUploadService fileUploadParaService;
	

	@PostMapping(value = "/import")
	public BaseHttpResponse importDCCSStock(@RequestPart(required = true) MultipartFile file,  String yearmonth) {
		BaseHttpResponse resp = null;
		try {
			resp = fileUploadParaService.uploadPara( "StockEntry", file,  yearmonth);

		} catch (Exception e) {
			System.out.println(e);
			resp = new BaseHttpResponse<>();
			resp.setFailed();

		}
		return resp;
	}

}

