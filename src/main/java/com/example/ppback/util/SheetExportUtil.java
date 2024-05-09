package com.example.ppback.util;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.excel.util.MapUtils;
import com.example.ppback.service.SummaryDataService;



@Component
public class SheetExportUtil{
	
	@Autowired SummaryDataService sdService ;
	private static SheetExportUtil SheetExportUtil;
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
		 int YearSimplePlus1 = YearSimple + 1;//It is not right after 2099.
		 String Month = (month>9)?(""+month):("0"+month);
		 String MonthMinus1 = (monthLast>9)?(""+monthLast):("0"+monthLast);
		 String Vendor = vendor;
		 String PDCL = pdcl;
		 String Type = type;
		 List<Integer> PP = new ArrayList<>(sdService.getPP(yearMonth, Vendor, PDCL, Type));
		 List<Integer> TB = new ArrayList<>(sdService.getTB(yearMonth, Vendor, PDCL, Type));
		 if(month>=7) {//对TB和PP进行补丁，对这两个的前12个数据，补充上当年1~6月中有值的数据
			 for(int i=0;i<6;i++) {
				 List<Integer> TBsupply = new ArrayList<>(sdService.getTB(Year+"-0"+i, Vendor, PDCL, Type));
						 TBsupply = TBsupply.subList(0,12);
				 List<Integer> PPsupply = new ArrayList<>(sdService.getPP(Year+"-0"+i, Vendor, PDCL, Type));
						 PPsupply = PPsupply.subList(0,12);	 
						 int sumCheck = TBsupply.stream().mapToInt(Integer::intValue).sum() + PPsupply.stream().mapToInt(Integer::intValue).sum();
						 if(sumCheck!=0) {
							 TBsupply.addAll(TB.subList(12,36));
							 TB = TBsupply;
							 PPsupply.addAll(PP.subList(12,36));
							 PP = PPsupply;
							 break;
						 }
			 }
		 };//如果找不到，就还是返回0
		 List<Integer> STOCK = sdService.getSTOCK(yearMonth, Vendor, PDCL, Type);
		 List<Integer> TBprev = new ArrayList<>();
		 List<Integer> PPprev = new ArrayList<>();
		 if(month==1) {
			 TBprev = new ArrayList<>(TB.subList(0, 11));
			 List<Integer> middleTB = sdService.getTB(prevYearMonth, Vendor, PDCL, Type).subList(23,36);
			 List<Integer> modifiableList = new ArrayList<>(middleTB);
		 	 TBprev.addAll(modifiableList);
		 	TBprev.addAll(Collections.nCopies(12, 0));
		 	PPprev = new ArrayList<>(PP.subList(0, 11));
		 	List<Integer> middlePP = sdService.getPP(prevYearMonth, Vendor, PDCL, Type).subList(23,36);
		 	modifiableList = new ArrayList<>(middlePP);
		 	 PPprev.addAll(modifiableList);
		 	PPprev.addAll(Collections.nCopies(12, 0));
		 }
		 else if(month<7) {
			 TBprev = new ArrayList<>(TB.subList(0, month + 10));
			 List<Integer> middleTB = sdService.getTB(prevYearMonth, Vendor, PDCL, Type).subList(month+10,36);
			 List<Integer> modifiableList = new ArrayList<>(middleTB);
		 	 TBprev.addAll(modifiableList);
		 	PPprev = new ArrayList<>(PP.subList(0, month + 10));
		 	List<Integer> middlePP = sdService.getPP(prevYearMonth, Vendor, PDCL, Type).subList(month+10,36);
		 	modifiableList = new ArrayList<>(middlePP);
		 	 PPprev.addAll(modifiableList);
				 }
		 else if(month==7) {
			 TBprev = Collections.nCopies(12, 0);
			 TBprev.addAll(TB.subList(0,5));
			 List<Integer> middleTB = sdService.getTB(prevYearMonth, Vendor, PDCL, Type).subList(17,36);
			 List<Integer> modifiableList = new ArrayList<>(middleTB);
			 TBprev.addAll(modifiableList);
			 PPprev = Collections.nCopies(12, 0);
			 PPprev.addAll(PP.subList(0,5));
			 List<Integer> middlePP = sdService.getPP(prevYearMonth, Vendor, PDCL, Type).subList(17,36);
			 modifiableList = new ArrayList<>(middlePP);
			 PPprev.addAll(modifiableList);
		 }
		 else {
			 TBprev = new ArrayList<>(TB.subList(0,12+month-2));
			 List<Integer> middleTB = sdService.getTB(prevYearMonth, Vendor, PDCL, Type).subList(12+month-2,36);
			 List<Integer> modifiableList = new ArrayList<>(middleTB);
			 TBprev.addAll(modifiableList);
			 PPprev = new ArrayList<>(PP.subList(0,12+month-2));
		 	List<Integer> middlePP = sdService.getPP(prevYearMonth, Vendor, PDCL, Type).subList(12+month-2,36);
		 	modifiableList = new ArrayList<>(middlePP);
		 	PPprev.addAll(modifiableList);
		 }
//		 List<Integer> STOCKprev = sdService.getSTOCK(prevYearMonth, Vendor, PDCL, Type);
		 map.put("Year", Year);
		 map.put("YearSimple", YearSimple);
		 map.put("YearMinus1", YearMinus1);
		 map.put("YearMinus1Simple", YearMinus1Simple);
		 map.put("Month", Month);
		 map.put("MonthMinus1", MonthMinus1);
		 map.put("YearSimplePlus1",YearSimplePlus1);
		 map.put("vendor", Vendor);
		 map.put("PDCL", PDCL);
		 map.put("type", Type);
		 map.put("YearPlus1",Year+1);
		 
