package com.example.ppback.controller;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ppback.base.SearchRequest;

import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/download")
public class ExcelController {

    @PostMapping(value = "/excel")
    public void downloadExcel(@RequestBody SearchRequest queryParams) throws IOException {//根据easyExcel的使用方法去写前后端
        // 从请求参数中获取年月、Vendor、type、pdcl等信息
        String yearMonth = queryParams.getMonthYear();
        String vendor = queryParams.getVendor();
        String type = queryParams.getType();
        String pdcl = queryParams.getGroup();
        log.info(yearMonth);
    }
}
