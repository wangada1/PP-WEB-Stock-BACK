package com.example.ppback.service;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import com.alibaba.excel.util.ListUtils;
import com.example.ppback.model.GRDataAggregatedResult;
import com.example.ppback.model.PPDataAggregatedResult;
import com.example.ppback.model.SOLDDataAggregatedResult;
import com.example.ppback.model.STOCKDataAggregatedResult;
import com.example.ppback.model.TBDataAggregatedResult;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SummaryDataService{
	@Autowired
	private MongoTemplate mongoTemplate;
	public List<Integer> getTB(String yearMonth,String vendor,String pdcl,String type){
		// 定义筛选规则
				int notNullCount = 0;
				YearMonth curMonth = YearMonth.parse(yearMonth, DateTimeFormatter.ofPattern("yyyy-MM"));
			    int month = curMonth.getMonthValue();// 当前月份值
			    int year = curMonth.getYear();// 当前年份值
				notNullCount += Stream.of(vendor, pdcl, type).filter(s -> !s.isEmpty()).count();
			    Criteria criteria = null;
			    if(notNullCount==0) {criteria = Criteria.where("yearMonth").is(yearMonth);}
			    if(notNullCount==1) {
			    	if(!vendor.isEmpty()) {criteria = Criteria.where("vendor").is(vendor).and("yearMonth").is(yearMonth);} // JAVA语法要求，控制流语句如果只有一条语句，就不能声明一个新变量
			    	if(!pdcl.isEmpty()) {criteria = Criteria.where("pdcl").is(pdcl).and("yearMonth").is(yearMonth);}
			    	if(!type.isEmpty()) {criteria = Criteria.where("type").is(type).and("yearMonth").is(yearMonth);}
			    };
			    if(notNullCount==2){
			    	if(vendor.isEmpty()) {criteria = Criteria.where("pdcl").is(pdcl).and("type").is(type).and("yearMonth").is(yearMonth);} 
			    	if(pdcl.isEmpty()) {criteria = Criteria.where("vendor").is(vendor).and("type").is(type).and("yearMonth").is(yearMonth);}
			    	if(type.isEmpty()) {criteria = Criteria.where("pdcl").is(pdcl).and("vendor").is(vendor).and("yearMonth").is(yearMonth);}
			    }
			    if(notNullCount==3) {
			    	criteria =  Criteria.where("pdcl").is(pdcl).and("type").is(type).and("vendor").is(vendor).and("yearMonth").is(yearMonth);
			    };
			    AggregationOperation match = Aggregation.match(criteria);
			  //TB聚类
			    GroupOperation tbgroup = Aggregation.group();
			    if(month>6) {
			    	for(int i=0;i<25-month;i++) {
			    		String tbFieldName = "$tb" + i;
			    	    String totalFieldName = "totalTB" + i;
			    	    tbgroup = tbgroup.sum(tbFieldName).as(totalFieldName) ; 
			    	}
			    	}
			    else {
			    	for(int i=0;i<13-month;i++) {
			    		String tbFieldName = "$tb" + i;
			    	    String totalFieldName = "totalTB" + i;
			    	    tbgroup = tbgroup.sum(tbFieldName).as(totalFieldName) ; 
			    	}
			    };
			    Aggregation TBaggregation = Aggregation.newAggregation(match, tbgroup);
			    AggregationResults<TBDataAggregatedResult> resultsTB =
			    	            mongoTemplate.aggregate(TBaggregation, "dataEntry", TBDataAggregatedResult.class);
			    //SOLD聚类
			    GroupOperation soldgroup = Aggregation.group();
			    if(month>6) {
			    	for(int i=0;i<month-1;i++) {
			    		String soldFieldName = "$soldInfo" + i;
			    	    String totalFieldName = "totalSOLD" + i;
			    	    soldgroup = soldgroup.sum(soldFieldName).as(totalFieldName) ; 
			    	}
			    	}
			    else {
			    	for(int i=0;i<month+11;i++) {
			    		String soldFieldName =  "$soldInfo" + i;
			    	    String totalFieldName = "totalSOLD" + i;
			    	    soldgroup = soldgroup.sum(soldFieldName).as(totalFieldName) ; 
			    	}
			    };
			    Aggregation SOLDaggregation = Aggregation.newAggregation(match, soldgroup);
			    AggregationResults<SOLDDataAggregatedResult> resultsSOLD =
			    	            mongoTemplate.aggregate(SOLDaggregation, "soldDataEntry", SOLDDataAggregatedResult.class);
			    //补充数据
			    //对于month<7，补充12个月数据到最后，是明年的TB
			    //对于month>6,补充12个月数据到最前面，是去年的TB,目前这个是12个0
			    GroupOperation supplygroup = Aggregation.group();
			    if(month<7){
			    	for(int i=13-month;i<19;i++) {
			    		String supplyFieldName = "$tb" + i;
			    	    String totalFieldName = "totalTB" + i;
			    	    supplygroup = supplygroup.sum(supplyFieldName).as(totalFieldName) ; 
			    	}
			    };
			    Aggregation supplyaggregation = Aggregation.newAggregation(match, supplygroup);
			    AggregationResults<TBDataAggregatedResult> resultssupply =
			    	            mongoTemplate.aggregate(supplyaggregation, "dataEntry", TBDataAggregatedResult.class);
			    //缝合输出
			    List<Integer> outputtb = resultsTB.getUniqueMappedResult().iterator(month);
			     List<Integer> outputsold = resultsSOLD.getUniqueMappedResult().iterator(month);
			     List<Integer> combined = Stream.concat(outputsold.stream(), outputtb.stream()).toList();
			     if(month<7) {
			    	 List<Integer> outputsupply = resultssupply.getUniqueMappedResult().iterator(month);
			    	 List<Integer> zeros = Collections.nCopies(6-month, 0);
			    	 outputsupply.addAll(zeros); 
			    	 combined = Stream.concat(combined.stream(), outputsupply.stream()).toList();;
			     }
			     else {
			    	 List<Integer> outputsupply = Collections.nCopies(12, 0);
			    	 combined = Stream.concat(outputsupply.stream(), combined.stream()).toList();;
			     }
			     return combined;//返回36个数据。分别是去年，今年，明年的TB;
			     }
	
	
	public List<Integer> getPP(String yearMonth,String vendor,String pdcl,String type){
		// 定义筛选规则
				int notNullCount = 0;
				YearMonth curMonth = YearMonth.parse(yearMonth, DateTimeFormatter.ofPattern("yyyy-MM"));
			    int month = curMonth.getMonthValue();// 当前月份值
			    int year = curMonth.getYear();// 当前年份值
				notNullCount += Stream.of(vendor, pdcl, type).filter(s -> !s.isEmpty()).count();
				Criteria criteria = null;
				if(notNullCount==0) {criteria = Criteria.where("yearMonth").is(yearMonth);}
			    if(notNullCount==1) {
			    	if(!vendor.isEmpty()) {criteria = Criteria.where("vendor").is(vendor).and("yearMonth").is(yearMonth);} // JAVA语法要求，控制流语句如果只有一条语句，就不能声明一个新变量
			    	if(!pdcl.isEmpty()) {criteria = Criteria.where("pdcl").is(pdcl).and("yearMonth").is(yearMonth);}
			    	if(!type.isEmpty()) {criteria = Criteria.where("type").is(type).and("yearMonth").is(yearMonth);}
			    };
			    if(notNullCount==2){
			    	if(vendor.isEmpty()) {criteria = Criteria.where("pdcl").is(pdcl).and("type").is(type).and("yearMonth").is(yearMonth);} 
			    	if(pdcl.isEmpty()) {criteria = Criteria.where("vendor").is(vendor).and("type").is(type).and("yearMonth").is(yearMonth);}
			    	if(type.isEmpty()) {criteria = Criteria.where("pdcl").is(pdcl).and("vendor").is(vendor).and("yearMonth").is(yearMonth);}
			    }
			    if(notNullCount==3) {
			    	criteria =  Criteria.where("pdcl").is(pdcl).and("type").is(type).and("vendor").is(vendor).and("yearMonth").is(yearMonth);
			    };
			    AggregationOperation match = Aggregation.match(criteria);
			  //PP聚类
			    GroupOperation ppgroup = Aggregation.group();
			    if(month>6) {
			    	for(int i=0;i<25-month;i++) {
			    		String ppFieldName = "$pp" + i;
			    	    String totalFieldName = "totalPP" + i;
			    	    ppgroup = ppgroup.sum(ppFieldName).as(totalFieldName) ; 
			    	}
			    	}
			    else {
			    	for(int i=0;i<13-month;i++) {
			    		String ppFieldName = "$pp" + i;
			    	    String totalFieldName = "totalPP" + i;
			    	    ppgroup = ppgroup.sum(ppFieldName).as(totalFieldName) ; 
			    	}
			    };
			    Aggregation PPaggregation = Aggregation.newAggregation(match, ppgroup);
			    AggregationResults<PPDataAggregatedResult> resultsPP =
			    	            mongoTemplate.aggregate(PPaggregation, "dataEntry", PPDataAggregatedResult.class);
			    //GR聚类
			    GroupOperation grgroup = Aggregation.group();
			    if(month>6) {
			    	for(int i=0;i<month-1;i++) {
			    		String grFieldName = "$grInfo" + i;
			    	    String totalFieldName = "totalGR" + i;
			    	    grgroup = grgroup.sum(grFieldName).as(totalFieldName) ; 
			    	}
			    	}
			    else {
			    	for(int i=0;i<month+11;i++) {
			    		String grFieldName =  "$grInfo" + i;
			    	    String totalFieldName = "totalGR" + i;
			    	    grgroup = grgroup.sum(grFieldName).as(totalFieldName) ; 
			    	}
			    };
			    //这里的grinfo默认处理为非数值，需要修改数据库中grinfo的数据结构，将其改为int而非array
			    Aggregation GRaggregation = Aggregation.newAggregation(match, grgroup);
			    AggregationResults<GRDataAggregatedResult> resultsGR =
			    	            mongoTemplate.aggregate(GRaggregation, "grDataEntry", GRDataAggregatedResult.class);
			  //补充数据
			    //对于month<7，补充12个月数据到最后，是明年的PP
			    //对于month>6,补充12个月数据到最前面，是去年的PP,目前这个是12个0
			    GroupOperation supplygroup = Aggregation.group();
			    if(month<7){
			    	for(int i=13-month;i<19;i++) {
			    		String supplyFieldName = "$pp" + i;
			    	    String totalFieldName = "totalPP" + i;
			    	    supplygroup = supplygroup.sum(supplyFieldName).as(totalFieldName) ; 
			    	}
			    };
			    Aggregation supplyaggregation = Aggregation.newAggregation(match, supplygroup);
			    AggregationResults<PPDataAggregatedResult> resultssupply =
			    	            mongoTemplate.aggregate(supplyaggregation, "dataEntry", PPDataAggregatedResult.class);
			    //缝合输出
			    List<Integer> outputpp = resultsPP.getUniqueMappedResult().iterator(month);
			     List<Integer> outputgr = (!type.isEmpty())?new ArrayList<>(Collections.nCopies(month>6?month-1:month+11, 0)):resultsGR.getUniqueMappedResult().iterator(month);
			     List<Integer> combined = Stream.concat(outputgr.stream(), outputpp.stream()).toList();
			     if(month<7) {
			    	 List<Integer> outputsupply = resultssupply.getUniqueMappedResult().iterator(month);
			    	 List<Integer> zeros = Collections.nCopies(6-month, 0);
			    	 outputsupply.addAll(zeros); 
			    	 combined = Stream.concat(combined.stream(), outputsupply.stream()).toList();;
			     }
			     else {
			    	 List<Integer> outputsupply = Collections.nCopies(12, 0);
			    	 combined = Stream.concat(outputsupply.stream(), combined.stream()).toList();;
			     }
			     return combined;//返回36个数据。分别是去年，今年，明年的PP
			     }
	
	
	public List<Integer> getSTOCK(String yearMonth,String vendor,String pdcl,String type){
		// 定义筛选规则
		int notNullCount = 0;
		YearMonth curMonth = YearMonth.parse(yearMonth, DateTimeFormatter.ofPattern("yyyy-MM"));
	    int month = curMonth.getMonthValue();// 当前月份值
	    int year = curMonth.getYear();// 当前年份值
		notNullCount += Stream.of(vendor, pdcl, type).filter(s -> !s.isEmpty()).count();
	    Criteria criteria = null;
	    if(notNullCount==0) {criteria = Criteria.where("yearMonth").is(yearMonth);}
	    if(notNullCount==1) {
	    	if(!vendor.isEmpty()) {criteria = Criteria.where("Vendor").is(vendor).and("yearMonth").is(yearMonth);} // JAVA语法要求，控制流语句如果只有一条语句，就不能声明一个新变量
	    	if(!pdcl.isEmpty()) {criteria = Criteria.where("PDCL").is(pdcl).and("yearMonth").is(yearMonth);}
	    	if(!type.isEmpty()) {criteria = Criteria.where("Type").is(type).and("yearMonth").is(yearMonth);}
	    };
	    if(notNullCount==2){
	    	if(vendor.isEmpty()) {criteria = Criteria.where("PDCL").is(pdcl).and("Type").is(type).and("yearMonth").is(yearMonth);} 
	    	if(pdcl.isEmpty()) {criteria = Criteria.where("Vendor").is(vendor).and("Type").is(type).and("yearMonth").is(yearMonth);}
	    	if(type.isEmpty()) {criteria = Criteria.where("PDCL").is(pdcl).and("Vendor").is(vendor).and("yearMonth").is(yearMonth);}
	    }
	    if(notNullCount==3) {
	    	criteria =  Criteria.where("PDCL").is(pdcl).and("Type").is(type).and("Vendor").is(vendor).and("yearMonth").is(yearMonth);
	    };
	    AggregationOperation match = Aggregation.match(criteria);
	  //Stock聚类
	    GroupOperation stockgroup = Aggregation.group();
	    if(month>6) {
	    	for(int i=0;i<month-1;i++) {
	    		String stockFieldName = "$StockInfo" + i;
	    	    String totalFieldName = "totalSTOCK" + i;
	    	    stockgroup = stockgroup.sum(stockFieldName).as(totalFieldName) ; 
	    	}
	    	}
	    else {
	    	for(int i=0;i<month+11;i++) {
	    		String stockFieldName = "$StockInfo" + i;
	    	    String totalFieldName = "totalSTOCK" + i;
	    	    stockgroup = stockgroup.sum(stockFieldName).as(totalFieldName) ; 
	    	}
	    };
	    Aggregation stockaggregation = Aggregation.newAggregation(match, stockgroup);
	    AggregationResults<STOCKDataAggregatedResult> resultsSTOCK =
	    	            mongoTemplate.aggregate(stockaggregation, "StockEntry", STOCKDataAggregatedResult.class);
	    List<Integer> outputstock = resultsSTOCK.getUniqueMappedResult().iterator(month);
	    List<Integer> combined = null;
	    //SUPPLY补充，小于7右边补25-month个0，大于6左边补12个0，右边补25-month个0
	    if(month>6) {
	    	List<Integer> outputsupply = Collections.nCopies(12, 0);
	    	combined = Stream.concat(outputsupply.stream(), outputstock.stream()).toList();
	    	 outputsupply= Collections.nCopies(25-month, 0);
	    	 combined = Stream.concat(combined.stream(), outputsupply.stream()).toList();
	    	}
	    	
	    else {
	    	List<Integer> outputsupply= Collections.nCopies(25-month, 0);
	    	combined = Stream.concat(outputstock.stream(), outputsupply.stream()).toList();
	    	}
	    return combined;
		//返回36个数据，分别是去年，今年和明年的stock
	}
	
}