		 for (int i = 0; i <= 11; i++) {
			    map.put("TBMinus1Last" + i, TBprev.get(i));
			}
		 for (int i = 0; i <= 11; i++) {
			    map.put("TBMinus1" + i, TB.get(i));
			}
		 for (int i = 12; i <= 23; i++) {
			    map.put("TBLast" + (i-12), TBprev.get(i));
			}
		 for (int i = 12; i <= 23; i++) {
			    map.put("TB" + (i-12), TB.get(i));
			}
		 double TBMinus1Total = TB.stream().limit(12).mapToInt(Integer::intValue).sum();
		 double TBTotal =  TB.subList(12, 24).stream().mapToInt(Integer::intValue).sum();
		 double TBLastTotal = TBprev.subList(12, 24).stream().mapToInt(Integer::intValue).sum();
		 double TBPlus1Total = TB.subList(24, 36).stream().mapToInt(Integer::intValue).sum();
		 double TBPlus1LastTotal = TBprev.subList(24, 36).stream().mapToInt(Integer::intValue).sum();
		 map.put("TBMinus1LastHY0",TBprev.stream().limit(6).mapToInt(Integer::intValue).sum());
		 map.put("TBMinus1LastHY1",TBprev.subList(6, 12).stream().mapToInt(Integer::intValue).sum());
		 map.put("TBMinus1LastTotal",TBprev.stream().limit(12).mapToInt(Integer::intValue).sum());
		 map.put("TBLastHY0",TBprev.subList(12, 18).stream().mapToInt(Integer::intValue).sum());
		 map.put("TBLastHY1",TBprev.subList(18, 24).stream().mapToInt(Integer::intValue).sum());
		 map.put("TBPlus1LastHY0",TBprev.subList(24, 30).stream().mapToInt(Integer::intValue).sum());
		 map.put("TBPlus1LastHY1",TBprev.subList(30, 36).stream().mapToInt(Integer::intValue).sum());
		 map.put("TBPlus1LastTotal",TBPlus1LastTotal);
		 map.put("TBLastTotal",TBLastTotal);
		 map.put("TBMinus1HY0",TB.stream().limit(6).mapToInt(Integer::intValue).sum());
		 map.put("TBMinus1HY1",TB.subList(6, 12).stream().mapToInt(Integer::intValue).sum());
		 map.put("TBMinus1Total",TBMinus1Total);
		 map.put("TBHY0",TB.subList(12, 18).stream().mapToInt(Integer::intValue).sum());
		 map.put("TBHY1",TB.subList(18, 24).stream().mapToInt(Integer::intValue).sum());
		 map.put("TBPlus1HY0",TB.subList(24,30).stream().mapToInt(Integer::intValue).sum());
		 map.put("TBPlus1HY1",TB.subList(30, 36).stream().mapToInt(Integer::intValue).sum());
		 map.put("TBPlus1Total",TBPlus1Total);
		 map.put("TBTotal",TBTotal);
		 String TBDivideTBLast = (TBLastTotal!=0.0)?Math.round((TBTotal / TBLastTotal - 1)*100) + "%":"infinity";
		 String TBPlus1DivideTBPlus1Last = (TBPlus1LastTotal!=0.0)?Math.round((TBPlus1Total / TBPlus1LastTotal - 1)*100) + "%":"infinity";
		 map.put("TBDivideTBLast",TBDivideTBLast);
		 map.put("TBPlus1DivideTBPlus1Last",TBPlus1DivideTBPlus1Last);
		 
		 
		 for (int i = 0; i <= 11; i++) {
			    map.put("STOCKMinus1" + i, STOCK.get(i));
			}
		 for (int i = 12; i <= 23; i++) {
			    map.put("STOCK" + (i-12), STOCK.get(i));
			}
		 
		 
		 for (int i = 0; i <= 11; i++) {
			    map.put("PPMinus1Last" + i, PPprev.get(i));
			}
			for (int i = 0; i <= 11; i++) {
			    map.put("PPMinus1" + i, PP.get(i));
			}
			for (int i = 12; i <= 23; i++) {
			    map.put("PPLast" + (i-12), PPprev.get(i));
			}
			for (int i = 12; i <= 23; i++) {
			    map.put("PP" + (i-12), PP.get(i));
			}
			double PPMinus1Total = PP.stream().limit(12).mapToInt(Integer::intValue).sum();
			double PPTotal =  PP.subList(12, 24).stream().mapToInt(Integer::intValue).sum();
			double PPPlus1Total = PP.subList(24, 36).stream().mapToInt(Integer::intValue).sum();
			double PPPlus1LastTotal = PPprev.subList(24, 36).stream().mapToInt(Integer::intValue).sum();
			double PPLastTotal = PPprev.subList(12, 24).stream().mapToInt(Integer::intValue).sum();
			map.put("PPMinus1LastHY0",PPprev.stream().limit(6).mapToInt(Integer::intValue).sum());
			map.put("PPMinus1LastHY1",PPprev.subList(6, 12).stream().mapToInt(Integer::intValue).sum());
			map.put("PPMinus1LastTotal",PPprev.stream().limit(12).mapToInt(Integer::intValue).sum());
			map.put("PPLastHY0",PPprev.subList(12, 18).stream().mapToInt(Integer::intValue).sum());
			map.put("PPLastHY1",PPprev.subList(18, 24).stream().mapToInt(Integer::intValue).sum());
			map.put("PPLastTotal",PPLastTotal);
			map.put("PPPlus1LastHY0",PPprev.subList(24, 30).stream().mapToInt(Integer::intValue).sum());
			map.put("PPPlus1LastHY1",PPprev.subList(30, 36).stream().mapToInt(Integer::intValue).sum());
			map.put("PPPlus1LastTotal",PPPlus1LastTotal);
			map.put("PPMinus1HY0",PP.stream().limit(6).mapToInt(Integer::intValue).sum());
			map.put("PPMinus1HY1",PP.subList(6, 12).stream().mapToInt(Integer::intValue).sum());
			map.put("PPMinus1Total",PPMinus1Total);
			map.put("PPHY0",PP.subList(12, 18).stream().mapToInt(Integer::intValue).sum());
			map.put("PPHY1",PP.subList(18, 24).stream().mapToInt(Integer::intValue).sum());
			map.put("PPPlus1HY0",PP.subList(24, 30).stream().mapToInt(Integer::intValue).sum());
			map.put("PPPlus1HY1",PP.subList(30, 36).stream().mapToInt(Integer::intValue).sum());
			map.put("PPPlus1Total",PPPlus1Total);
			map.put("PPTotal",PPTotal);
			String PPDividePPMinus1Last = (PPLastTotal!=0.0)?Math.round((PPTotal / PPLastTotal - 1) * 100) + "%":"infinity";
			String PPPlus1DividePPPlus1Last = (PPPlus1LastTotal!=0.0)?Math.round((PPPlus1Total / PPPlus1LastTotal - 1) * 100) + "%":"infinity";
			map.put("PPDividePPMinus1Last",PPDividePPMinus1Last);
			map.put("PPPlus1DividePPPlus1Last",PPPlus1DividePPPlus1Last);
			
