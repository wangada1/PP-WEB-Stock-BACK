package com.example.ppback.service;


import java.util.ArrayList;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.example.ppback.model.GrDataEntry;
import com.example.ppback.model.GrEntryImportEntity;
import com.example.ppback.repository.GrDataEntryRepository;
import com.example.ppback.util.ExcelUtil;



@Service
public class GrEntryService implements UploadPara{
	@Autowired
	private GrDataEntryRepository dataEntryRepository;
	@Autowired
	private ProductListEntryService dataEntryService;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Override
	public void uploadPara(Workbook workbook, String para, BaseHttpResponse uploadResponse) throws Exception {

		String[] splitted = para.split("-");
		int year = Integer.parseInt(splitted[0]);
		int month = Integer.parseInt(splitted[1]);
	    List<GrEntryImportEntity> importEntities = ExcelUtil.excel2Gr(workbook, month);
	    List<GrDataEntry> dataEntries = new ArrayList<>();
	    int entityCount = importEntities.size();
 	    AtomicInteger progress = new AtomicInteger(0);
 	    long startTime = System.currentTimeMillis(); // 记录开始时间
	    importEntities.forEach(importEntity -> {
	        GrDataEntry info = new GrDataEntry();
	        info.setProductNumber(importEntity.getProductNumber());
	        info.setPdcl(importEntity.getPdcl());
	        info.setBusinessUnit(importEntity.getBusinessUnit());
	        String vendor = importEntity.getVendor();
	        info.setVendorNumber(vendor);
	        VendorPDCLMapper VendorMapper = new VendorPDCLMapper();
	        vendor = VendorMapper.getVendorName(vendor);
	        info.setVendor(vendor);
	        info.setProfitCenter(importEntity.getProfitCenter());
	      //Type根据material到PPDATA中进行匹配，根据PN到DATA中匹配productnumber
	        String PN = importEntity.getProductNumber();
	        String Type = dataEntryService.getTypeByPN(PN);
	        info.setType(Type==null?"":Type);
	        info.setYearMonth(para);
	        info.setGrInfo0(importEntity.getGrInfo0());
	        info.setGrInfo1(importEntity.getGrInfo1());
	        info.setGrInfo2(importEntity.getGrInfo2());
	        info.setGrInfo3(importEntity.getGrInfo3());
	        info.setGrInfo4(importEntity.getGrInfo4());
	        info.setGrInfo5(importEntity.getGrInfo5());
	        info.setGrInfo6(importEntity.getGrInfo6());
	        info.setGrInfo7(importEntity.getGrInfo7());
	        info.setGrInfo8(importEntity.getGrInfo8());
	        info.setGrInfo9(importEntity.getGrInfo9());
	        info.setGrInfo10(importEntity.getGrInfo10());
	        info.setGrInfo11(importEntity.getGrInfo11());
	        info.setGrInfo12(importEntity.getGrInfo12());
	        info.setGrInfo13(importEntity.getGrInfo13());
	        info.setGrInfo14(importEntity.getGrInfo14());
	        info.setGrInfo15(importEntity.getGrInfo15());
	        info.setGrInfo16(importEntity.getGrInfo16());
	        info.setGrInfo17(importEntity.getGrInfo17());

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
	public void deleteByMonth(String yearmonth) {
	    String sql = "DELETE FROM gr_data_entry WHERE year_month = '" + yearmonth + "'";
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
		return "grDataEntry";
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
