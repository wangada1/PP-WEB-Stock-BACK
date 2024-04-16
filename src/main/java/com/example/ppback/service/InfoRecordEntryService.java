package com.example.ppback.service;


import java.util.ArrayList;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;


import java.util.List;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
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
	private MongoTemplate mongoTemplate;
	

	@Override
	public void uploadPara(Workbook workbook, String para, BaseHttpResponse uploadResponse) throws Exception {

		String[] splitted = para.split("-");
		// int year = Integer.parseInt(splitted[0]);
		// int month = Integer.parseInt(splitted[1]);
	    List<InfoRecordImportEntity> importEntities = ExcelUtil.excel2InfoRecord(workbook);
	    List<InfoRecordEntry> InfoRecordEntries = new ArrayList<>();
	    importEntities.forEach(importEntity -> {
	    	InfoRecordEntry info = new InfoRecordEntry();
	        info.setProductNumber(importEntity.getProductNumber());
	        info.setVendor(importEntity.getVendor().replaceFirst("^0+(?!$)", ""));//不要头部的0
	        info.setYearMonth(para);
	        InfoRecordEntries.add(info);
	        }
	        );
	    if (!mongoTemplate.collectionExists("InfoRecordEntry")) {
	        // If the collection doesn't exist, create it (optional)
	        mongoTemplate.createCollection("InfoRecordEntry");
	    }
	    deleteEntriesWithYearMonth(para);
	    InfoRecordEntryRepository.saveAll(InfoRecordEntries);
	}
	
	   
	public void deleteEntriesWithYearMonth(String para) {
		Query query = new Query(Criteria.where("yearMonth").is(para));
		mongoTemplate.remove(query, InfoRecordEntry.class);
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
