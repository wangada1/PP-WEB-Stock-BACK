package com.example.ppback.service;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.Collections;
import javax.swing.JOptionPane;
import org.springframework.jdbc.core.JdbcTemplate;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.example.ppback.model.DataEntry;
import com.example.ppback.model.DataEntryImportEntity;
import com.example.ppback.model.GRDataAggregatedResult;
import com.example.ppback.model.SOLDDataAggregatedResult;
import com.example.ppback.model.PPDataAggregatedResult;
import com.example.ppback.model.TBDataAggregatedResult;
import com.example.ppback.repository.DataEntryRepository;
import com.example.ppback.repository.PPDataGroupByPDCLandVendorRepository;
import com.example.ppback.util.ExcelUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DataEntryService implements UploadPara{
	@Autowired
	private DataEntryRepository dataEntryRepository;
	@Autowired
	private VendorPDCLMapper VendorPDCLMapper;
	@Autowired
	private JdbcTemplate jdbcTemplate;
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
	    int entityCount = importEntities.size();
 	    AtomicInteger progress = new AtomicInteger(0);
 	    long startTime = System.currentTimeMillis(); // 记录开始时间
	    importEntities.forEach(importEntity -> {
	        DataEntry info = new DataEntry();
	        info.setProductNumber(importEntity.getProductNumber());
	        info.setPdcl(importEntity.getPdcl());
	        //上传逻辑修改，删除末尾的.0
	        String vendor = importEntity.getVendor();
	        if (vendor.endsWith(".0")) {
	            vendor = vendor.substring(0, vendor.length() - 2);
	        }
	        info.setVendorNumber(vendor);
	        vendor = VendorPDCLMapper.getVendorName(vendor);
	        info.setVendor(vendor);
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
	        dataEntries.add(info);
	        int currentProgress = progress.incrementAndGet();
	         long endTime = System.currentTimeMillis(); // 记录方法结束执行的时间
	         long duration = endTime - startTime; // 计算方法执行时间
	        System.out.println("当前进度: " + currentProgress + "/" + importEntities.size()+";"+"平均时间："+duration/currentProgress + " 毫秒" + " 总时间：" + duration/1000 + " 秒" );
	        
	    });
	    System.out.println("检查数据一致性和建立查找索引中，请稍等");
	    dataEntryRepository.saveAll(dataEntries);
	}
	public String getTypeByPN(String PN) {
	    String sql = "SELECT TOP 1 type FROM data_entry WHERE product_number = ?";
	    List<String> types = jdbcTemplate.query(sql, new Object[] { PN }, (resultSet, rowNum) -> {
	        return resultSet.getString("type");
	    });
	    if (types.isEmpty()) {
	        return null;
	    } else {
	        return types.get(0);
	    }
	}


	public BaseHttpResponse<List<Integer>> getTotalTB(String vendor, String pdcl, String type, String yearMonth) {
		int notNullCount = 0;
		YearMonth curMonth = YearMonth.parse(yearMonth, DateTimeFormatter.ofPattern("yyyy-MM"));
	    YearMonth prevMonth = curMonth.minusMonths(1);//前一月的年月值
	    int month = curMonth.getMonthValue();// 当前月份值
	    int year = curMonth.getYear();// 当前年份值
		BaseHttpResponse<List<Integer>> resp = new BaseHttpResponse<>();
        notNullCount += Stream.of(vendor, pdcl, type).filter(s -> !s.isEmpty()).count();
        if(notNullCount==0) {resp.setSuccess(Collections.emptyList());return resp;}
        //TB聚类
        int counttb = month>6?25-month:13-month;
        String sql = "SELECT ";
	    	for(int i=0;i<counttb;i++) {
	    		sql += "SUM(tb" + i + ") as totalTB" + i + ", ";
	    	}
        sql = sql.substring(0, sql.length() - 2); // Remove the last comma and space
        sql += " FROM data_entry WHERE year_month = ?";
        if (vendor != null) {
            sql += " AND vendor = ?";
        }
        if (type != null) {
            sql += " AND type = ?";
        }
        if (pdcl != null) {
            sql += " AND pdcl = ?";
        }
        List<Object[]> resultstb = jdbcTemplate.query(sql, new Object[]{yearMonth, vendor, type, pdcl}, (resultSet, rowNum) -> {
        	
            Object[] row = new Object[counttb];
    	    	for(int i=0;i<counttb;i++) {
                row[i] = resultSet.getInt("totalTB" + i);
            }
            return row;
        });

/*        List<Integer> outputtb = (resultstb.isEmpty()) ? Collections.emptyList() : Arrays.asList(resultstb.get(0));
        sql = "SELECT ";
        if(month>6) {
	    	for(int i=0;i<month-1;i++) {
	    		sql += "SUM(soldInfo" + i + ") as totalSOLD" + i + ", ";
	    	}
	    	}
	    else {
	    	for(int i=0;i<month+11;i++) {
	    		sql += "SUM(soldInfo" + i + ") as totalSOLD" + i + ", ";
	    	}
	    };
	    List<Object[]> resultssold = jdbcTemplate.query(sql, new Object[]{yearMonth}, (resultSet, rowNum) -> {
            Object[] row = new Object[25];
            for (int i = 0; i < 25; i++) {
                row[i] = resultSet.getInt("totalTB" + i);
            }
            return row;
        });
	    List<Integer> outputsold = (resultssold.isEmpty()) ? Collections.emptyList() : Arrays.asList(resultssold.get(0));
	    
        List<Integer> combined = new ArrayList<>(outputtb);
        combined.addAll(outputsold);

        resp.setSuccess(combined);*/
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
		BaseHttpResponse<List<Integer>> resp = new BaseHttpResponse<>();/*
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
	     List<Integer> combined = Stream.concat(outputgr.stream(), outputpp.stream()).collect(Collectors.toList());

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
	     List<Integer> lastcombined = Stream.concat(outputgr.stream(), outputpp.stream()).collect(Collectors.toList());
	     lastcombined = Stream.concat(lastcombined.stream(), combined.stream()).collect(Collectors.toList());
		    resp.setSuccess(lastcombined);*/
	    return resp;
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
