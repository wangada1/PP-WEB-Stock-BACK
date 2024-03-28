package com.example.ppback.service;

import java.io.Console;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.Fields;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.aggregation.UnwindOperation;
import org.springframework.data.mongodb.core.aggregation.AccumulatorOperators;
import org.springframework.data.mongodb.core.aggregation.ArrayOperators;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.xmlbeans.impl.xb.xsdschema.TotalDigitsDocument.TotalDigits;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.example.ppback.model.DataEntry;
import com.example.ppback.model.DataEntryImportEntity;
import com.example.ppback.model.PPByMonthEntry;
import com.example.ppback.model.PPByQuarterEntry;
import com.example.ppback.model.PPByQuarterPercent;
import com.example.ppback.model.PPPercentageCompare;
import com.example.ppback.model.TBByMonthEntry;
import com.example.ppback.model.TBByQuarterEntry;
import com.example.ppback.model.TBByQuarterPercent;
import com.example.ppback.model.TBPercentageCompare;
import com.example.ppback.repository.DataEntryRepository;
import com.example.ppback.repository.PPByMonthEntryRepository;
import com.example.ppback.repository.PPByQuarterEntryRepository;
import com.example.ppback.repository.PPByQuarterPercentRepository;
import com.example.ppback.repository.PPPercentageCompareRepository;
import com.example.ppback.repository.TBByMonthEntryRepository;
import com.example.ppback.repository.TBByQuarterEntryRepository;
import com.example.ppback.repository.TBByQuarterPercentRepository;
import com.example.ppback.repository.TBPercentageCompareRepository;
import com.example.ppback.util.ExcelUtil;
import com.mongodb.BasicDBObject;
import net.bytebuddy.asm.Advice.OffsetMapping.Sort;


@Service
public class DataEntryService implements UploadPara{
	@Autowired
	private DataEntryRepository dataEntryRepository;
	@Autowired
	private PPByMonthEntryRepository ppByMonthEntryRepository;
	@Autowired
	private TBByMonthEntryRepository tbByMonthEntryRepository;
	@Autowired
	private PPByQuarterEntryRepository ppByQuarterEntryRepository; 
	@Autowired
	private TBByQuarterEntryRepository tbByQuarterEntryRepository; 
	@Autowired
	private PPByQuarterPercentRepository ppByQuarterPercentRepository; 
	@Autowired
	private TBByQuarterPercentRepository tbByQuarterPercentRepository; 
	@Autowired
	private PPPercentageCompareRepository ppPercentageCompareRepository;
	@Autowired
	private TBPercentageCompareRepository tbPercentageCompareRepository;
	

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
	        info.setPdcl(importEntity.getPdcl());
	        info.setBusinessUnit(importEntity.getBusinessUnit());
	        info.setProfitCenter(importEntity.getProfitCenter());
	        info.setYearMonth(para);
	        info.setPp0(importEntity.getPp0());
	        info.setPp1(importEntity.getPp1());
	        info.setPp2(importEntity.getPp2());
	        info.setPp3(importEntity.getPp3());
	        info.setPp4(importEntity.getPp4());
	        info.setPp5(importEntity.getPp5());
	        info.setPp6(importEntity.getPp6());
	        info.setPp7(importEntity.getPp7());
	        info.setPp8(importEntity.getPp8());
	        info.setPp9(importEntity.getPp9());
	        info.setPp10(importEntity.getPp10());
	        info.setPp11(importEntity.getPp11());
	        info.setPp12(importEntity.getPp12());
	        info.setPp13(importEntity.getPp13());
	        info.setPp14(importEntity.getPp14());
	        info.setPp15(importEntity.getPp15());
	        info.setPp16(importEntity.getPp16());
	        info.setPp17(importEntity.getPp17());
	        info.setPp18(importEntity.getPp18());
	        
	        info.setTb0(importEntity.getTb0());
	        info.setTb1(importEntity.getTb1());
	        info.setTb2(importEntity.getTb2());
	        info.setTb3(importEntity.getTb3());
	        info.setTb4(importEntity.getTb4());
	        info.setTb5(importEntity.getTb5());
	        info.setTb6(importEntity.getTb6());
	        info.setTb7(importEntity.getTb7());
	        info.setTb8(importEntity.getTb8());
	        info.setTb9(importEntity.getTb9());
	        info.setTb10(importEntity.getTb10());
	        info.setTb11(importEntity.getTb11());
	        info.setTb12(importEntity.getTb12());
	        info.setTb13(importEntity.getTb13());
	        info.setTb14(importEntity.getTb14());
	        info.setTb15(importEntity.getTb15());
	        info.setTb16(importEntity.getTb16());
	        info.setTb17(importEntity.getTb17());
	        info.setTb18(importEntity.getTb18());
	        info.setTb19(importEntity.getTb19());
	        
