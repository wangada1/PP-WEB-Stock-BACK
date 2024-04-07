package com.example.ppback.controller;

import java.nio.file.FileSystemNotFoundException;
import java.util.Arrays;
import java.util.List;

import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.example.ppback.base.SearchRequest;
import com.example.ppback.model.DataEntry;
import com.example.ppback.model.DataEntryImportEntity;
import com.example.ppback.model.PPByQuarterEntry;
import com.example.ppback.model.PPByQuarterPercent;
import com.example.ppback.model.PPPercentageCompare;
import com.example.ppback.model.TBByQuarterEntry;
import com.example.ppback.model.TBByQuarterPercent;
import com.example.ppback.model.TBPercentageCompare;
import com.example.ppback.service.BaseHttpResponse;
import com.example.ppback.service.DataEntryService;
import com.example.ppback.service.FileUploadService;
import com.example.ppback.service.GrEntryService;
import com.example.ppback.service.SoldEntryService;
import com.example.ppback.repository.PPDataGroupByPDCLandVendorRepository;

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
		 BaseHttpResponse<List<Integer>> pp = dtservice.getTotalPP(req.getVendor(),req.getGroup(), req.getType(), req.getMonthYear());
		// BaseHttpResponse<List<Integer>> pp = dtservice.getTotalCountByProductGroup(req.getGroup(), req.getVendor(),req.getMonthYear());
		 log.info("get pp data from: " + req.getVendor() + " " + req.getGroup() + " " + req.getType() + " in " + req.getMonthYear());
		return pp;
	}
	
	@PostMapping(value = "tb/get")
	public BaseHttpResponse<List<Integer>> getTBCountByGroup( @RequestBody SearchRequest req){
		BaseHttpResponse<List<Integer>> tb = dtservice.getTotalTB(req.getVendor(),req.getGroup(), req.getType(), req.getMonthYear());
		log.info("get tb data from: " + req.getVendor() + " " + req.getGroup() + " " + req.getType() + " in " + req.getMonthYear());
		return tb;
	}
	
	@PostMapping(value = "/pp/quarter/get")
	public List<PPByQuarterEntry> getPPQuarter(@RequestBody String yearMonth) {
		List<PPByQuarterEntry> ppByQuarterEntries = dtservice.getPPQuarterCount(yearMonth);
		return ppByQuarterEntries;
	}
	
	@PostMapping(value = "/pp/quarter/percent/get")
	public List<PPByQuarterPercent> getPPQuarterPercent(@RequestBody String yearMonth) {
		List<PPByQuarterPercent> ppByQuarterPercents = dtservice.getPPQuarterPercent(yearMonth);
		return ppByQuarterPercents;
	}
	
	@PostMapping(value = "/pp/percent/compare/get")
	public List<PPPercentageCompare> getPPComparePercent(@RequestBody String yearMonth) {
		List<PPPercentageCompare> ppPercentageCompares = dtservice.getPPCompare(yearMonth);
		return ppPercentageCompares;
	}
	
	@PostMapping(value = "/tb/quarter/get")
	public List<TBByQuarterEntry> getTBQuarter(@RequestBody String yearMonth) {
		List<TBByQuarterEntry> tbByQuarterEntries = dtservice.getTBQuarterCount(yearMonth);
		return tbByQuarterEntries;
	}
	
	@PostMapping(value = "/tb/quarter/percent/get")
	public List<TBByQuarterPercent> getTBQuarterPercent(@RequestBody String yearMonth) {
		List<TBByQuarterPercent> tbByQuarterPercents = dtservice.getTBQuarterPercent(yearMonth);
		return tbByQuarterPercents;
	}
	
	@PostMapping(value = "/tb/percent/compare/get")
	public List<TBPercentageCompare> getTBComparePercent(@RequestBody String yearMonth) {
		List<TBPercentageCompare> tbPercentageCompares = dtservice.getTBCompare(yearMonth);
		return tbPercentageCompares;
	}
		    
	@PostMapping(value = "/pp/save")
	public void saveCountByGroup(@RequestBody SearchRequest req){
		List<String> allpdcls = dtservice.findAllDistinctpdcl(req.getMonthYear());
		for(String pdcl: allpdcls){
			if(!pdcl.equals("")) {
				List<Integer> pp = dtservice.calcTotalCountByProductGroup(pdcl, req.getMonthYear());
				List<Integer> gr = grservice.getSumByIndexForMdltPN(pdcl, req.getMonthYear());
				int grlen = gr.size();
				int pplen = 24 - grlen;
				for(int i = 0; i < pplen; i++) {
					gr.add(pp.get(i));
				}
				dtservice.savePPByMonth(gr, req.getMonthYear(), pdcl);
			}
		}		
		dtservice.ppQuarterCount(req.getMonthYear());
		dtservice.ppQuarterPercentage(req.getMonthYear());	
		dtservice.ppComparePercentage(req.getMonthYear());
	}
	
	@PostMapping(value = "/tb/save")
	public void savetbCountByGroup(@RequestBody SearchRequest req){
		List<String> allpdcls = dtservice.findAllDistinctpdcl(req.getMonthYear());
		for(String pdcl: allpdcls){
			if(!pdcl.equals("")) {
				List<Integer> tb = dtservice.calcTotalTBCountByProductGroup(pdcl, req.getMonthYear());
				List<Integer> sold = soldService.getSumByIndexForMdltPN(pdcl, req.getMonthYear());
				int soldlen = sold.size();
				int tblen = 24 - soldlen;
				for(int i = 0; i < tblen; i++) {
					sold.add(tb.get(i));
				}
				dtservice.saveTBByMonth(sold, req.getMonthYear(), pdcl);
			}
		}		
		dtservice.tbQuarterCount(req.getMonthYear());
		dtservice.tbQuarterPercentage(req.getMonthYear());	
		dtservice.tbComparePercentage(req.getMonthYear());
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