			for(int i=24;i<36;i++) {
				map.put("TBPlus1Last"+(i-24),TBprev.get(i));
			}
			for(int i=24;i<36;i++) {
				map.put("TBPlus1"+(i-24),TB.get(i));
			}
			for(int i=24;i<36;i++) {
				map.put("STOCKPlus1"+(i-24),STOCK.get(i));
			}
			for(int i=24;i<36;i++) {
				map.put("PPPlus1Last"+(i-24),PPprev.get(i));
			}
			for(int i=24;i<36;i++) {
				map.put("PPPlus1"+(i-24),PP.get(i));
			}
			
			map.put("PPLastHY0",PPprev.subList(12, 18).stream().mapToInt(Integer::intValue).sum());
			map.put("PPLastHY1",PPprev.subList(18, 24).stream().mapToInt(Integer::intValue).sum());
			map.put("PPLastTotal",PPprev.subList(12, 24).stream().mapToInt(Integer::intValue).sum());
			map.put("PPMinus1HY0",PP.stream().limit(6).mapToInt(Integer::intValue).sum());
			map.put("PPMinus1HY1",PP.subList(6, 12).stream().mapToInt(Integer::intValue).sum());
			map.put("PPMinus1Total",PPMinus1Total);
			map.put("PPHY0",PP.subList(12, 18).stream().mapToInt(Integer::intValue).sum());
			map.put("PPHY1",PP.subList(18, 24).stream().mapToInt(Integer::intValue).sum());
			map.put("PPTotal",PPTotal);
		 
