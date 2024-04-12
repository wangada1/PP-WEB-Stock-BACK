package com.example.ppback.service;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;
import java.util.Collections;
import javax.swing.JOptionPane;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationExpression;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;
import org.springframework.data.mongodb.core.aggregation.UnwindOperation;
import com.example.ppback.model.DataEntry;
import com.example.ppback.model.DataEntryImportEntity;
import com.example.ppback.model.GRDataAggregatedResult;
import com.example.ppback.model.SOLDDataAggregatedResult;
import com.example.ppback.model.PPByMonthEntry;
import com.example.ppback.model.PPByQuarterEntry;
import com.example.ppback.model.PPByQuarterPercent;
import com.example.ppback.model.PPDataAggregatedResult;
import com.example.ppback.model.TBDataAggregatedResult;
import com.example.ppback.model.PPPercentageCompare;
import com.example.ppback.model.TBByMonthEntry;
import com.example.ppback.model.TBByQuarterEntry;
import com.example.ppback.model.TBByQuarterPercent;
import com.example.ppback.model.TBPercentageCompare;
import com.example.ppback.repository.DataEntryRepository;
import com.example.ppback.repository.PPByMonthEntryRepository;
import com.example.ppback.repository.PPByQuarterEntryRepository;
import com.example.ppback.repository.PPByQuarterPercentRepository;
import com.example.ppback.repository.PPDataGroupByPDCLandVendorRepository;
import com.example.ppback.repository.PPPercentageCompareRepository;
import com.example.ppback.repository.TBByMonthEntryRepository;
import com.example.ppback.repository.TBByQuarterEntryRepository;
import com.example.ppback.repository.TBByQuarterPercentRepository;
import com.example.ppback.repository.TBPercentageCompareRepository;
import com.example.ppback.util.ExcelUtil;
import com.mongodb.BasicDBObject;

import lombok.extern.slf4j.Slf4j;

