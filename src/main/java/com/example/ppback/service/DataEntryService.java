package com.example.ppback.service;

import java.awt.print.Printable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.example.ppback.model.DataEntry;
import com.example.ppback.model.DataEntryImportEntity;
import com.example.ppback.repository.DataEntryRepository;
import com.example.ppback.util.*;



@Service
public class DataEntryService implements UploadPara{
	@Autowired
	private DataEntryRepository dataEntryRepository;
	@Autowired
	private MongoTemplate mongoTemplate;
	
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

	    importEntities.forEach(importEntity -> {
	        DataEntry info = new DataEntry();
	        info.setProductNumber(importEntity.getProductNumber());
	        info.setMdltPN(importEntity.getMdltPN());
	        info.setVendor(importEntity.getVendor());
	        info.setYearMonth(para);
	        // Assuming these methods correctly retrieve the lists you need
	        info.setTbInfo(importEntity.getTbValues());
	        info.setPpInfo(importEntity.getPpValues());
	        info.setGrInfo(importEntity.getGrValues());
	        dataEntries.add(info);
	    });
	    if (!mongoTemplate.collectionExists("dataEntry")) {
	        // If the collection doesn't exist, create it (optional)
	        mongoTemplate.createCollection("dataEntry");
	    }
	    deleteEntriesWithYearMonth(para);
	    dataEntryRepository.saveAll(dataEntries);
	}
	
	public void deleteEntriesWithYearMonth(String para) {
	    Query query = new Query(Criteria.where("yearMonth").is(para));
	    mongoTemplate.remove(query, DataEntry.class);
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
