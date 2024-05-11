package com.example.ppback.service;

import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.example.ppback.model.DataEntry;
import com.example.ppback.model.DataEntryImportEntity;
import com.example.ppback.repository.DataEntryRepository;
import com.example.ppback.util.ExcelUtil;

@Service
public class DataEntryService implements UploadPara{
	@Autowired
	private DataEntryRepository dataEntryRepository;
	@Autowired
	private VendorPDCLMapper VendorPDCLMapper;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	public DataEntry addDataEntry(DataEntry dataEntry){
		return dataEntryRepository.save(dataEntry);
	}
	public List<DataEntry> findAllEntries(){
		return dataEntryRepository.findAll();
	}

	@Override
	public void uploadPara(Workbook workbook, String para, BaseHttpResponse uploadResponse) throws Exception {
	    List<DataEntryImportEntity> importEntities = ExcelUtil.excel2Entry(workbook);
	    List<DataEntry> dataEntries = new ArrayList<>();
 	    AtomicInteger progress = new AtomicInteger(0);
 	    long startTime = System.currentTimeMillis(); // 记录开始时间
	    importEntities.forEach(importEntity -> {
	        DataEntry info = new DataEntry();
	        info.setProductNumber(importEntity.getProductNumber());
	        info.setPdcl(importEntity.getPdcl());
	        //上传逻辑修改，删除末尾的.0
	        String vendor = importEntity.getVendor();
	        if (vendor.endsWith(".0")) {
	            vendor = vendor.substring(0, vendor.length() - 2);
	        }
	        info.setVendorNumber(vendor);
	        vendor = VendorPDCLMapper.getVendorName(vendor);
	        info.setVendor(vendor);
	        info.setType(importEntity.getType());
	        info.setBusinessUnit(importEntity.getBusinessUnit());
	        info.setProfitCenter(importEntity.getProfitCenter());
	        info.setYearMonth(para);
	        info.setPp0(importEntity.getPp0());
	        info.setPp1(importEntity.getPp1());
	        info.setPp2(importEntity.getPp2());
	        info.setPp3(importEntity.getPp3());
	        info.setPp4(importEntity.getPp4());
	        info.setPp5(importEntity.getPp5());
	        info.setPp6(importEntity.getPp6());
	        info.setPp7(importEntity.getPp7());
	        info.setPp8(importEntity.getPp8());
	        info.setPp9(importEntity.getPp9());
	        info.setPp10(importEntity.getPp10());
	        info.setPp11(importEntity.getPp11());
	        info.setPp12(importEntity.getPp12());
	        info.setPp13(importEntity.getPp13());
	        info.setPp14(importEntity.getPp14());
	        info.setPp15(importEntity.getPp15());
	        info.setPp16(importEntity.getPp16());
	        info.setPp17(importEntity.getPp17());
	        info.setPp18(importEntity.getPp18());
	        
	        info.setTb0(importEntity.getTb0());
	        info.setTb1(importEntity.getTb1());
	        info.setTb2(importEntity.getTb2());
	        info.setTb3(importEntity.getTb3());
	        info.setTb4(importEntity.getTb4());
	        info.setTb5(importEntity.getTb5());
	        info.setTb6(importEntity.getTb6());
	        info.setTb7(importEntity.getTb7());
	        info.setTb8(importEntity.getTb8());
	        info.setTb9(importEntity.getTb9());
	        info.setTb10(importEntity.getTb10());
	        info.setTb11(importEntity.getTb11());
	        info.setTb12(importEntity.getTb12());
	        info.setTb13(importEntity.getTb13());
	        info.setTb14(importEntity.getTb14());
	        info.setTb15(importEntity.getTb15());
	        info.setTb16(importEntity.getTb16());
	        info.setTb17(importEntity.getTb17());
	        info.setTb18(importEntity.getTb18());
	        info.setTb19(importEntity.getTb19());
	        dataEntries.add(info);
	        int currentProgress = progress.incrementAndGet();
	         long endTime = System.currentTimeMillis(); // 记录方法结束执行的时间
	         long duration = endTime - startTime; // 计算方法执行时间
	         System.out.println(currentProgress + "/" + importEntities.size()+";"+"average time:"+duration/currentProgress + " mm " + " totol time：" + duration/1000 + " s" );
		        
        }
        );
    System.out.println("Please wait while checking data consistency and establishing a search index.");
	    dataEntryRepository.saveAll(dataEntries);
	}
/*	public String getTypeByPN(String PN) {
	    String sql = "SELECT TOP 1 type FROM data_entry WHERE product_number = ?";
	    List<String> types = jdbcTemplate.query(sql, new Object[] { PN }, (resultSet, rowNum) -> {
	        return resultSet.getString("type");
	    });
	    if (types.isEmpty()) {
	        return null;
	    } else {
	        return types.get(0);
	    }
	}*/
	public void deleteByMonth(String yearmonth) {
	    String sql = "DELETE FROM data_entry WHERE year_month = '" + yearmonth + "'";
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
		return "dataEntry";
	}
	
	@Override
	public boolean isFileValid(Workbook workbook) {
		// TODO Auto-generated method stub
		return true;
	}

}
