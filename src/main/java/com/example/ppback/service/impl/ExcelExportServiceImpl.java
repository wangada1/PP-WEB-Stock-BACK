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

import javax.servlet.http.HttpServletResponse;
import com.alibaba.excel.support.ExcelTypeEnum;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.OutputStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ExcelExportServiceImpl implements ExcelExportService {
	@Autowired SheetExportUtil SheetExportUtil;
    @Override
    public String exportData(String yearMonth,String vendor,String pdcl,String type,HttpServletResponse response) throws Exception {
    	String truePath = TestFileUtil.getPath();
    	truePath = truePath.replace("file:/","");
    	truePath = truePath.replace(".war!","");
    	truePath = truePath.replace("classes!","classes");
    	System.out.println(truePath);
    	String templateFileName = truePath + "demo" + "/" + "fill" + "/" + "PP summary format.xlsx";
		 String fileName0 =  truePath + "PP summary sheet " + "0" + ".xlsx";//\target\classes
		 String fileName1 =  truePath + "PP summary sheet " + "1" + ".xlsx";//\target\classes
		 String fileName2 =  truePath + "PP summary sheet " + "2" + ".xlsx";//\target\classes
		 String fileName3 =  truePath + "PP summary sheet " + "3" + ".xlsx";//\target\classes
		 String fileTargetName = truePath + "PP " + yearMonth + " " + (vendor==""?"":vendor+" ") + (pdcl==""?"":pdcl+" ") + (type==""?"":type+" ")  +  "summary"  + ".xlsx";//\target\classes;
		 Map<String, Object> map = MapUtils.newHashMap();
		 map = SheetExportUtil.summary1(yearMonth, vendor, pdcl, type);
		 List<String> sheetNames = new ArrayList<>();
		 sheetNames.add("Summary1 " + map.get("YearMinus1") + " and " + map.get("Year"));
		 sheetNames.add("Summary1 " + map.get("Year") + " and " + map.get("YearPlus1"));
		 sheetNames.add("Summary2 " + map.get("YearMinus1") + " and " + map.get("Year"));
		 sheetNames.add("Summary2 " + map.get("Year") + " and " + map.get("YearPlus1"));
		 EasyExcel.write(fileName0).withTemplate(templateFileName).sheet(0,null).doFill(map);
		 EasyExcel.write(fileName1).withTemplate(templateFileName).sheet(1,null).doFill(map);
		 EasyExcel.write(fileName2).withTemplate(templateFileName).sheet(2,null).doFill(map);
		 EasyExcel.write(fileName3).withTemplate(templateFileName).sheet(3,null).doFill(map);
		 //合并多个文件
		 List<String> originalFilePathList = new ArrayList();
		 originalFilePathList.add(truePath + "PP summary sheet " + "0" + ".xlsx");
		 originalFilePathList.add(truePath + "PP summary sheet " + "1" + ".xlsx");
		 originalFilePathList.add(truePath + "PP summary sheet " + "2" + ".xlsx");
		 originalFilePathList.add(truePath + "PP summary sheet " + "3" + ".xlsx");
		 FileInputStream originalFile = null;
		 Workbook originalWorkbook;
		 Workbook newWorkbook = new XSSFWorkbook(); 
		 for (int i = 0; i < 4; i++) {

             String originalFilePath = originalFilePathList.get(i);

             // 读取原始Excel文件
             originalFile = new FileInputStream(originalFilePath);
             
             originalWorkbook = new XSSFWorkbook(originalFile);

             Sheet originalSheet = originalWorkbook.getSheetAt(i);

             Sheet newSheet = newWorkbook.createSheet(sheetNames.get(i));

             // 复制原始Sheet到新文件（包括内容和样式）
             copySheet(originalSheet, newSheet, newWorkbook);
         }
         // 保存新文件
         try (FileOutputStream fileOut = new FileOutputStream(fileTargetName)) {
             newWorkbook.write(fileOut);
         }

         System.out.println("复制完成");
		// 返回包含路径的文件名给调用处
	        return fileTargetName;
    }
    private static void copySheet(Sheet source, Sheet target, Workbook newWorkbook) {
        // 处理合并单元格
        for (int i = 0; i < source.getNumMergedRegions(); i++) {
            CellRangeAddress mergedRegion = source.getMergedRegion(i);
            target.addMergedRegion(mergedRegion);
        }

        // 复制内容和样式
        for (int rowNum = 0; rowNum <= source.getLastRowNum(); rowNum++) {
            Row sourceRow = source.getRow(rowNum);
            Row newRow = target.createRow(rowNum);

            if (sourceRow != null) {
                // 复制每个单元格的内容和样式
                for (int colNum = 0; colNum < sourceRow.getLastCellNum(); colNum++) {
                    Cell sourceCell = sourceRow.getCell(colNum, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    Cell newCell = newRow.createCell(colNum);

                    // 复制内容
                    copyCellValue(sourceCell, newCell);

                    // 复制样式
                    CellStyle newCellStyle = newWorkbook.createCellStyle();
                    newCellStyle.cloneStyleFrom(sourceCell.getCellStyle());
                    newCell.setCellStyle(newCellStyle);

                    // 复制宽度和高度
                    target.setColumnWidth(colNum, source.getColumnWidth(colNum));
                    newRow.setHeight(sourceRow.getHeight());
                }
            }
        }
    }

    private static void copyCellValue(Cell sourceCell, Cell newCell) {
        if (sourceCell == null) {
            return;
        }

        DataFormatter dataFormatter = new DataFormatter();
        String formattedValue = dataFormatter.formatCellValue(sourceCell);

        if (sourceCell.getCellType() == CellType.NUMERIC) {
            // 处理数值类型
            if (DateUtil.isCellDateFormatted(sourceCell)) {
                // 如果是日期格式，则直接设置日期值
                newCell.setCellValue(sourceCell.getDateCellValue());
            } else {
                // 否则，获取数值并设置
                double numericValue = sourceCell.getNumericCellValue();
                newCell.setCellValue(numericValue);
            }
        } else {
            // 非数值类型，直接设置格式化后的值
            newCell.setCellValue(formattedValue);
        }
    }
}
