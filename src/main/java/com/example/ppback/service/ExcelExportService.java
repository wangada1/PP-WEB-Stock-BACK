package com.example.ppback.service;

import jakarta.servlet.http.HttpServletResponse;

public interface ExcelExportService {
    void exportData(String yearMonth,String vendor,String pdcl,String type,HttpServletResponse response)  throws Exception;
}