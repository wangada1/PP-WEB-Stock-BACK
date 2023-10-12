package com.example.ppback.util;
import java.io.IOException;

import java.io.UnsupportedEncodingException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.ppback.model.ColumnIndex;
import com.example.ppback.model.DataEntry;
import com.example.ppback.model.DataEntryImportEntity;
import lombok.extern.slf4j.Slf4j;


public class ExcelUtil {

	

	private static final Logger LOG = LoggerFactory.getLogger(ExcelUtil.class);

	private static void createTitle(int num, String value, Row titleRow, CellStyle cellStyleTitle) {

		Cell titleCellRegion = titleRow.createCell(num);
		titleCellRegion.setCellValue(value);
		titleCellRegion.setCellStyle(cellStyleTitle);

	}

	public static int getColumnNum(Workbook workbook) {
		if ((null == workbook)) {
			return 0;
		}
		Workbook excel = workbook;
		Sheet sheet = excel.getSheetAt(0);
		// 获取到第一行数据
		Row dataRow = sheet.getRow(1);
		// 获取到总共多少行
		short cellNum = dataRow.getLastCellNum();
		return cellNum;
	}

	/**
	 * @param hssfCell
	 * @return fix读取excel时,数字自动带“.0”
	 */
	private static String getValue(Cell hssfCell) {
		if (hssfCell.getCellType() == CellType.BOOLEAN) {
			// 返回布尔类型的值
			return String.valueOf(hssfCell.getBooleanCellValue());
		} else if (hssfCell.getCellType() == CellType.NUMERIC) {
			// 返回数值类型的值
			Object inputValue = null;// 单元格值
			Long longVal = Math.round(hssfCell.getNumericCellValue());
			Double doubleVal = hssfCell.getNumericCellValue();
			if (Double.parseDouble(longVal + ".0") == doubleVal) {
				// 判断是否含有小数位.0
				inputValue = longVal;
			} else {
				inputValue = doubleVal;
			}
			// 格式化为四位小数，按自己需求选择；
			DecimalFormat df = new DecimalFormat("#.####");
			// 返回String类型
			return String.valueOf(df.format(inputValue));
		} else {
			// 返回字符串类型的值
			return String.valueOf(hssfCell.getStringCellValue());
		}
	}

	
	

	public static List<DataEntryImportEntity> excel2Entry(Workbook workbook) throws Exception {
		if (null == workbook) {
			return null;
		}
		List<DataEntryImportEntity> entryList = new ArrayList<>();
		try {
			Sheet sheet = workbook.getSheetAt(0);
			int rowNum = sheet.getLastRowNum();
			Row headerRow = sheet.getRow(7);
			int cellNum = headerRow.getLastCellNum();

			Map<String, Integer> keyMap = new HashMap<>();
			String cellName, fieldName, columnName;
			for (int i = 8; i <= rowNum; i++) {
				DataEntryImportEntity entity = new DataEntryImportEntity();
				Class<DataEntryImportEntity> entityClass = DataEntryImportEntity.class;
				Field[] fields = entityClass.getDeclaredFields();
				for (Field field : fields) {
			        if (field.isAnnotationPresent(ColumnIndex.class)) {
			            Annotation annotation = field.getAnnotation(ColumnIndex.class);
			            ColumnIndex excelColumnIndex = (ColumnIndex) annotation;
			            int columnIndex = excelColumnIndex.value();
			            setField(sheet.getRow(i).getCell(columnIndex), field, entity);
			        }
			    }	
				entryList.add(entity);
			}
		} catch (Exception e) {
			throw e;
		}
		return entryList;
	}

	private static void setField(Cell cell, Field field, DataEntryImportEntity entity)
			throws IllegalAccessException, ParseException, UnsupportedEncodingException {
		field.setAccessible(true);
	    Class<? extends Object> type = field.getType();
	    
	    if (cell == null) {
	        // Handle the case when the cell is null, e.g., set a default value or leave the field as it is
	        if (type.equals(Integer.class)) {
	            field.set(entity, 0); // Set a default value (0) for Integer fields
	        } else if (type.equals(String.class)) {
	            field.set(entity, ""); // Set an empty string for String fields
	        }
	        return;
	    }
	    
	    Object cellValue = cell.toString(); // Get the cell value as an Object

	    if (type.equals(Integer.class)) {
	        if (cellValue != null) {
	            String string = cellValue.toString();
	            if (StringUtils.isNotEmpty(string)) {
	                string = new BigDecimal(string).toPlainString();
	                int index = string.lastIndexOf('.');
	                if (index > 0) {
	                    string = string.substring(0, index);
	                }
	                field.set(entity, Integer.parseInt(string));
	            } else {
	                field.set(entity, 0); // Set a default value (0) for empty cell values
	            }
	        } else {
	            field.set(entity, 0); // Set a default value (0) for null cell values
	        }
	    } else if (type.equals(String.class)) {
	        if (cellValue != null) {
	            field.set(entity, cellValue.toString());
	        } else {
	            field.set(entity, ""); // Set an empty string for null cell values
	        }
	    }
	}
}
