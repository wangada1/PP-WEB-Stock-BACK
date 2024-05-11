package com.example.ppback.service;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.example.ppback.model.InfoRecordEntry;
import com.example.ppback.model.InfoRecordImportEntity;
import com.example.ppback.repository.InfoRecordEntryRepository;
import com.example.ppback.util.ExcelUtil;



@Service
public class InfoRecordEntryService implements UploadPara{
	@Autowired
	private InfoRecordEntryRepository InfoRecordEntryRepository;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Override
	public void uploadPara(Workbook workbook, String para, BaseHttpResponse uploadResponse) throws Exception {

		String[] splitted = para.split("-");
		// int year = Integer.parseInt(splitted[0]);
		// int month = Integer.parseInt(splitted[1]);
	    List<InfoRecordImportEntity> importEntities = ExcelUtil.excel2InfoRecord(workbook);
	    List<InfoRecordEntry> InfoRecordEntries = new ArrayList<>();
	    AtomicInteger progress = new AtomicInteger(0);
 	    long startTime = System.currentTimeMillis(); // 记录开始时间
	    importEntities.forEach(importEntity -> {
	    	InfoRecordEntry info = new InfoRecordEntry();
	        info.setProductNumber(importEntity.getProductNumber());
	        String vendor = importEntity.getVendor().replaceFirst("^0+(?!$)", "");//不要头部的0
	        VendorPDCLMapper VendorMapper = new VendorPDCLMapper();
	        vendor = VendorMapper.getVendorName(vendor);
	        info.setVendor(vendor);
	        InfoRecordEntries.add(info);
	        int currentProgress = progress.incrementAndGet();
	         long endTime = System.currentTimeMillis(); // 记录方法结束执行的时间
	         long duration = endTime - startTime; // 计算方法执行时间
	         System.out.println(currentProgress + "/" + importEntities.size()+";"+"average time:"+duration/currentProgress + " mm " + " totol time：" + duration/1000 + " s" );
		        
        }
        );
    System.out.println("Please wait while checking data consistency and establishing a search index.It may take long time.");
	    InfoRecordEntryRepository.saveAll(InfoRecordEntries);
	}
	
	public String getVendorByPN(String PN) {
	    String sql = "SELECT TOP 1 vendor FROM info_record_entry WHERE product_number = ?";
	    List<String> vendors = jdbcTemplate.query(sql, new Object[] { PN }, (resultSet, rowNum) -> {
	        return resultSet.getString("vendor");
	    });
	    if (vendors.isEmpty()) {
	        return null;
	    } else {
	        return vendors.get(0);
	    }
	}
	public void deleteByMonth() {
	    String sql = "DELETE FROM info_record_entry WHERE 1 = 1 ";
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
		return "InfoRecordEntry";
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
