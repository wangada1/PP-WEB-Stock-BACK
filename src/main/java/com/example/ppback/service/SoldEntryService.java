package com.example.ppback.service;
import java.util.ArrayList;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ppback.model.DataEntry;
import com.example.ppback.model.SoldDataEntry;
import com.example.ppback.model.SoldEntryImportEntity;
import com.example.ppback.repository.GrDataEntryRepository;
import com.example.ppback.repository.SoldDataEntryRepository;
import com.example.ppback.util.ExcelUtil;
import com.example.ppback.service.MongoDBService;



@Service
public class SoldEntryService implements UploadPara{
	@Autowired
	private SoldDataEntryRepository soldDataEntryRepository;
	@Autowired
	private DataEntryService dataEntryService;
	@Autowired
	private InfoRecordEntryService infoRecordEntryService;
	@Autowired
	private MaterialMasterEntryService materialMasterEntryService;
	@Override
	public void uploadPara(Workbook workbook, String para, BaseHttpResponse uploadResponse) throws Exception {

		String[] splitted = para.split("-");
		int year = Integer.parseInt(splitted[0]);
		int month = Integer.parseInt(splitted[1]);
	    List<SoldEntryImportEntity> importEntities = ExcelUtil.excel2Sold(workbook, month);
	    List<SoldDataEntry> dataEntries = new ArrayList<>();
	    int entityCount = importEntities.size();
	    AtomicInteger progress = new AtomicInteger(0);
	    long startTime = System.currentTimeMillis(); // 记录开始时间
	    importEntities.forEach(importEntity -> {
	        SoldDataEntry info = new SoldDataEntry();
	        info.setProductNumber(importEntity.getProductNumber());
	        //Vendor根据material到info中进行匹配，PDCL根据material到master中匹配，Type根据material到PP中匹配
	        String PN = importEntity.getProductNumber();
	        String VEN = infoRecordEntryService.getVendorByPN(PN);
	         String Type = dataEntryService.getTypeByPN(PN);
	         info.setVendor(VEN==null?"":VEN);
	         info.setType(Type==null?"":Type);
	        String PDCL = materialMasterEntryService.getPDCLByPN(PN);
	        info.setPdcl(PDCL==null?"":PDCL);
	        info.setYearMonth(para);
	        info.setSoldInfo0(importEntity.getSoldInfo0());
	        info.setSoldInfo1(importEntity.getSoldInfo1());
	        info.setSoldInfo2(importEntity.getSoldInfo2());
	        info.setSoldInfo3(importEntity.getSoldInfo3());
	        info.setSoldInfo4(importEntity.getSoldInfo4());
	        info.setSoldInfo5(importEntity.getSoldInfo5());
	        info.setSoldInfo6(importEntity.getSoldInfo6());
	        info.setSoldInfo7(importEntity.getSoldInfo7());
	        info.setSoldInfo8(importEntity.getSoldInfo8());
	        info.setSoldInfo9(importEntity.getSoldInfo9());
	        info.setSoldInfo10(importEntity.getSoldInfo10());
	        info.setSoldInfo11(importEntity.getSoldInfo11());
	        info.setSoldInfo12(importEntity.getSoldInfo12());
	        info.setSoldInfo13(importEntity.getSoldInfo13());
	        info.setSoldInfo14(importEntity.getSoldInfo14());
	        info.setSoldInfo15(importEntity.getSoldInfo15());
	        info.setSoldInfo16(importEntity.getSoldInfo16());
	        info.setSoldInfo17(importEntity.getSoldInfo17());
	        dataEntries.add(info);
	        int currentProgress = progress.incrementAndGet();
	         long endTime = System.currentTimeMillis(); // 记录方法结束执行的时间
	         long duration = endTime - startTime; // 计算方法执行时间
	        System.out.println("当前进度: " + currentProgress + "/" + importEntities.size()+";"+"单次平均时间："+duration/currentProgress + " 毫秒" + " 总时间：" + duration/1000 + " 秒" );
	        
	        }
	        );
	    System.out.println("检查数据一致性和建立查找索引中，请稍等");
	    soldDataEntryRepository.saveAll(dataEntries);

	}
	

	@Override
	public String getUploaderType() {
		// TODO Auto-generated method stub
		return "soldDataEntry";
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