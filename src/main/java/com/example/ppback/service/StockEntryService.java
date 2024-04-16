package com.example.ppback.service;


import java.util.ArrayList;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;


import java.util.List;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ppback.model.StockEntry;
import com.example.ppback.model.StockImportEntity;
import com.example.ppback.repository.StockEntryRepository;
import com.example.ppback.util.ExcelUtil;



@Service
public class StockEntryService implements UploadPara{
	@Autowired
	private StockEntryRepository StockEntryRepository;
	@Autowired
	private MongoTemplate mongoTemplate;
	

	@Override
	public void uploadPara(Workbook workbook, String para, BaseHttpResponse uploadResponse) throws Exception {

		String[] splitted = para.split("-");
		// int year = Integer.parseInt(splitted[0]);
		// int month = Integer.parseInt(splitted[1]);
	    List<StockImportEntity> importEntities = ExcelUtil.excel2Stock(workbook);
	    List<StockEntry> StockEntries = new ArrayList<>();
	    
	    importEntities.forEach(importEntity -> {
	    	StockEntry info = new StockEntry();
	        info.setProductNumber(importEntity.getProductNumber());
	        info.setPDCL(importEntity.getPDCL());
	        info.setStockInfo0(importEntity.getStockInfo0());
	        info.setStockInfo1(importEntity.getStockInfo1());
	        info.setStockInfo2(importEntity.getStockInfo2());
	        info.setStockInfo3(importEntity.getStockInfo3());
	        info.setStockInfo4(importEntity.getStockInfo4());
	        info.setStockInfo5(importEntity.getStockInfo5());
	        info.setStockInfo6(importEntity.getStockInfo6());
	        info.setStockInfo7(importEntity.getStockInfo7());
	        info.setStockInfo8(importEntity.getStockInfo8());
	        info.setStockInfo9(importEntity.getStockInfo9());
	        info.setStockInfo10(importEntity.getStockInfo10());
	        info.setStockInfo11(importEntity.getStockInfo11());
	        info.setStockInfo12(importEntity.getStockInfo12());
	        info.setStockInfo13(importEntity.getStockInfo13());
	        info.setStockInfo14(importEntity.getStockInfo14());
	        info.setStockInfo15(importEntity.getStockInfo15());
	        info.setStockInfo16(importEntity.getStockInfo16());
	        info.setStockInfo17(importEntity.getStockInfo17());//现在这个值不对，都为0！
	        info.setYearMonth(para);
	      //Type根据material到PPDATA中进行匹配，根据PN到DATA中匹配productnumber,输出Type
	        //String PN = importEntity.getProductNumber();
	         //String Type = MongoDBService2.findTypeByPN(PN);
	         //info.setType(Type==null?"":Type);
	         StockEntries.add(info);
	        }
	        );
	    for (StockEntry entry : StockEntries) {
	        // 这里可以对每个 StockEntry 对象进行处理
	    	String PN = entry.getProductNumber();
	    	String VEN = MongoDBService.findVendorByPN(PN);
	    	entry.setVendor(VEN==null?"":VEN);
	    }
	
		        
	    if (!mongoTemplate.collectionExists("StockEntry")) {
	        // If the collection doesn't exist, create it (optional)
	        mongoTemplate.createCollection("StockEntry");
	    }
	    deleteEntriesWithYearMonth(para);
	    StockEntryRepository.saveAll(StockEntries);
	}
	
	   
	public void deleteEntriesWithYearMonth(String para) {
		Query query = new Query(Criteria.where("yearMonth").is(para));
		mongoTemplate.remove(query, StockEntry.class);
	}


	@Override
	public String getUploaderType() {
		// TODO Auto-generated method stub
		return "StockEntry";
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