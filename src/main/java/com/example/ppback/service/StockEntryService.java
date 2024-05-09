package com.example.ppback.service;


import java.util.ArrayList;


import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

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
	private DataEntryService dataEntryService;
	@Autowired
	private InfoRecordEntryService infoRecordEntryService;

	@Override
	public void uploadPara(Workbook workbook, String para, BaseHttpResponse uploadResponse) throws Exception {

		String[] splitted = para.split("-");
		// int year = Integer.parseInt(splitted[0]);
		// int month = Integer.parseInt(splitted[1]);
	    List<StockImportEntity> importEntities = ExcelUtil.excel2Stock(workbook);
	    List<StockEntry> StockEntries = new ArrayList<>();
	    int entityCount = importEntities.size();
 	    AtomicInteger progress = new AtomicInteger(0);
 	    long startTime = System.currentTimeMillis(); // 记录开始时间
	    importEntities.forEach(importEntity -> {
	    	StockEntry info = new StockEntry();
	        info.setProductNumber(importEntity.getProductNumber());
	        info.setPDCL(importEntity.getPDCL());
	        info.setStockInfo0(importEntity.getStockInfo0());//如果月份数小于7，则从去年1月开始，否则从今年1月开始，以上月截至
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
	        info.setStockInfo17(importEntity.getStockInfo17());
	        info.setYearMonth(para);
	      //Type根据material到PPDATA中进行匹配，根据PN到DATA中匹配productnumber,输出Type
	        String PN = importEntity.getProductNumber();
	        String VEN = infoRecordEntryService.getVendorByPN(PN);
	         String Type = dataEntryService.getTypeByPN(PN);
	         info.setVendor(VEN==null?"":VEN);
	         info.setType(Type==null?"":Type);
	         StockEntries.add(info);
	         int currentProgress = progress.incrementAndGet();
	         long endTime = System.currentTimeMillis(); // 记录方法结束执行的时间
	         long duration = endTime - startTime; // 计算方法执行时间
	        System.out.println("当前进度: " + currentProgress + "/" + importEntities.size()+";"+"单次平均时间："+duration/currentProgress + " 毫秒" + " 总时间：" + duration/1000 + " 秒" );
	        
	        }
	        );
	    System.out.println("检查数据一致性和建立查找索引中，请稍等");
		 StockEntryRepository.saveAll(StockEntries);       
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
