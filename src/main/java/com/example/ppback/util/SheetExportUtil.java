package com.example.ppback.util;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import com.alibaba.excel.util.MapUtils;
import com.example.ppback.service.SummaryDataService;



@Component
public class SheetExportUtil{
	
	@Autowired SummaryDataService sdService ;
	private static SheetExportUtil SheetExportUtil;
	@Autowired MongoTemplate mongoTemplate; 
	public Map summary1(String yearMonth,String vendor,String pdcl,String type) {
		Map<String, Object> map = MapUtils.newHashMap();
		YearMonth curMonth = YearMonth.parse(yearMonth, DateTimeFormatter.ofPattern("yyyy-MM"));
		 int month = curMonth.getMonthValue();// 当前月份值
		 int Year = curMonth.getYear();// 当前年份值
		 int YearSimple = Year - (Year/100)*100;
		 YearMonth prevMonth = curMonth.minusMonths(1);//前一月的年月值
		 String prevYearMonth = prevMonth.toString();
		 int monthLast = prevMonth.getMonthValue();// 前一月月份值
		 int yearLast = prevMonth.getYear();// 前一月年份值
		 int YearMinus1 = Year - 1;
		 int YearMinus1Simple = YearMinus1 - (YearMinus1/100)*100;
		 String Month = (month>9)?(""+month):("0"+month);
		 String MonthMinus1 = (monthLast>9)?(""+monthLast):("0"+monthLast);
		 String Vendor = vendor;
		 String PDCL = pdcl;
		 String Type = type;
		 List<Integer> TB = sdService.getTB(yearMonth, Vendor, PDCL, Type);
		 List<Integer> PP = sdService.getPP(yearMonth, Vendor, PDCL, Type);
		 List<Integer> STOCK = sdService.getSTOCK(yearMonth, Vendor, PDCL, Type);
		 List<Integer> TBprev = sdService.getTB(prevYearMonth, Vendor, PDCL, Type);
		 List<Integer> PPprev = sdService.getPP(prevYearMonth, Vendor, PDCL, Type);
		 List<Integer> STOCKprev = sdService.getSTOCK(prevYearMonth, Vendor, PDCL, Type);
		 map.put("Year", Year);
		 map.put("YearSimple", YearSimple);
		 map.put("YearMinus1", YearMinus1);
		 map.put("YearMinus1Simple", YearMinus1Simple);
		 map.put("Month", Month);
		 map.put("MonthMinus1", MonthMinus1);
		 map.put("vendor", Vendor);
		 map.put("PDCL", PDCL);
		 map.put("type", Type);
		 
		 
		 for (int i = 0; i <= 11; i++) {
			    map.put("TBMinus1Last" + i, TBprev.get(i));
			}
		 for (int i = 0; i <= 11; i++) {
			    map.put("TBMinus1" + i, TB.get(i));
			}
		 for (int i = 12; i <= 23; i++) {
			    map.put("TBLast" + i, TBprev.get(i));
			}
		 for (int i = 12; i <= 23; i++) {
			    map.put("TB" + i, TB.get(i));
			}
		 double TBMinus1Total = TB.stream().limit(12).mapToInt(Integer::intValue).sum();
		 double TBTotal =  TB.subList(12, 24).stream().mapToInt(Integer::intValue).sum();
		 map.put("TBMinus1LastHY0",TBprev.stream().limit(6).mapToInt(Integer::intValue).sum());
		 map.put("TBMinus1LastHY1",TBprev.subList(6, 12).stream().mapToInt(Integer::intValue).sum());
		 map.put("TBMinus1LastTotal",TBprev.stream().limit(12).mapToInt(Integer::intValue).sum());
		 map.put("TBLastHY0",TBprev.subList(12, 18).stream().mapToInt(Integer::intValue).sum());
		 map.put("TBLastHY1",TBprev.subList(18, 24).stream().mapToInt(Integer::intValue).sum());
		 map.put("TBLastTotal",TBprev.subList(12, 24).stream().mapToInt(Integer::intValue).sum());
		 map.put("TBMinus1HY0",TB.stream().limit(6).mapToInt(Integer::intValue).sum());
		 map.put("TBMinus1HY1",TB.subList(6, 12).stream().mapToInt(Integer::intValue).sum());
		 map.put("TBMinus1Total",TBMinus1Total);
		 map.put("TBHY0",TB.subList(12, 18).stream().mapToInt(Integer::intValue).sum());
		 map.put("TBHY1",TB.subList(18, 24).stream().mapToInt(Integer::intValue).sum());
		 map.put("TBTotal",TBTotal);
		 String TBDivideTBLast = (TBTotal / TBMinus1Total - 1)*100 + "%";
		 map.put("TBDivideTBLast",TBDivideTBLast);
		 
		 
		 for (int i = 0; i <= 11; i++) {
			    map.put("STOCKMinus1" + i, STOCKprev.get(i));
			}
		 for (int i = 12; i <= 23; i++) {
			    map.put("STOCK" + i, STOCK.get(i));
			}
		 
		 
		 for (int i = 0; i <= 11; i++) {
			    map.put("PPMinus1Last" + i, PPprev.get(i));
			}
			for (int i = 0; i <= 11; i++) {
			    map.put("PPMinus1" + i, PP.get(i));
			}
			for (int i = 12; i <= 23; i++) {
			    map.put("PPLast" + i, PPprev.get(i));
			}
			for (int i = 12; i <= 23; i++) {
			    map.put("PP" + i, PP.get(i));
			}
			double PPMinus1Total = PP.stream().limit(12).mapToInt(Integer::intValue).sum();
			double PPTotal =  PP.subList(12, 24).stream().mapToInt(Integer::intValue).sum();
			map.put("PPMinus1LastHY0",PPprev.stream().limit(6).mapToInt(Integer::intValue).sum());
			map.put("PPMinus1LastHY1",PPprev.subList(6, 12).stream().mapToInt(Integer::intValue).sum());
			map.put("PPMinus1LastTotal",PPprev.stream().limit(12).mapToInt(Integer::intValue).sum());
			map.put("PPLastHY0",PPprev.subList(12, 18).stream().mapToInt(Integer::intValue).sum());
			map.put("PPLastHY1",PPprev.subList(18, 24).stream().mapToInt(Integer::intValue).sum());
			map.put("PPLastTotal",PPprev.subList(12, 24).stream().mapToInt(Integer::intValue).sum());
			map.put("PPMinus1HY0",PP.stream().limit(6).mapToInt(Integer::intValue).sum());
			map.put("PPMinus1HY1",PP.subList(6, 12).stream().mapToInt(Integer::intValue).sum());
			map.put("PPMinus1Total",PPMinus1Total);
			map.put("PPHY0",PP.subList(12, 18).stream().mapToInt(Integer::intValue).sum());
			map.put("PPHY1",PP.subList(18, 24).stream().mapToInt(Integer::intValue).sum());
			map.put("PPTotal",PPTotal);
			String PPDividePPLast = (PPTotal / PPMinus1Total - 1) * 100 + "%";
			map.put("PPDividePPLast",PPDividePPLast);

		 
		return map;
	}
	@PostConstruct 
    public void init() {
		SheetExportUtil = this;
		SheetExportUtil.sdService = this.sdService;
	}
}