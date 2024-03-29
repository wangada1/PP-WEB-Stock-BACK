package com.example.ppback.service;
import java.util.ArrayList;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.ArrayOperators;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.aggregation.UnwindOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import java.util.List;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import com.example.ppback.model.DataEntry;
import com.example.ppback.model.SoldDataEntry;
import com.example.ppback.model.SoldEntryImportEntity;
import com.example.ppback.repository.GrDataEntryRepository;
import com.example.ppback.repository.SoldDataEntryRepository;
import com.example.ppback.util.ExcelUtil;



@Service
public class SoldEntryService implements UploadPara{
	@Autowired
	private SoldDataEntryRepository soldDataEntryRepository;
	@Autowired
	private MongoTemplate mongoTemplate;
	

	@Override
	public void uploadPara(Workbook workbook, String para, BaseHttpResponse uploadResponse) throws Exception {

		String[] splitted = para.split("-");
		int year = Integer.parseInt(splitted[0]);
		int month = Integer.parseInt(splitted[1]);
	    List<SoldEntryImportEntity> importEntities = ExcelUtil.excel2Sold(workbook, month);
	    List<SoldDataEntry> dataEntries = new ArrayList<>();
	    
	    importEntities.forEach(importEntity -> {
	        SoldDataEntry info = new SoldDataEntry();
	        info.setProductNumber(importEntity.getProductNumber());
	        info.setPdcl(importEntity.getPdcl());
	        info.setBusinessUnit(importEntity.getBusinessUnit());
	        info.setProfitCenter(importEntity.getProfitCenter());
	        info.setYearMonth(para);
	        info.setSoldInfo(importEntity.getSoldList());
	        dataEntries.add(info);
	    });
	    
	    if (!mongoTemplate.collectionExists("soldDataEntry")) {
	        // If the collection doesn't exist, create it (optional)
	        mongoTemplate.createCollection("soldDataEntry");
	    }
	    deleteEntriesWithYearMonth(para);
	    soldDataEntryRepository.saveAll(dataEntries);
	}
	
	public List<Integer> getSumByIndexForMdltPN(String pdcl, String yearMonth) {
		MongoCollection<Document> collection = mongoTemplate.getCollection("soldDataEntry");
	    int month = Integer.parseInt(yearMonth.split("-")[1]);
	    int n = 0;
	    //如果为1-6月，计算今年和去年
	    if(month <= 6) {
	    	n = month - 1 + 12;
	    }
	    //否则计算今年和明年
	    else{
	    	n = month - 1;
	    }
	    List<Integer> sums = new ArrayList<>();
	    for (int i = 0; i < n; i++) {
	        Document sumResult = collection.aggregate(Arrays.asList(
	                new Document("$match", new Document("pdcl", pdcl).append("yearMonth", yearMonth)),
	                new Document("$group", new Document("_id", null).append("sum", new Document("$sum", new Document("$arrayElemAt", Arrays.asList("$soldInfo", i)))))
	        )).first();

	        if (sumResult != null) {
	            sums.add(sumResult.getInteger("sum", 0));
	        } else {
	            sums.add(0);
	        }
	    }
	    return sums;
	}
	   
	public void deleteEntriesWithYearMonth(String para) {
		Query query = new Query(Criteria.where("yearMonth").is(para));
		mongoTemplate.remove(query, SoldDataEntry.class);
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