package com.example.ppback.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
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
    public void ExcelExportController(@RequestBody SearchRequest queryParams, HttpServletResponse response) throws IOException {//根据easyExcel的使用方法去写前后端
        // 从请求参数中获取年月、Vendor、type、pdcl等信息
        String yearMonth = queryParams.getMonthYear();
        String vendor = queryParams.getVendor();
        String type = queryParams.getType();
        String pdcl = queryParams.getGroup();
        
        log.info(yearMonth+" "+vendor+" "+type+ " "+pdcl+" ");
        BaseHttpResponse resp = null;
        try {
			excelService.exportData(yearMonth,vendor,type,pdcl,response);
			
		} catch (Exception e) {
			System.out.println(e);
			resp = new BaseHttpResponse<>();
			resp.setFailed();
		}
        //return resp;
    }
}