			for(int i=0;i<4;i++) {
				map.put("TBMinus1Q"+i,TB.subList(3*i, 3+3*i).stream().mapToInt(Integer::intValue).sum());
			};
			for(int i=0;i<4;i++) {
				map.put("TBQ"+i,TB.subList(12+3*i, 3+12+3*i).stream().mapToInt(Integer::intValue).sum());
			};
			for(int i=0;i<4;i++) {//修改这里的类型bug
				map.put("TBDivideTBMinus1Q"+i,(TB.subList(3*i, 3+3*i).stream().mapToInt(Integer::intValue).sum()!=0.0)
						?Math.round(((double)
						TB.subList(12+3*i, 3+12+3*i).stream().mapToInt(Integer::intValue).sum()/TB.subList(3*i, 3+3*i).stream().mapToInt(Integer::intValue).sum()
						 - 1) * 100) + "%":"infinity");
			};
			for(int i=0;i<2;i++) {
				map.put("TBDivideTBMinus1HY"+i,(TB.subList(6*i, 6+6*i).stream().mapToInt(Integer::intValue).sum()!=0.0)?
						Math.round(((double)
								TB.subList(12+6*i, 6+12+6*i).stream().mapToInt(Integer::intValue).sum()/TB.subList(6*i, 6+6*i).stream().mapToInt(Integer::intValue).sum()
						 - 1) * 100) + "%":"infinity");
			};
			
