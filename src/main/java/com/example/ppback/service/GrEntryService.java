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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
	        String vendor = importEntity.getVendor();
	        Map<String, String> vendorMap = new HashMap<>();
	        vendorMap.put("4770", "HorP");
	        vendorMap.put("54646", "HoP2");
	        vendorMap.put("58557", "EcP");
	        vendorMap.put("132994", "LoP1");
	        vendorMap.put("134104", "DCHK");
	        vendorMap.put("134418", "DCEM");
	        vendorMap.put("134775", "DCIT");
	        vendorMap.put("136236", "DCUS");
	        vendorMap.put("136255", "DCCZ");
	        vendorMap.put("136715", "DCKR");
	        vendorMap.put("137445", "AfP");
	        vendorMap.put("138271", "Didactic");
	        vendorMap.put("190091", "DCAT");
	        vendorMap.put("190093", "AhmP");
	        vendorMap.put("190095", "DCFI");
	        vendorMap.put("190136", "PkP SVC");
	        vendorMap.put("190142", "WujP SVC");
	        vendorMap.put("190890", "NuP2");
	        vendorMap.put("195301", "VxP");
	        vendorMap.put("197635", "FniP");
	        vendorMap.put("362753", "DCBR");
	        vendorMap.put("370877", "TscP");
	        vendorMap.put("371366", "PkP");
	        vendorMap.put("372132", "Lohr SVC");
	        vendorMap.put("377434", "3RD");
	        vendorMap.put("381479", "DCOC");
	        vendorMap.put("638788", "WujP");
	        vendorMap.put("651165", "HejP");
	        vendorMap.put("97032862", "DCOC");
	        vendorMap.put("97032864", "DCOC");
	        vendorMap.put("97032868", "DCOC");
	        vendorMap.put("97081226", "DCIN");
	        vendorMap.put("97155076", "MllP");
	        vendorMap.put("97323689", "NuP2 MA SVC");
	        vendorMap.put("97415951", "GleP");
	        vendorMap.put("97469609", "BuP2");
	        vendorMap.put("97474483", "3RD");
	        vendorMap.put("97505750", "3RD");
	        vendorMap.put("97510160", "3RD");
	        vendorMap.put("97526489", "3RD");
	        vendor = vendorMap.getOrDefault(vendor,"");
	        info.setVendor(vendor);
	        info.setProfitCenter(importEntity.getProfitCenter());
	        info.setType(importEntity.getType());
	        info.setYearMonth(para);
	        info.setGrInfo(importEntity.getGrList());
	        dataEntries.add(info);
	        List<Integer> grList = importEntity.getGrList();
	        int size = grList.size();
	        info.setGrInfo0(importEntity.getGrList().get(0));
	        for (int k = 0; k < size; k++) {
	        	double grValue = grList.get(k);
	        	switch (k) {
	        	case 0: info.setGrInfo0(grValue); break;
	            case 1: info.setGrInfo1(grValue); break;
	            case 2: info.setGrInfo2(grValue); break;
	            case 3: info.setGrInfo3(grValue); break;
	            case 4: info.setGrInfo4(grValue); break;
	            case 5: info.setGrInfo5(grValue); break;
	            case 6: info.setGrInfo6(grValue); break;
	            case 7: info.setGrInfo7(grValue); break;
	            case 8: info.setGrInfo8(grValue); break;
	            case 9: info.setGrInfo9(grValue); break;
	            case 10: info.setGrInfo10(grValue); break;
	            case 11: info.setGrInfo11(grValue); break;
	            case 12: info.setGrInfo12(grValue); break;
	            case 13: info.setGrInfo13(grValue); break;
	            case 14: info.setGrInfo14(grValue); break;
	            case 15: info.setGrInfo15(grValue); break;
	            case 16: info.setGrInfo16(grValue); break;
	            case 17: info.setGrInfo17(grValue); break;
	            default: break;
	        }
	    }
	        }
	        );
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