@Slf4j
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
	        info.setVendor(importEntity.getVendor());
	        //上传逻辑修改，删除末尾的.0
	        String vendor = importEntity.getVendor();
	        if (vendor.endsWith(".0")) {
	            vendor = vendor.substring(0, vendor.length() - 2);
	        }
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
	        //修改结束
	        info.setType(importEntity.getType());
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
	
	public BaseHttpResponse<List<Integer>> getTotalTB(String vendor, String pdcl, String type, String yearMonth) {
		// 定义筛选规则
		int notNullCount = 0;
		YearMonth curMonth = YearMonth.parse(yearMonth, DateTimeFormatter.ofPattern("yyyy-MM"));
	    YearMonth prevMonth = curMonth.minusMonths(1);//前一月的年月值
	    int month = curMonth.getMonthValue();// 当前月份值
	    int year = curMonth.getYear();// 当前年份值
		BaseHttpResponse<List<Integer>> resp = new BaseHttpResponse<>();
		notNullCount += Stream.of(vendor, pdcl, type).filter(s -> !s.isEmpty()).count();
	    if(notNullCount==0) {resp.setSuccess(Collections.emptyList());return resp;}
	    Criteria criteria = null;
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
	    //这里的soldinfo默认处理为非数值，需要修改数据库中soldinfo的数据结构，将其改为int而非array
	    Aggregation SOLDaggregation = Aggregation.newAggregation(match, soldgroup);
	    AggregationResults<SOLDDataAggregatedResult> resultsSOLD =
	    	            mongoTemplate.aggregate(SOLDaggregation, "soldDataEntry", SOLDDataAggregatedResult.class);
	    //缝合输出
	    if(resultsTB.getUniqueMappedResult()==null) {resp.setFailed(HttpsResponseEnum.THIS_MONTH_NONEXIST);return resp;}
	    if(type.isEmpty() && resultsSOLD.getUniqueMappedResult()==null) {resp.setFailed(HttpsResponseEnum.THIS_MONTH_NONEXIST);return resp;}
	    List<Integer> outputtb = resultsTB.getUniqueMappedResult().iterator(month);
	     List<Integer> outputsold = (!type.isEmpty())?new ArrayList<>(Collections.nCopies(month>6?month-1:month+11, 0)):resultsSOLD.getUniqueMappedResult().iterator(month);
	     //combine的前24个数据显示为上个月，后24个数据显示为当月
	     List<Integer> combined = Stream.concat(outputsold.stream(), outputtb.stream()).toList();

	    //对上月执行类似的操作,注意对1月和7月的处理，无法处理，只能提示
	    yearMonth = prevMonth.format(DateTimeFormatter.ofPattern("yyyy-MM"));
	    month = prevMonth.getMonthValue();
	    if(month==6) { 
	    	String messageMonth = "比较的上月数据为" + (year-1) + "年到" + year + "年的数据，进行比较时请留意";
	    	JOptionPane.showMessageDialog(null, messageMonth, "警告", JOptionPane.WARNING_MESSAGE); 
	    	};
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
	    match = Aggregation.match(criteria);
	  //TB聚类
	    tbgroup = Aggregation.group();
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
	    TBaggregation = Aggregation.newAggregation(match, tbgroup);
	    resultsTB = mongoTemplate.aggregate(TBaggregation, "dataEntry", TBDataAggregatedResult.class);
	    //SOLD聚类
	    soldgroup = Aggregation.group();
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
	    //这里的soldinfo默认处理为非数值，需要修改数据库中grinfo的数据结构，将其改为int而非array
	    SOLDaggregation = Aggregation.newAggregation(match, soldgroup);
	    resultsSOLD = mongoTemplate.aggregate(SOLDaggregation, "soldDataEntry", SOLDDataAggregatedResult.class);
	    //缝合输出
	    if(resultsTB.getUniqueMappedResult()==null) {resp.setFailed(HttpsResponseEnum.LAST_MONTH_NONEXIST);return resp;}
	    if(type.isEmpty() && resultsSOLD.getUniqueMappedResult()==null) {resp.setFailed(HttpsResponseEnum.LAST_MONTH_NONEXIST);return resp;}
		    outputtb = resultsTB.getUniqueMappedResult().iterator(month);
		    outputsold = (!type.isEmpty())?new ArrayList<>(Collections.nCopies(month>6?month-1:month+11, 0)):resultsSOLD.getUniqueMappedResult().iterator(month);
		     //combine的前24个数据显示为上个月，后24个数据显示为当月
		     List<Integer> lastcombined = Stream.concat(outputsold.stream(), outputtb.stream()).toList();
	     lastcombined = Stream.concat(lastcombined.stream(), combined.stream()).toList();
		    resp.setSuccess(lastcombined);
	    return resp;
	}
	// 1-6月 去年的GR +本月前GR + 本月PP +本月后今年PP
	// 7-12月 本月前GR + 本月PP + 本月后今年PP + 明年PP
	// 根据pdcl和vendor查询data和gr数据库，分别输出结果后，根据月份进行数据缝合
	public BaseHttpResponse<List<Integer>> getTotalPP(String vendor, String pdcl, String type, String yearMonth) {
		// 定义筛选规则
		int notNullCount = 0;
		YearMonth curMonth = YearMonth.parse(yearMonth, DateTimeFormatter.ofPattern("yyyy-MM"));
	    YearMonth prevMonth = curMonth.minusMonths(1);//前一月的年月值
	    int month = curMonth.getMonthValue();// 当前月份值
	    int year = curMonth.getYear();// 当前年份值
		BaseHttpResponse<List<Integer>> resp = new BaseHttpResponse<>();
		notNullCount += Stream.of(vendor, pdcl, type).filter(s -> !s.isEmpty()).count();
		if(notNullCount==0) {resp.setSuccess(Collections.emptyList());return resp;}
	    Criteria criteria = null;
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
	    //缝合输出
	    if(resultsPP.getUniqueMappedResult()==null) {resp.setFailed(HttpsResponseEnum.THIS_MONTH_NONEXIST);return resp;}
	    if(type.isEmpty() && resultsGR.getUniqueMappedResult()==null) {resp.setFailed(HttpsResponseEnum.THIS_MONTH_NONEXIST);return resp;}
	    List<Integer> outputpp = resultsPP.getUniqueMappedResult().iterator(month);
	     List<Integer> outputgr = (!type.isEmpty())?new ArrayList<>(Collections.nCopies(month>6?month-1:month+11, 0)):resultsGR.getUniqueMappedResult().iterator(month);
	     //combine的前24个数据显示为上个月，后24个数据显示为当月
	     List<Integer> combined = Stream.concat(outputgr.stream(), outputpp.stream()).toList();

	    //对上月执行类似的操作,注意对1月和7月的处理，无法处理，只能提示
	    yearMonth = prevMonth.format(DateTimeFormatter.ofPattern("yyyy-MM"));
	    month = prevMonth.getMonthValue();// 上月月份值
	    if(month==6) { 
	    	String messageMonth = "比较的上月数据实际为" + (year-1) + "年到" + year + "年的数据，显示可能存在误解，进行比较时请留意";
	    	JOptionPane.showMessageDialog(null, messageMonth, "警告", JOptionPane.WARNING_MESSAGE); 
	    	};
	    log.info("get pp data from: " + vendor + " and " + pdcl + " in " + yearMonth);
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
	    match = Aggregation.match(criteria);
	    ppgroup = Aggregation.group();
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
	    PPaggregation = Aggregation.newAggregation(match, ppgroup);
	    resultsPP = mongoTemplate.aggregate(PPaggregation, "dataEntry", PPDataAggregatedResult.class);
	    grgroup = Aggregation.group();
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
	    GRaggregation = Aggregation.newAggregation(match, grgroup);
	    resultsGR = mongoTemplate.aggregate(GRaggregation, "grDataEntry", GRDataAggregatedResult.class);
	    if(resultsPP.getUniqueMappedResult()==null) {resp.setFailed(HttpsResponseEnum.LAST_MONTH_NONEXIST);return resp;}
	    if(type.isEmpty() && resultsGR.getUniqueMappedResult()==null) {resp.setFailed(HttpsResponseEnum.LAST_MONTH_NONEXIST);return resp;}
	     outputpp = resultsPP.getUniqueMappedResult().iterator(month);
	     outputgr = (!type.isEmpty())?new ArrayList<>(Collections.nCopies(month>6?month-1:month+11, 0)):resultsGR.getUniqueMappedResult().iterator(month);
	     List<Integer> lastcombined = Stream.concat(outputgr.stream(), outputpp.stream()).toList();
	     lastcombined = Stream.concat(lastcombined.stream(), combined.stream()).toList();
		    resp.setSuccess(lastcombined);
	    return resp;
	}
	//修改为pdcl和Vendor共同筛选，采用复合索引的方式,下面的这个不用了，用上面的getTotalPP代替
	/*
	public BaseHttpResponse<List<Integer>> getTotalCountByProductGroup(String productGroup, String vendor,String yearMonth) {
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
*/
	
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