			for(int i=0;i<4;i++) {
				map.put("PPMinus1Q"+i,PP.subList(3*i, 3+3*i).stream().mapToInt(Integer::intValue).sum());
			};
			for(int i=0;i<4;i++) {
				map.put("PPQ"+i,PP.subList(12+3*i, 3+12+3*i).stream().mapToInt(Integer::intValue).sum());
			};
			for(int i=0;i<4;i++) {
				map.put("PPDividePPMinus1Q"+i,(PP.subList(3*i, 3+3*i).stream().mapToInt(Integer::intValue).sum()!=0.0)?
						Math.round(((double)
						PP.subList(12+3*i, 3+12+3*i).stream().mapToInt(Integer::intValue).sum()/PP.subList(3*i, 3+3*i).stream().mapToInt(Integer::intValue).sum()
						 - 1) * 100) + "%":"infinity");
				
			};
			for(int i=0;i<2;i++) {
				map.put("PPDividePPMinus1HY"+i,(PP.subList(6*i, 6+6*i).stream().mapToInt(Integer::intValue).sum()!=0.0)?
						Math.round(((double)
						PP.subList(12+6*i, 6+12+6*i).stream().mapToInt(Integer::intValue).sum()/PP.subList(6*i, 6+6*i).stream().mapToInt(Integer::intValue).sum()
						 - 1) * 100) + "%":"infinity");
			};
			
			for(int i=0;i<4;i++) {
				map.put("TBMinus1Q"+i+"Percent",(TB.subList(0, 12).stream().mapToInt(Integer::intValue).sum()!=0.0)?
						Math.round(((double)
						TB.subList(3*i, 3*i+3).stream().mapToInt(Integer::intValue).sum()/TB.subList(0, 12).stream().mapToInt(Integer::intValue).sum()
						 ) * 100) + "%":"infinity");
			}
			for(int i=0;i<4;i++) {
				map.put("TBQ"+i+"Percent",(TB.subList(12, 24).stream().mapToInt(Integer::intValue).sum()!=0.0)?
						Math.round(((double)
						TB.subList(12+3*i, 12+3*i+3).stream().mapToInt(Integer::intValue).sum()/TB.subList(12, 24).stream().mapToInt(Integer::intValue).sum()
						) * 100) + "%":"infinity");
			}
			for(int i=0;i<4;i++) {
				map.put("PPMinus1Q"+i+"Percent",(PP.subList(0, 12).stream().mapToInt(Integer::intValue).sum()!=0.0)?
						Math.round(((double)
						PP.subList(3*i, 3*i+3).stream().mapToInt(Integer::intValue).sum()/PP.subList(0, 12).stream().mapToInt(Integer::intValue).sum()
						) * 100) + "%":"infinity");
			}
			for(int i=0;i<4;i++) {
				map.put("PPQ"+i+"Percent",(PP.subList(12, 24).stream().mapToInt(Integer::intValue).sum()!=0.0)?
						Math.round(((double)
						PP.subList(12+3*i, 12+3*i+3).stream().mapToInt(Integer::intValue).sum()/PP.subList(12, 24).stream().mapToInt(Integer::intValue).sum()
						) * 100) + "%":"infinity");
			}
			
