package com.example.ppback.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.example.ppback.service.BaseHttpResponse;
import com.example.ppback.service.FileUploadService;
import com.example.ppback.service.MaterialMasterEntryService;


@CrossOrigin
@RestController
@RequestMapping("/materialMaster")
public class MaterialMasterEntryControlloer {
	@Autowired MaterialMasterEntryService service;

	@Autowired
	private FileUploadService fileUploadParaService;
	

	@PostMapping(value = "/import")
	public BaseHttpResponse importDCCSStock(@RequestPart(required = true) MultipartFile file,  String yearmonth) {
		BaseHttpResponse resp = null;
		try {
			resp = fileUploadParaService.uploadPara( "MaterialMasterEntry", file,  yearmonth);

		} catch (Exception e) {
			System.out.println(e);
			resp = new BaseHttpResponse<>();
			resp.setFailed();

		}
		return resp;
	}

}

