package com.example.ppback.controller;

import java.util.List;
import java.util.Map;

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

import com.alibaba.excel.util.MapUtils;
import com.example.ppback.base.SearchRequest;
import com.example.ppback.model.DataEntry;
import com.example.ppback.service.BaseHttpResponse;
import com.example.ppback.service.DataEntryService;
import com.example.ppback.service.FileUploadService;
import com.example.ppback.service.GrEntryService;
import com.example.ppback.service.SoldEntryService;
import com.example.ppback.util.SheetExportUtil;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin
@Data
@Slf4j
@RestController
@RequestMapping("/data")
public class DataEntryController {
	@Autowired SheetExportUtil SheetExportUtil;
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
	
	@PostMapping(value = "/pp/get") // 这里返回一个map，让前端根据Key去查找对应的值，只留pp和tb两个接口就行
	public BaseHttpResponse<Map<String, Object>> getPPCountByGroup( @RequestBody SearchRequest req){
		Map<String, Object> map = MapUtils.newHashMap();
		 map = SheetExportUtil.summary1(req.getMonthYear(), req.getVendor(), req.getPdcl(), req.getType());
		 log.info("get pp data from: " + req.getVendor() + " " + req.getPdcl() + " " + req.getType() + " in " + req.getMonthYear());
		 BaseHttpResponse<Map<String, Object>> pp = new BaseHttpResponse(map);
		 return pp;
	}
	
	@PostMapping(value = "tb/get")
	public BaseHttpResponse<Map<String, Object>> getTBCountByGroup( @RequestBody SearchRequest req){
		Map<String, Object> map = MapUtils.newHashMap();
		 map = SheetExportUtil.summary1(req.getMonthYear(), req.getVendor(), req.getPdcl(), req.getType());
		log.info("get tb data from: " + req.getVendor() + " " + req.getPdcl() + " " + req.getType() + " in " + req.getMonthYear());
		BaseHttpResponse<Map<String, Object>> tb = new BaseHttpResponse(map);
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