	        // Assuming these methods correctly retrieve the lists you need
	        dataEntries.add(info);
	    });
	    if (!mongoTemplate.collectionExists("dataEntry")) {
	        // If the collection doesn't exist, create it (optional)
	        mongoTemplate.createCollection("dataEntry");
	    }
	    deleteEntriesWithYearMonth(para);
	    dataEntryRepository.saveAll(dataEntries);
	}
	
	public List<Integer> calcTotalCountByProductGroup(String productGroup, String yearMonth) {
	    List<Integer> output = new ArrayList<Integer>();
	    MatchOperation matchOperation = Aggregation.match(
	            Criteria.where("pdcl").is(productGroup)
	                    .and("yearMonth").is(yearMonth)
	    );
	    for(int i = 0; i <= 18; i++) {
	    	  Aggregation aggregation = Aggregation.newAggregation(
	  	            matchOperation,
	  	            Aggregation.group().sum("pp" + i).as("sum" + i),
	  	            Aggregation.project("sum" + i).andExclude("_id")
	  	    );
	    	
	    	 AggregationResults<BasicDBObject> results = mongoTemplate.aggregate(aggregation, "dataEntry", BasicDBObject.class);
	    	 output.add(results.getUniqueMappedResult().getInt("sum" + i));
	    }
	    return output;	  
	}
	
	
	public List<Integer> calcTotalTBCountByProductGroup(String productGroup, String yearMonth) {
	    List<Integer> output = new ArrayList<Integer>();
	    MatchOperation matchOperation = Aggregation.match(
	            Criteria.where("pdcl").is(productGroup)
	                    .and("yearMonth").is(yearMonth)
	    );
	    for(int i = 0; i <= 18; i++) {
	    	  Aggregation aggregation = Aggregation.newAggregation(
	  	            matchOperation,
	  	            Aggregation.group().sum("tb" + i).as("sum" + i),
	  	            Aggregation.project("sum" + i).andExclude("_id")
	  	    );
	    	
	    	 AggregationResults<BasicDBObject> results = mongoTemplate.aggregate(aggregation, "dataEntry", BasicDBObject.class);
	    	 output.add(results.getUniqueMappedResult().getInt("sum" + i));
	    }
	    return output;	  
	}
	
	public BaseHttpResponse<List<Integer>> getTotalTBCountByProductGroup(String productGroup, String yearMonth) {
		BaseHttpResponse<List<Integer>> resp = new BaseHttpResponse<>();//criteria是一个查询类
	    Criteria criteria = Criteria.where("pdcl");
	    criteria = criteria.is(productGroup);
	    criteria = criteria.and("yearMonth");
	    criteria = criteria.is(yearMonth);
	    Query query = new Query(criteria);
	    TBByMonthEntry tbCurByMonthEntry = mongoTemplate.findOne(query,  TBByMonthEntry.class);
	    if(tbCurByMonthEntry == null) {
	    	 resp.setFailed(HttpsResponseEnum.THIS_MONTH_NONEXIST);
	    }   
	    else {
	    	 List<Integer> output = mongoTemplate.findOne(query, TBByMonthEntry.class).getData();
	         // Parse the input string to YearMonth
	         YearMonth prevMonth = YearMonth.parse(yearMonth, DateTimeFormatter.ofPattern("yyyy-MM"));
	         
	         // Calculate the one month earlier YearMonth
	         YearMonth oneMonthEarlier = prevMonth.minusMonths(1);

	         // Format the result as a string in the 'YYYY-MM' format
	         String prevYearMonth = oneMonthEarlier.format(DateTimeFormatter.ofPattern("yyyy-MM"));
	 	    criteria = Criteria.where("pdcl").is(productGroup)
	                 .and("yearMonth").is(prevYearMonth);
	 	    query = new Query(criteria);
	 	    TBByMonthEntry tbByMonthEntry = mongoTemplate.findOne(query, TBByMonthEntry.class);
	 	    if(tbByMonthEntry == null) {
	 	    	 resp.setFailed(HttpsResponseEnum.LAST_MONTH_NONEXIST);
	 	    }   
	 	    else {
	 	    	List<Integer> output2 = mongoTemplate.findOne(query, TBByMonthEntry.class).getData();
	 	  	    List<Integer> combined = Stream.concat(output.stream(), output2.stream()).toList();
	 	    	resp.setSuccess(combined);    
	 	    }	
	    }
	   
	    return resp;	  
	}
	
	
	public BaseHttpResponse<List<Integer>> getTotalCountByProductGroup(String productGroup, String yearMonth) {
		BaseHttpResponse<List<Integer>> resp = new BaseHttpResponse<>();
	    Criteria criteria = Criteria.where("pdcl").is(productGroup)
                .and("yearMonth").is(yearMonth);
	    Query query = new Query(criteria);
	    PPByMonthEntry ppCurByMonthEntry = mongoTemplate.findOne(query,  PPByMonthEntry.class);
	    if(ppCurByMonthEntry == null) {
	    	 resp.setFailed(HttpsResponseEnum.THIS_MONTH_NONEXIST);
	    }   
	    else {
	    	 List<Integer> output = mongoTemplate.findOne(query, PPByMonthEntry.class).getData();
	         // Parse the input string to YearMonth
	         YearMonth prevMonth = YearMonth.parse(yearMonth, DateTimeFormatter.ofPattern("yyyy-MM"));
	         
	         // Calculate the one month earlier YearMonth
	         YearMonth oneMonthEarlier = prevMonth.minusMonths(1);

	         // Format the result as a string in the 'YYYY-MM' format
	         String prevYearMonth = oneMonthEarlier.format(DateTimeFormatter.ofPattern("yyyy-MM"));
	 	    criteria = Criteria.where("pdcl").is(productGroup)
	                 .and("yearMonth").is(prevYearMonth);
	 	    query = new Query(criteria);
	 	    PPByMonthEntry ppByMonthEntry = mongoTemplate.findOne(query, PPByMonthEntry.class);
	 	    if(ppByMonthEntry == null) {
	 	    	 resp.setFailed(HttpsResponseEnum.LAST_MONTH_NONEXIST);
	 	    }   
	 	    else {
	 	    	List<Integer> output2 = mongoTemplate.findOne(query, PPByMonthEntry.class).getData();
	 	  	    List<Integer> combined = Stream.concat(output.stream(), output2.stream()).toList();
	 	    	resp.setSuccess(combined);    
	 	    }	
	    }
	   
	    return resp;	  
	}


	
	public void savePPByMonth(List<Integer> ppByMonthEntry, String yearMonth, String pdcl) {
		deletePPMonth(yearMonth, pdcl);;
		PPByMonthEntry pp = new PPByMonthEntry();
		pp.setData(ppByMonthEntry);
		pp.setYearMonth(yearMonth);
		pp.setPdcl(pdcl);		
		if (!mongoTemplate.collectionExists("ppByMonthEntry")) {
		        // If the collection doesn't exist, create it (optional)
		        mongoTemplate.createCollection("ppByMonthEntry");
		}
		ppByMonthEntryRepository.save(pp);
	}

	public void saveTBByMonth(List<Integer> tbByMonthEntry, String yearMonth, String pdcl) {
		deleteTBMonth(yearMonth, pdcl);;
		TBByMonthEntry tb = new TBByMonthEntry();
		tb.setData(tbByMonthEntry);
		tb.setYearMonth(yearMonth);
		tb.setPdcl(pdcl);		
		if (!mongoTemplate.collectionExists("tbByMonthEntry")) {
		        // If the collection doesn't exist, create it (optional)
		        mongoTemplate.createCollection("tbByMonthEntry");
		}
		tbByMonthEntryRepository.save(tb);
	}
	
	
	public void deleteTBMonth(String yearMonth, String pdcl) {
		 Query query = new Query(Criteria.where("pdcl").is(pdcl)
	                .and("yearMonth").is(yearMonth));
		 mongoTemplate.remove(query, TBByMonthEntry.class);
	}
	
	public void deletePPMonth(String yearMonth, String pdcl) {
		 Query query = new Query(Criteria.where("pdcl").is(pdcl)
	                .and("yearMonth").is(yearMonth));
		 mongoTemplate.remove(query, PPByMonthEntry.class);
	}
	
	
	public void ppQuarterCount(String yearMonth){
	    Criteria criteria = Criteria.where("yearMonth").is(yearMonth);
	    Query query = new Query(criteria);
	    List<PPByMonthEntry> output = mongoTemplate.find(query, PPByMonthEntry.class);
	    deleteQuarterCount(yearMonth);
	    for(PPByMonthEntry entry: output) {
	    	List<Integer> monthlyValues = entry.getData();
	    	//8 quarters
	    	List<Integer> quarterlyValues = new ArrayList<>();
	    	int overallIndex = 0;
	    	for(int i = 0; i < 8; i++) {
	    		int quarterValue = 0;
	    		for(int j = 0; j < 3; j++) {
	    			quarterValue += monthlyValues.get(overallIndex);
	    			overallIndex += 1;
	    		}
	    		quarterlyValues.add(quarterValue);	    		
	    	}
	    	PPByQuarterEntry pp = new PPByQuarterEntry();
	    	pp.setPdcl(entry.getPdcl());
	    	pp.setData(quarterlyValues);
	    	pp.setYearMonth(entry.getYearMonth());
	    	ppByQuarterEntryRepository.save(pp);	
	    }
	}
	
	public void tbQuarterCount(String yearMonth){
	    Criteria criteria = Criteria.where("yearMonth").is(yearMonth);
	    Query query = new Query(criteria);
	    List<TBByMonthEntry> output = mongoTemplate.find(query, TBByMonthEntry.class);
	    deleteQuarterCount(yearMonth);
	    for(TBByMonthEntry entry: output) {
	    	List<Integer> monthlyValues = entry.getData();
	    	//8 quarters
	    	List<Integer> quarterlyValues = new ArrayList<>();
	    	int overallIndex = 0;
	    	for(int i = 0; i < 8; i++) {
	    		int quarterValue = 0;
	    		for(int j = 0; j < 3; j++) {
	    			quarterValue += monthlyValues.get(overallIndex);
	    			overallIndex += 1;
	    		}
	    		quarterlyValues.add(quarterValue);	    		
	    	}
	    	TBByQuarterEntry tb = new TBByQuarterEntry();
	        tb.setPdcl(entry.getPdcl());
	    	tb.setData(quarterlyValues);
	    	tb.setYearMonth(entry.getYearMonth());
	    	tbByQuarterEntryRepository.save(tb);	
	    }
	}
	
	public List<PPByQuarterEntry> getPPQuarterCount(String yearMonth){
		 Criteria criteria = Criteria.where("yearMonth").is(yearMonth);
		 Query query = new Query(criteria);
		 List<PPByQuarterEntry> output = mongoTemplate.find(query, PPByQuarterEntry.class);
		 return output;		
	}
	
	public List<TBByQuarterEntry> getTBQuarterCount(String yearMonth){
		 Criteria criteria = Criteria.where("yearMonth").is(yearMonth);
		 Query query = new Query(criteria);
		 List<TBByQuarterEntry> output = mongoTemplate.find(query, TBByQuarterEntry.class);
		 return output;		
	}
	
	
	public void deleteTBQuarterCount(String yearMonth) {
		 Query query = new Query(Criteria.where("yearMonth").is(yearMonth));
		 mongoTemplate.remove(query, TBByQuarterEntry.class);
	}
	
	public void deleteQuarterCount(String yearMonth) {
		 Query query = new Query(Criteria.where("yearMonth").is(yearMonth));
		 mongoTemplate.remove(query, PPByQuarterEntry.class);
	}
	
	public void ppQuarterPercentage(String yearMonth){
	    Criteria criteria = Criteria.where("yearMonth").is(yearMonth);
	    Query query = new Query(criteria);
	    List<PPByQuarterEntry> output = mongoTemplate.find(query, PPByQuarterEntry.class);
	    deleteQuarterPercentage(yearMonth);
	    for(PPByQuarterEntry entry: output) {
	    	int overallyear1 = 0;
	    	int overallyear2 = 0;
	    	List<Integer> quarterlyValues = entry.getData();
	    	List<Integer> quarterlyPercentages = new ArrayList<>();
	    	for(int i = 0; i < 4; i++) {
	    		overallyear1 += quarterlyValues.get(i);	    		
	    	}
	    	for(int i = 0; i < 4; i++) {
	    		double quarterlyPercentage = (double) quarterlyValues.get(i) / (double) overallyear1;  
	    		int percentage = (int) Math.round(quarterlyPercentage * 100);
	    		quarterlyPercentages.add(percentage);
	    	}
	    	for(int i = 4; i < 8; i++) {
	    		overallyear2 += quarterlyValues.get(i);	    		
	    	}
	    	for(int i = 4; i < 8; i++) {
	    		double quarterlyPercentage = (double) quarterlyValues.get(i) / (double) overallyear2;  
	    		int percentage = (int) Math.round(quarterlyPercentage * 100);
	    		quarterlyPercentages.add(percentage);
	    	}
	    	PPByQuarterPercent pp = new PPByQuarterPercent();
	    	pp.setPdcl(entry.getPdcl());
	    	pp.setData(quarterlyPercentages);
	    	pp.setYearMonth(yearMonth);
	    	ppByQuarterPercentRepository.save(pp);	 
	    }
	}
	
	public void tbQuarterPercentage(String yearMonth){
	    Criteria criteria = Criteria.where("yearMonth").is(yearMonth);
	    Query query = new Query(criteria);
	    List<TBByQuarterEntry> output = mongoTemplate.find(query, TBByQuarterEntry.class);
	    deleteQuarterPercentage(yearMonth);
	    for(TBByQuarterEntry entry: output) {
	    	int overallyear1 = 0;
	    	int overallyear2 = 0;
	    	List<Integer> quarterlyValues = entry.getData();
	    	List<Integer> quarterlyPercentages = new ArrayList<>();
	    	for(int i = 0; i < 4; i++) {
	    		overallyear1 += quarterlyValues.get(i);	    		
	    	}
	    	for(int i = 0; i < 4; i++) {
	    		double quarterlyPercentage = (double) quarterlyValues.get(i) / (double) overallyear1;  
	    		int percentage = (int) Math.round(quarterlyPercentage * 100);
	    		quarterlyPercentages.add(percentage);
	    	}
	    	for(int i = 4; i < 8; i++) {
	    		overallyear2 += quarterlyValues.get(i);	    		
	    	}
	    	for(int i = 4; i < 8; i++) {
	    		double quarterlyPercentage = (double) quarterlyValues.get(i) / (double) overallyear2;  
	    		int percentage = (int) Math.round(quarterlyPercentage * 100);
	    		quarterlyPercentages.add(percentage);
	    	}
	    	TBByQuarterPercent tb = new TBByQuarterPercent();
	    	tb.setPdcl(entry.getPdcl());
	    	tb.setData(quarterlyPercentages);
	    	tb.setYearMonth(yearMonth);
	    	tbByQuarterPercentRepository.save(tb);	 
	    }
	}
	
	public void tbComparePercentage(String yearMonth){
		 Criteria criteria = Criteria.where("yearMonth").is(yearMonth);
		 Query query = new Query(criteria);
		 List<TBByQuarterEntry> output = mongoTemplate.find(query, TBByQuarterEntry.class);
		 for(TBByQuarterEntry entry: output) {
			 int firstHalfYear1 = 0;
			 int firstHalfYear2 = 0;
			 int secondHalfYear1 = 0;
			 int secondHalfYear2 = 0;
			 List<Integer> quarterlyValues = entry.getData();
			 List<Integer> calcoutput = new ArrayList<>();
			 for(int i = 0; i < 4; i++) {
				 int first = quarterlyValues.get(i);
				 int second = quarterlyValues.get(i + 4);
				 if(i < 2) {
					 firstHalfYear1 += first;
					 secondHalfYear1 += second;				 }
				 else {
					 firstHalfYear2 += first;
					 secondHalfYear2 += second;
				 }
				 double curcal = (double) second / (double)first - 1;
				 int percentage = (int) Math.round(curcal * 100);
				 calcoutput.add(percentage);				  
			}
			double curcal = (double) secondHalfYear1 / (double) firstHalfYear1 - 1;
			int percentage = (int) Math.round(curcal * 100);
			calcoutput.add(percentage);
			
			curcal = (double) secondHalfYear2 / (double) firstHalfYear2 - 1;
			percentage = (int) Math.round(curcal * 100);
			calcoutput.add(percentage);
			
			curcal = (double) (secondHalfYear1 +secondHalfYear2) / (double) (firstHalfYear1 + firstHalfYear2) - 1;
			percentage = (int) Math.round(curcal * 100);
			calcoutput.add(percentage);
			TBPercentageCompare tb = new TBPercentageCompare();
	    	tb.setPdcl(entry.getPdcl());
	    	tb.setData(calcoutput);
	    	tb.setYearMonth(yearMonth);
	    	tbPercentageCompareRepository.save(tb);	
		}
	}
	
	public void ppComparePercentage(String yearMonth){
		 Criteria criteria = Criteria.where("yearMonth").is(yearMonth);
		 Query query = new Query(criteria);
		 List<PPByQuarterEntry> output = mongoTemplate.find(query, PPByQuarterEntry.class);
		 for(PPByQuarterEntry entry: output) {
			 int firstHalfYear1 = 0;
			 int firstHalfYear2 = 0;
			 int secondHalfYear1 = 0;
			 int secondHalfYear2 = 0;
			 List<Integer> quarterlyValues = entry.getData();
			 List<Integer> calcoutput = new ArrayList<>();
			 for(int i = 0; i < 4; i++) {
				 int first = quarterlyValues.get(i);
				 int second = quarterlyValues.get(i + 4);
				 if(i < 2) {
					 firstHalfYear1 += first;
					 secondHalfYear1 += second;				 }
				 else {
					 firstHalfYear2 += first;
					 secondHalfYear2 += second;
				 }
				 double curcal = (double) second / (double)first - 1;
				 int percentage = (int) Math.round(curcal * 100);
				 calcoutput.add(percentage);				  
			}
			double curcal = (double) secondHalfYear1 / (double) firstHalfYear1 - 1;
			int percentage = (int) Math.round(curcal * 100);
			calcoutput.add(percentage);
			
			curcal = (double) secondHalfYear2 / (double) firstHalfYear2 - 1;
			percentage = (int) Math.round(curcal * 100);
			calcoutput.add(percentage);
			
			curcal = (double) (secondHalfYear1 +secondHalfYear2) / (double) (firstHalfYear1 + firstHalfYear2) - 1;
			percentage = (int) Math.round(curcal * 100);
			calcoutput.add(percentage);
			PPPercentageCompare pp = new PPPercentageCompare();
	    	pp.setPdcl(entry.getPdcl());
	    	pp.setData(calcoutput);
	    	pp.setYearMonth(yearMonth);
	    	ppPercentageCompareRepository.save(pp);	
		}
	}
	
	
	public List<PPPercentageCompare> getPPCompare(String yearMonth){
		 Criteria criteria = Criteria.where("yearMonth").is(yearMonth);
		 Query query = new Query(criteria);
		 List<PPPercentageCompare> output = mongoTemplate.find(query, PPPercentageCompare.class);
		 return output;		
	}
	
	public List<TBPercentageCompare> getTBCompare(String yearMonth){
		 Criteria criteria = Criteria.where("yearMonth").is(yearMonth);
		 Query query = new Query(criteria);
		 List<TBPercentageCompare> output = mongoTemplate.find(query, TBPercentageCompare.class);
		 return output;		
	}
	
	public List<PPByQuarterPercent> getPPQuarterPercent(String yearMonth){
		 Criteria criteria = Criteria.where("yearMonth").is(yearMonth);
		 Query query = new Query(criteria);
		 List<PPByQuarterPercent> output = mongoTemplate.find(query, PPByQuarterPercent.class);
		 return output;		
	}
	
	public List<TBByQuarterPercent> getTBQuarterPercent(String yearMonth){
		 Criteria criteria = Criteria.where("yearMonth").is(yearMonth);
		 Query query = new Query(criteria);
		 List<TBByQuarterPercent> output = mongoTemplate.find(query, TBByQuarterPercent.class);
		 return output;		
	}
	
	
	public void deleteQuarterPercentage(String yearMonth) {
		 Query query = new Query(Criteria.where("yearMonth").is(yearMonth));
		 mongoTemplate.remove(query, PPByQuarterPercent.class);
	}
	
	
	public List<String> findAllDistinctpdcl(String yearMonth) {
	    Criteria criteria = Criteria.where("yearMonth").is(yearMonth);
	    Query query = new Query(criteria);
	    List<String> distinctpdclList = mongoTemplate.findDistinct(query, "pdcl", DataEntry.class, String.class);
	    return distinctpdclList;
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
