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

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

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
import com.example.ppback.service.MongoDBService;



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
	    int entityCount = importEntities.size();
	    AtomicInteger progress = new AtomicInteger(0);
	    long startTime = System.currentTimeMillis(); // 记录开始时间
	    importEntities.forEach(importEntity -> {
	        SoldDataEntry info = new SoldDataEntry();
	        info.setProductNumber(importEntity.getProductNumber());
	        //Vendor根据material到info中进行匹配，PDCL根据material到master中匹配，Type根据material到PP中匹配
	        String PN = importEntity.getProductNumber();
	        String VEN = MongoDBService.findVendorByPN(PN);
	        String TYPE = MongoDBService2.findTypeByPN(PN);
	        String PDCL = MongoDBService3.findPDCLByPN(PN);
	        info.setVendor(VEN==null?"":VEN);
	        info.setType(TYPE==null?"":TYPE);
	        info.setPdcl(PDCL==null?"":PDCL);
	        info.setYearMonth(para);
	        info.setSoldInfo(importEntity.getSoldList());
	        List<Integer> soldList = importEntity.getSoldList();
	        int size = soldList.size();
	        info.setSoldInfo0(importEntity.getSoldList().get(0));
	        for (int k = 0; k < size; k++) {
	            double soldValue = soldList.get(k);
	            switch (k) {
	                case 0: info.setSoldInfo0(soldValue); break;
	                case 1: info.setSoldInfo1(soldValue); break;
	                case 2: info.setSoldInfo2(soldValue); break;
	                case 3: info.setSoldInfo3(soldValue); break;
	                case 4: info.setSoldInfo4(soldValue); break;
	                case 5: info.setSoldInfo5(soldValue); break;
	                case 6: info.setSoldInfo6(soldValue); break;
	                case 7: info.setSoldInfo7(soldValue); break;
	                case 8: info.setSoldInfo8(soldValue); break;
	                case 9: info.setSoldInfo9(soldValue); break;
	                case 10: info.setSoldInfo10(soldValue); break;
	                case 11: info.setSoldInfo11(soldValue); break;
	                case 12: info.setSoldInfo12(soldValue); break;
	                case 13: info.setSoldInfo13(soldValue); break;
	                case 14: info.setSoldInfo14(soldValue); break;
	                case 15: info.setSoldInfo15(soldValue); break;
	                case 16: info.setSoldInfo16(soldValue); break;
	                case 17: info.setSoldInfo17(soldValue); break;
	                default: break;
	            }
	        }
	        dataEntries.add(info);
	        int currentProgress = progress.incrementAndGet();
	         long endTime = System.currentTimeMillis(); // 记录方法结束执行的时间
	         long duration = endTime - startTime; // 计算方法执行时间
	        System.out.println("当前进度: " + currentProgress + "/" + importEntities.size()+";"+"单次平均时间："+duration/currentProgress + " 毫秒");
	        

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