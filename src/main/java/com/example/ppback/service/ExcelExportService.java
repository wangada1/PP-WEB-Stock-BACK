package com.example.ppback.service;

import javax.servlet.http.HttpServletResponse;

public interface ExcelExportService {
    String exportData(String yearMonth,String vendor,String pdcl,String type,HttpServletResponse response)  throws Exception;
}