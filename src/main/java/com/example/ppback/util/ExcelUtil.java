package com.example.ppback.util;
import java.io.UnsupportedEncodingException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.ppback.model.ColumnIndex;
import com.example.ppback.model.DataEntryImportEntity;
import com.example.ppback.model.GrEntryImportEntity;
import com.example.ppback.model.SoldEntryImportEntity;
import com.example.ppback.model.MaterialMasterImportEntity;
import com.example.ppback.model.ProductListImportEntity;
import com.example.ppback.model.InfoRecordImportEntity;
import com.example.ppback.model.StockImportEntity;


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
			for (int i = 8; i <= rowNum; i++) {
				Row row = sheet.getRow(i);
				Cell PNCell = row.getCell(0); 
				if (PNCell == null || PNCell.getCellType() == CellType.BLANK) {
	                // If the row is empty, stop reading further
	                break;
	            }
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


	public static List<GrEntryImportEntity> excel2Gr(Workbook workbook, int count) throws Exception {
		if (workbook == null) {
			return null;
		}
		List<GrEntryImportEntity> entryList = new ArrayList<>();
		try {
			Sheet sheet = workbook.getSheetAt(1);
			int rowNum = sheet.getLastRowNum();
			for (int i = 3; i <= rowNum; i++) {
				Row row = sheet.getRow(i);
				Cell PNCell = row.getCell(0); 
				if (PNCell == null || PNCell.getCellType() == CellType.BLANK) {
	                // If the row is empty, stop reading further
	                break;
	            }
				GrEntryImportEntity entity = new GrEntryImportEntity();
				Class<GrEntryImportEntity> entityClass = GrEntryImportEntity.class;
				Field[] fields = entityClass.getDeclaredFields();
				for (Field field : fields) {
			        if (field.isAnnotationPresent(ColumnIndex.class)) {
			            Annotation annotation = field.getAnnotation(ColumnIndex.class);
			            ColumnIndex excelColumnIndex = (ColumnIndex) annotation;
			            int columnIndex = excelColumnIndex.value();
			            if(sheet.getRow(i)!=null) setGrField(sheet.getRow(i), columnIndex, count, field, entity);
			        }
			    }
				entryList.add(entity);
			}
		} catch (Exception e) {
			throw e;
		}
		return entryList;
	}
	
	public static List<MaterialMasterImportEntity> excel2MaterialMaster(Workbook workbook) throws Exception {
		if (null == workbook) {
			return null;
		}
		List<MaterialMasterImportEntity> entryList = new ArrayList<>();
		try {
			Sheet sheet = workbook.getSheetAt(0);
			int rowNum = sheet.getLastRowNum();
			for (int i = 4; i <= rowNum; i++) {
				MaterialMasterImportEntity entity = new MaterialMasterImportEntity();
				Class<MaterialMasterImportEntity> entityClass = MaterialMasterImportEntity.class;
				Field[] fields = entityClass.getDeclaredFields();
				for (Field field : fields) {
			        if (field.isAnnotationPresent(ColumnIndex.class)) {
			            Annotation annotation = field.getAnnotation(ColumnIndex.class);
			            ColumnIndex excelColumnIndex = (ColumnIndex) annotation;
			            int columnIndex = excelColumnIndex.value();
			            if(sheet.getRow(i)!=null) {
			            setMaterialMasterField(sheet.getRow(i).getCell(columnIndex), field, entity);}
			        }
			    }
				entryList.add(entity);
			}
		} catch (Exception e) {
			throw e;
		}
		return entryList;
	}
	
	private static void setMaterialMasterField(Cell cell, Field field, MaterialMasterImportEntity entity)
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
	
	public static List<StockImportEntity> excel2Stock(Workbook workbook) throws Exception {
		if (null == workbook) {
			return null;
		}
		List<StockImportEntity> entryList = new ArrayList<>();
		try {
			Sheet sheet = workbook.getSheetAt(1);//这个为啥会是1呢？是否存在隐藏的或保护的sheet?
			int rowNum = sheet.getLastRowNum();
			for (int i = 3; i <= rowNum; i++) {
				Row row = sheet.getRow(i);
				Cell PNCell = row.getCell(0); 
				if (PNCell == null || PNCell.getCellType() == CellType.BLANK) {
	                // If the row is empty, stop reading further
	                break;
	            }
				StockImportEntity entity = new StockImportEntity();
				Class<StockImportEntity> entityClass = StockImportEntity.class;
				Field[] fields = entityClass.getDeclaredFields();
				for (Field field : fields) {
			        if (field.isAnnotationPresent(ColumnIndex.class)) {
			            Annotation annotation = field.getAnnotation(ColumnIndex.class);
			            ColumnIndex excelColumnIndex = (ColumnIndex) annotation;
			            int columnIndex = excelColumnIndex.value();
			            if(sheet.getRow(i)!=null) setStockField(sheet.getRow(i), columnIndex, field, entity);
			        }
			    }
				entryList.add(entity);
			}
		} catch (Exception e) {
			throw e;
		}
		return entryList;
	}
	
	public static List<SoldEntryImportEntity> excel2Sold(Workbook workbook, int count) throws Exception {
		if (workbook == null) {
			return null;
		}
		List<SoldEntryImportEntity> entryList = new ArrayList<>();
		try {
			Sheet sheet = workbook.getSheetAt(1);
			int rowNum = sheet.getLastRowNum();
			for (int i = 3; i <= rowNum; i++) {
				Row row = sheet.getRow(i);
				Cell PNCell = row.getCell(0); 
				if (PNCell == null || PNCell.getCellType() == CellType.BLANK) {
	                // If the row is empty, stop reading further
	                break;
	            }
				SoldEntryImportEntity entity = new SoldEntryImportEntity();
				Class<SoldEntryImportEntity> entityClass = SoldEntryImportEntity.class;
				Field[] fields = entityClass.getDeclaredFields();
				for (Field field : fields) {
			        if (field.isAnnotationPresent(ColumnIndex.class)) {
			            Annotation annotation = field.getAnnotation(ColumnIndex.class);
			            ColumnIndex excelColumnIndex = (ColumnIndex) annotation;
			            int columnIndex = excelColumnIndex.value();
			            if(sheet.getRow(i)!=null) { setSoldField(sheet.getRow(i), columnIndex, count, field, entity);}
			        }
			    }
				entryList.add(entity);
			}
		} catch (Exception e) {
			throw e;
		}
		return entryList;
	}
	
	private static void setStockField(Row row, int columnIndex, Field field, StockImportEntity entity)
				throws IllegalAccessException, ParseException, UnsupportedEncodingException {
		field.setAccessible(true);
	    Class<? extends Object> type = field.getType();
	    Cell cell = row.getCell(columnIndex);
	    if (cell == null) {
	        // Handle the case when the cell is null, e.g., set a default value or leave the field as it is
	        if (type.equals(Integer.class)) {
	            field.set(entity, 0); // Set a default value (0) for Integer fields
	        } else if (type.equals(String.class)) {
	            field.set(entity, ""); // Set an empty string for String fields
	        }
	        else if (type.equals(List.class)) {
	         	field.set(entity, new ArrayList<>());
	         }
	         return;
	    }

	    Object cellValue = cell.toString(); // Get the cell value as an Object


	    if (type.equals(double.class)) {
	        if (cellValue != null) {
	            String string = cellValue.toString().trim(); // Trim leading and trailing spaces
	            if (string.equals("*")) {
	                field.set(entity, 0); // Handle asterisk by setting to 0 or another default value
	            } else if (StringUtils.isNotEmpty(string)) {
	                try {
	                    field.set(entity, Double.parseDouble(string)); // Attempt to parse a valid integer
	                } catch (NumberFormatException e) {
	                    // Handle the case where the string is not a valid integer
	                    // You can set a default value or handle the exception as needed
	                    field.set(entity, 0); // Set a default value (0) for invalid values
	                }
	            } else {
	                field.set(entity, 0); // Set a default value (0) for empty cell values
	            }
	        } else {
	            field.set(entity, 0); // Set a default value (0) for null cell values
	        }
	    }
	    else if (type.equals(String.class)) {
	        if (cellValue != null) {
	            field.set(entity, cellValue.toString());
	        } else {
	            field.set(entity, ""); // Set an empty string for null cell values
	        }
	    }
	    else if (type.equals(List.class)) {
	    	List<Integer> toset = new ArrayList<>();
	    	for(int i = columnIndex; i < columnIndex + 18 ; i += 1){
	    		Cell curCell = row.getCell(i);
	    		String string = curCell.toString();
	            if (StringUtils.isNotEmpty(string)) {
	            	try {
	            		   string = new BigDecimal(string).toPlainString();
	 	                   int index = string.lastIndexOf('.');
	 	                   if (index > 0) {
	 	                     string = string.substring(0, index);
	 	                   }
	 	                   toset.add(Integer.parseInt(string));
		                } catch (NumberFormatException e) {
		                    // Handle the case where the string is not a valid integer
		                    // You can set a default value or handle the exception as needed
		                    toset.add(0);// Set a default value (0) for invalid values
		                 }
	            } else {
	                toset.add(0);
	            }
	    	}
	    	field.set(entity, toset);
	    }
	}
	
	private static void setSoldField(Row row, int columnIndex, int count, Field field, SoldEntryImportEntity entity)
			throws IllegalAccessException, ParseException, UnsupportedEncodingException {
		
		field.setAccessible(true);
	    Class<? extends Object> type = field.getType();
	    Cell cell = row.getCell(columnIndex);
	    if (cell == null) {
	        // Handle the case when the cell is null, e.g., set a default value or leave the field as it is
	        if (type.equals(Integer.class)) {
	            field.set(entity, 0); // Set a default value (0) for Integer fields
	        } else if (type.equals(String.class)) {
	            field.set(entity, ""); // Set an empty string for String fields
	        }
	        else if (type.equals(List.class)) {
	         	field.set(entity, new ArrayList<>());
	         }
	         return;
	    }

	    Object cellValue = cell.toString(); // Get the cell value as an Object


	    if (type.equals(double.class)) {
	        if (cellValue != null) {
	            String string = cellValue.toString().trim(); // Trim leading and trailing spaces
	            if (string.equals("*")) {
	                field.set(entity, 0); // Handle asterisk by setting to 0 or another default value
	            } else if (StringUtils.isNotEmpty(string)) {
	                try {
	                    field.set(entity, Double.parseDouble(string)); // Attempt to parse a valid integer
	                } catch (NumberFormatException e) {
	                    // Handle the case where the string is not a valid integer
	                    // You can set a default value or handle the exception as needed
	                    field.set(entity, 0); // Set a default value (0) for invalid values
	                }
	            } else {
	                field.set(entity, 0); // Set a default value (0) for empty cell values
	            }
	        } else {
	            field.set(entity, 0); // Set a default value (0) for null cell values
	        }
	    }
	    else if (type.equals(String.class)) {
	        if (cellValue != null) {
	            field.set(entity, cellValue.toString());
	        } else {
	            field.set(entity, ""); // Set an empty string for null cell values
	        }
	    }
	    else if (type.equals(List.class)) {
	    	List<Integer> toset = new ArrayList<>();
	        if(count <= 6) {
	        	count = count - 1 + 12;
	        }
	        //否则计算今年和明年  
	        else{
	        	count = count - 1;
	        }
	    	for(int i = columnIndex; i < columnIndex + 2 * count ; i += 2){
	    		Cell curCell = row.getCell(i);
	    		String string = curCell.toString();
	            if (StringUtils.isNotEmpty(string)) {
	            	try {
	            		   string = new BigDecimal(string).toPlainString();
	 	                   int index = string.lastIndexOf('.');
	 	                   if (index > 0) {
	 	                     string = string.substring(0, index);
	 	                   }
	 	                   toset.add(Integer.parseInt(string));
		                } catch (NumberFormatException e) {
		                    // Handle the case where the string is not a valid integer
		                    // You can set a default value or handle the exception as needed
		                    toset.add(0);// Set a default value (0) for invalid values
		                 }
	            } else {
	                toset.add(0);
	            }
	    	}
	    	field.set(entity, toset);
	    }
	}
	
	public static List<InfoRecordImportEntity> excel2InfoRecord(Workbook workbook) throws Exception {
		if (null == workbook) {
			return null;
		}
		List<InfoRecordImportEntity> entryList = new ArrayList<>();
		try {
			Sheet sheet = workbook.getSheetAt(0);
			int rowNum = sheet.getLastRowNum();
			for (int i = 2; i <= rowNum; i++) {
				InfoRecordImportEntity entity = new InfoRecordImportEntity();
				Class<InfoRecordImportEntity> entityClass = InfoRecordImportEntity.class;
				Field[] fields = entityClass.getDeclaredFields();
				for (Field field : fields) {
			        if (field.isAnnotationPresent(ColumnIndex.class)) {
			            Annotation annotation = field.getAnnotation(ColumnIndex.class);
			            ColumnIndex excelColumnIndex = (ColumnIndex) annotation;
			            int columnIndex = excelColumnIndex.value();
			            setInfoRecordField(sheet.getRow(i).getCell(columnIndex), field, entity);
			        }
			    }
				entryList.add(entity);
			}
		} catch (Exception e) {
			throw e;
		}
		return entryList;
	}
	
	private static void setInfoRecordField(Cell cell, Field field, InfoRecordImportEntity entity)
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
	
	private static void setGrField(Row row, int columnIndex, int count, Field field, GrEntryImportEntity entity)
			throws IllegalAccessException, ParseException, UnsupportedEncodingException {
		field.setAccessible(true);
	    Class<? extends Object> type = field.getType();
	    Cell cell = row.getCell(columnIndex);
	    if (cell == null) {
	        // Handle the case when the cell is null, e.g., set a default value or leave the field as it is
	        if (type.equals(Integer.class)) {
	            field.set(entity, 0); // Set a default value (0) for Integer fields
	        } else if (type.equals(String.class)) {
	            field.set(entity, ""); // Set an empty string for String fields
	        }
	        else if (type.equals(List.class)) {
	         	field.set(entity, new ArrayList<>());
	         }
	         return;
	    }

	    Object cellValue = cell.toString(); // Get the cell value as an Object


	    if (type.equals(double.class)) {
	        if (cellValue != null) {
	            String string = cellValue.toString().trim(); // Trim leading and trailing spaces
	            if (string.equals("*")) {
	                field.set(entity, 0); // Handle asterisk by setting to 0 or another default value
	            } else if (StringUtils.isNotEmpty(string)) {
	                try {
	                    field.set(entity, Double.parseDouble(string)); // Attempt to parse a valid integer
	                } catch (NumberFormatException e) {
	                    // Handle the case where the string is not a valid integer
	                    // You can set a default value or handle the exception as needed
	                    field.set(entity, 0); // Set a default value (0) for invalid values
	                }
	            } else {
	                field.set(entity, 0); // Set a default value (0) for empty cell values
	            }
	        } else {
	            field.set(entity, 0); // Set a default value (0) for null cell values
	        }
	    }
	    else if (type.equals(String.class)) {
	        if (cellValue != null) {
	            field.set(entity, cellValue.toString());
	        } else {
	            field.set(entity, ""); // Set an empty string for null cell values
	        }
	    }
	    else if (type.equals(List.class)) {
	    	List<Integer> toset = new ArrayList<>();
	        if(count <= 6) {
	        	count = count - 1 + 12;
	        }
	        //否则计算今年和明年  
	        else{
	        	count = count - 1;
	        }
	    	for(int i = columnIndex; i < columnIndex + count ; i++){
	    		Cell curCell = row.getCell(i);
	    		String string = curCell.toString();
	            if (StringUtils.isNotEmpty(string)) {
	            	try {
	            		   string = new BigDecimal(string).toPlainString();
	 	                   int index = string.lastIndexOf('.');
	 	                   if (index > 0) {
	 	                     string = string.substring(0, index);
	 	                   }
	 	                   toset.add(Integer.parseInt(string));
		                } catch (NumberFormatException e) {
		                    // Handle the case where the string is not a valid integer
		                    // You can set a default value or handle the exception as needed
		                    toset.add(0);// Set a default value (0) for invalid values
		                 }
	            } else {
	                toset.add(0);
	            }
	    	}
	    	field.set(entity, toset);
	    }
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
	
	public static List<ProductListImportEntity> excel2ProductList(Workbook workbook) throws Exception {
		if (null == workbook) {
			return null;
		}
		List<ProductListImportEntity> entryList = new ArrayList<>();
		try {
			Sheet sheet = workbook.getSheetAt(0);//sheet1
			int rowNum = sheet.getLastRowNum();
			for (int i = 1; i <= rowNum; i++) {//second column
				ProductListImportEntity entity = new ProductListImportEntity();
				Class<ProductListImportEntity> entityClass = ProductListImportEntity.class;
				Field[] fields = entityClass.getDeclaredFields();
				for (Field field : fields) {
			        if (field.isAnnotationPresent(ColumnIndex.class)) {
			            Annotation annotation = field.getAnnotation(ColumnIndex.class);
			            ColumnIndex excelColumnIndex = (ColumnIndex) annotation;
			            int columnIndex = excelColumnIndex.value();
			            if(sheet.getRow(i)!=null) {
			            setProductListField(sheet.getRow(i).getCell(columnIndex), field, entity);}
			        }
			    }
				entryList.add(entity);
			}
		} catch (Exception e) {
			throw e;
		}
		return entryList;
	}
	
	private static void setProductListField(Cell cell, Field field, ProductListImportEntity entity)
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