			for(int i=0;i<4;i++) {
				map.put("TBPlus1Q"+i,TB.subList(24+3*i, 24+3+3*i).stream().mapToInt(Integer::intValue).sum());
			};
			for(int i=0;i<4;i++) {
				map.put("PPPlus1Q"+i,PP.subList(24+3*i, 24+3+3*i).stream().mapToInt(Integer::intValue).sum());
			};
			for(int i=0;i<4;i++) {
				map.put("TBPlus1Q"+i+"Percent",(TB.subList(24, 36).stream().mapToInt(Integer::intValue).sum()!=0.0)?
						Math.round(((double)
						TB.subList(24+3*i, 24+3*i+3).stream().mapToInt(Integer::intValue).sum()/TB.subList(24, 36).stream().mapToInt(Integer::intValue).sum()
						) * 100) + "%":"infinity");
			}
			for(int i=0;i<4;i++) {
				map.put("PPPlus1Q"+i+"Percent",(PP.subList(24, 36).stream().mapToInt(Integer::intValue).sum()!=0.0)?
						Math.round(((double)
						PP.subList(24+3*i, 24+3*i+3).stream().mapToInt(Integer::intValue).sum()/PP.subList(24, 36).stream().mapToInt(Integer::intValue).sum()
						 ) * 100) + "%":"infinity");
			}
			for(int i=0;i<4;i++) {
				map.put("TBPlus1DivideTBQ"+i,(TB.subList(12+3*i, 12+3+3*i).stream().mapToInt(Integer::intValue).sum()!=0.0)
						?Math.round(((double)
						TB.subList(24+3*i, 3+24+3*i).stream().mapToInt(Integer::intValue).sum()/TB.subList(12+3*i, 12+3+3*i).stream().mapToInt(Integer::intValue).sum()
						 - 1) * 100) + "%":"infinity");
			};
			for(int i=0;i<4;i++) {
				map.put("PPPlus1DividePPQ"+i,(PP.subList(12+3*i, 12+3+3*i).stream().mapToInt(Integer::intValue).sum()!=0.0)
						?Math.round(((double)
						PP.subList(24+3*i, 3+24+3*i).stream().mapToInt(Integer::intValue).sum()/PP.subList(12+3*i, 12+3+3*i).stream().mapToInt(Integer::intValue).sum()
						 - 1) * 100) + "%":"infinity");
			};
			for(int i=0;i<2;i++) {
				map.put("TBPlus1DivideTBHY"+i,(TB.subList(12+6*i, 12+6+6*i).stream().mapToInt(Integer::intValue).sum()!=0.0)?
						Math.round(((double)
								TB.subList(12+12+6*i, 12+6+12+6*i).stream().mapToInt(Integer::intValue).sum()/TB.subList(12+6*i, 12+6+6*i).stream().mapToInt(Integer::intValue).sum()
						 - 1) * 100) + "%":"infinity");
			};
			for(int i=0;i<2;i++) {
				map.put("PPPlus1DividePPHY"+i,(PP.subList(12+6*i, 12+6+6*i).stream().mapToInt(Integer::intValue).sum()!=0.0)?
						Math.round(((double)
								PP.subList(12+12+6*i, 12+6+12+6*i).stream().mapToInt(Integer::intValue).sum()/PP.subList(12+6*i, 12+6+6*i).stream().mapToInt(Integer::intValue).sum()
						 - 1) * 100) + "%":"infinity");
			};
			int quarter = (month-1)/3;
			for(int i=0;i<4;i++) {
				map.put("soldState"+i,"forecast");
				map.put("grState"+i,"forecast");
			}
			for(int i=0;i<quarter;i++) {
				map.put("soldState"+i,"sold");
				map.put("grState"+i,"GR");
			}
			String TBDivideTBMinus1 = (TBMinus1Total!=0.0)?Math.round((TBTotal/TBMinus1Total - 1)*100) + "%":"infinity";
			String PPDividePPMinus1 = (PPMinus1Total!=0.0)?Math.round((PPTotal/PPMinus1Total - 1)*100) + "%":"infinity";
			String TBPlus1DivideTB = (TBTotal!=0.0)?Math.round((TBPlus1Total/TBTotal - 1)*100) + "%":"infinity";
			String PPPlus1DividePP = (PPTotal!=0.0)?Math.round((PPPlus1Total/PPTotal - 1)*100) + "%":"infinity";
			map.put("TBDivideTBMinus1",TBDivideTBMinus1);
			map.put("PPDividePPMinus1",PPDividePPMinus1);
			map.put("TBPlus1DivideTB",TBPlus1DivideTB);
			map.put("PPPlus1DividePP",PPPlus1DividePP);
		return map;
	}
	@PostConstruct 
    public void init() {
		SheetExportUtil = this;
		SheetExportUtil.sdService = this.sdService;
	}
}