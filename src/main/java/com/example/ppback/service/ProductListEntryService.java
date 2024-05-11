package com.example.ppback.service;


import java.util.ArrayList;


import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.example.ppback.model.ProductListEntry;
import com.example.ppback.model.ProductListImportEntity;
import com.example.ppback.repository.ProductListEntryRepository;
import com.example.ppback.util.ExcelUtil;



@Service
public class ProductListEntryService implements UploadPara{
	@Autowired
	private ProductListEntryRepository ProductListEntryRepository;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Override
	public void uploadPara(Workbook workbook, String para, BaseHttpResponse uploadResponse) throws Exception {

		String[] splitted = para.split("-");
		// int year = Integer.parseInt(splitted[0]);
		// int month = Integer.parseInt(splitted[1]);
	    List<ProductListImportEntity> importEntities = ExcelUtil.excel2ProductList(workbook);
	    List<ProductListEntry> ProductListEntries = new ArrayList<>();
	    AtomicInteger progress = new AtomicInteger(0);
 	    long startTime = System.currentTimeMillis(); // 记录开始时间
	    importEntities.forEach(importEntity -> {
	    	ProductListEntry info = new ProductListEntry();
	        info.setProductNumber(importEntity.getProductNumber());
	        info.setType(importEntity.getType());
	        info.setYearMonth(para);
	        ProductListEntries.add(info);
	        int currentProgress = progress.incrementAndGet();
	         long endTime = System.currentTimeMillis(); // 记录方法结束执行的时间
	         long duration = endTime - startTime; // 计算方法执行时间
	        System.out.println(currentProgress + "/" + importEntities.size()+";"+"average time:"+duration/currentProgress + " mm " + " totol time：" + duration/1000 + " s" );
	        
	        }
	        );
	    System.out.println("Please wait while checking data consistency and establishing a search index.");
	    ProductListEntryRepository.saveAll(ProductListEntries);
	}
	
	public String getTypeByPN(String PN) {

	    String sql = "SELECT TOP 1 type FROM product_list_entry WHERE product_number = ?";
	    List<String> types = jdbcTemplate.query(sql, new Object[] { PN }, (resultSet, rowNum) -> {
	        return resultSet.getString("type");
	    });
	    if (types.isEmpty()) {
	        return null;
	    } else {
	        return types.get(0);
	    }
	}
	public void deleteByMonth(String yearmonth) {
	    String sql = "DELETE FROM product_list_entry WHERE year_month = '" + yearmonth + "'";
	    try {
	    	 int rowsAffected = jdbcTemplate.update(sql);
        } catch (DataAccessException e) {
            // 处理数据访问异常
            e.printStackTrace();
        }
	    
	}   
	@Override
	public String getUploaderType() {
		// TODO Auto-generated method stub
		return "productListEntry";
	}
	@Override
	public boolean isFileValid(Workbook workbook) {
		// TODO Auto-generated method stub
		return true;
	}
	
	private static class SumResult {
        private List<Integer> kks;

        public List<Integer> getValues() {
            return kks;
        }

        public int getSumIndex(int index) {
            return kks.get(index);
        }
    }

}
