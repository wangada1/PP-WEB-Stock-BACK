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
import com.example.ppback.model.GrDataEntry;
import com.example.ppback.model.GrEntryImportEntity;
import com.example.ppback.repository.GrDataEntryRepository;
import com.example.ppback.util.ExcelUtil;



@Service
public class GrEntryService implements UploadPara{
	@Autowired
	private GrDataEntryRepository dataEntryRepository;
	@Autowired
	private MongoTemplate mongoTemplate;
	

	@Override
	public void uploadPara(Workbook workbook, String para, BaseHttpResponse uploadResponse) throws Exception {

		String[] splitted = para.split("-");
		int year = Integer.parseInt(splitted[0]);
		int month = Integer.parseInt(splitted[1]);
	    List<GrEntryImportEntity> importEntities = ExcelUtil.excel2Gr(workbook, month);
	    List<GrDataEntry> dataEntries = new ArrayList<>();
	    importEntities.forEach(importEntity -> {
	        GrDataEntry info = new GrDataEntry();
	        info.setProductNumber(importEntity.getProductNumber());
	        info.setPdcl(importEntity.getPdcl());
	        info.setBusinessUnit(importEntity.getBusinessUnit());
	        info.setProfitCenter(importEntity.getProfitCenter());
	        info.setYearMonth(para);
	        info.setGrInfo(importEntity.getGrList());
	        dataEntries.add(info);

	    });
	    if (!mongoTemplate.collectionExists("grDataEntry")) {
	        // If the collection doesn't exist, create it (optional)
	        mongoTemplate.createCollection("grDataEntry");
	    }
	    deleteEntriesWithYearMonth(para);
	    dataEntryRepository.saveAll(dataEntries);
	}
	
	   public List<Integer> getSumByIndexForMdltPN(String pdcl, String yearMonth) {
		    MongoCollection<Document> collection = mongoTemplate.getCollection("grDataEntry");;
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
	                    new Document("$group", new Document("_id", null).append("sum", new Document("$sum", new Document("$arrayElemAt", Arrays.asList("$grInfo", i)))))
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
		mongoTemplate.remove(query, GrDataEntry.class);
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
