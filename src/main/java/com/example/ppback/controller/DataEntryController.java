package com.example.ppback.controller;

import java.util.List;

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
import com.example.ppback.base.SearchRequest;
import com.example.ppback.model.DataEntry;
import com.example.ppback.service.BaseHttpResponse;
import com.example.ppback.service.DataEntryService;
import com.example.ppback.service.FileUploadService;
import com.example.ppback.service.GrEntryService;
import com.example.ppback.service.SoldEntryService;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin
@Data
@Slf4j
@RestController
@RequestMapping("/data")
public class DataEntryController {
	@Autowired DataEntryService dtservice;
	@Autowired GrEntryService grservice;
	@Autowired SoldEntryService soldService;

	@Autowired
	private FileUploadService fileUploadParaService;

	@PostMapping(value = "/add")
	@ResponseStatus(HttpStatus.CREATED)
	public DataEntry creatDataEntry(@RequestBody DataEntry dataEntry){
		return dtservice.addDataEntry(dataEntry);

	}
	@GetMapping
	public List<DataEntry> getEntries(){
		return dtservice.findAllEntries();
	}
	
	@PostMapping(value = "/pp/get")
	public BaseHttpResponse<List<Integer>> getPPCountByGroup( @RequestBody SearchRequest req){
		 BaseHttpResponse<List<Integer>> pp = null ;//dtservice.getTotalPP(req.getVendor(),req.getPdcl(), req.getType(), req.getMonthYear());
		// BaseHttpResponse<List<Integer>> pp = dtservice.getTotalCountByProductGroup(req.getGroup(), req.getVendor(),req.getMonthYear());
		 log.info("get pp data from: " + req.getVendor() + " " + req.getPdcl() + " " + req.getType() + " in " + req.getMonthYear());
		return pp;
	}
	
	@PostMapping(value = "tb/get")
	public BaseHttpResponse<List<Integer>> getTBCountByGroup( @RequestBody SearchRequest req){
		BaseHttpResponse<List<Integer>> tb = null ;//dtservice.getTotalTB(req.getVendor(),req.getPdcl(), req.getType(), req.getMonthYear());
		log.info("get tb data from: " + req.getVendor() + " " + req.getPdcl() + " " + req.getType() + " in " + req.getMonthYear());
		return tb;
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
