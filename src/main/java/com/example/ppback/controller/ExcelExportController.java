package com.example.ppback.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ppback.base.SearchRequest;
import com.example.ppback.service.BaseHttpResponse;
import com.example.ppback.service.ExcelExportService;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/download")
public class ExcelExportController {
	@Autowired ExcelExportService excelService;
    @PostMapping(value = "/excel")
    public BaseHttpResponse ExcelExportController(@RequestBody SearchRequest queryParams, HttpServletResponse response) throws IOException {
        String yearMonth = queryParams.getMonthYear();
        String vendor = queryParams.getVendor();
        String pdcl = queryParams.getGroup();
        String type = queryParams.getType();
        
        log.info(yearMonth+" "+vendor+" "+type+ " "+pdcl+" ");
        String filePath ="";
        BaseHttpResponse httpResponse = new BaseHttpResponse();
        try {
        	 filePath = excelService.exportData(yearMonth,vendor,pdcl,type,response);
        	 String filePathTrue = filePath.substring(1);
        	 log.info(filePathTrue);
             httpResponse.setFilePath(filePathTrue);
             return httpResponse;
		} catch (Exception e) {
			System.out.println(e);
		}
        return httpResponse;
    }
}
