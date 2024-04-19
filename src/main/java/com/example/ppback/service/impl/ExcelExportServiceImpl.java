package com.example.ppback.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.util.ListUtils;
import com.alibaba.excel.util.MapUtils;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.alibaba.excel.write.builder.ExcelWriterSheetBuilder;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.example.ppback.model.Summary1;
import com.example.ppback.model.TestFileUtil;
import com.example.ppback.service.BaseHttpResponse;
import com.example.ppback.service.ExcelExportService;
import com.example.ppback.util.SheetExportUtil;

import jakarta.servlet.http.HttpServletResponse;
import com.alibaba.excel.support.ExcelTypeEnum;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.io.OutputStream;

import org.springframework.stereotype.Service;

@Service
public class ExcelExportServiceImpl implements ExcelExportService {

    @Override
    public void exportData(String yearMonth,String vendor,String pdcl,String type,HttpServletResponse response) throws Exception {
    	BaseHttpResponse BaseHttpResponse = new BaseHttpResponse();
    	String templateFileName = TestFileUtil.getPath() + "demo" + File.separator + "fill" + File.separator + "PP summary format.xlsx";
		 String fileName = "C:\\Users\\BNG1SGH\\Desktop\\wangyang\\ppweb\\PPreportwebsite\\" + "PP summary demo" + System.currentTimeMillis() + ".xlsx";//这个后面再讨论
		 SheetExportUtil SheetExportUtil = new SheetExportUtil();
		 //String nextYearMonth = YearMonth.parse(yearMonth, DateTimeFormatter.ofPattern("yyyy-MM")).plusMonths(12).toString();//下一年的年月值
		 //Summary1 sheet1 = SheetExportUtil.summary1(yearMonth,vendor,pdcl,type);
		 //Summary1 sheet2 = SheetExportUtil.summary1(nextYearMonth,vendor,pdcl,type);
		 //Summary2 sheet3 = SheetExportUtil.summary2(yearMonth,vendor,pdcl,type);
		 //Summary2 sheet4 = SheetExportUtil.summary2(nextYearMonth,vendor,pdcl,type);
		 //String sheetname1 = "Summary1 " + sheet1.getYearMinus1() + " and " + sheet1.getYear();
		// String sheetname2 = "Summary1 " + sheet2.getYearMinus1() + " and " + sheet2.getYear();
		 //String sheetname3 = "";
		 //String sheetname4 = "";
		 //EasyExcel.write(fileName,Summary1.class).inMemory(true).withTemplate(templateFileName).sheet(0,sheetname1).doFill(sheet1);
		 //EasyExcel.write(fileName).withTemplate(templateFileName).sheet(1,sheetname2).doFill(sheet2);
		//BaseHttpResponse.setSuccess();
		//return BaseHttpResponse;
		 Map<String, Object> map1 = MapUtils.newHashMap();
		 map1 = SheetExportUtil.summary1(yearMonth, vendor, pdcl, type);
		 String sheetname1 = "Summary1 " + map1.get("YearMinus1") + " and " + map1.get("Year");
		 EasyExcel.write(fileName).withTemplate(templateFileName).sheet(0,sheetname1).doFill(map1);
    }
}
