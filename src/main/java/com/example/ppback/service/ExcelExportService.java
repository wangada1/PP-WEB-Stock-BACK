package com.example.ppback.service;

import java.util.Map;

import jakarta.servlet.http.HttpServletResponse;

public interface ExcelExportService {
    String exportData(String yearMonth,String vendor,String pdcl,String type,HttpServletResponse response)  throws Exception;
}