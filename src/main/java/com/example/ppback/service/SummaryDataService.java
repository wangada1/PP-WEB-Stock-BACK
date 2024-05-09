package com.example.ppback.service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.DriverManager;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.alibaba.excel.util.ListUtils;
import com.example.ppback.model.GRDataAggregatedResult;
import com.example.ppback.model.PPDataAggregatedResult;
import com.example.ppback.model.SOLDDataAggregatedResult;
import com.example.ppback.model.STOCKDataAggregatedResult;
import com.example.ppback.model.SupplyDataAggregatedResult;
import com.example.ppback.model.TBDataAggregatedResult;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Component
public class SummaryDataService{
	@Autowired TBDataAggregatedResult tbDataAggregatedResult;
	@Autowired SOLDDataAggregatedResult soldDataAggregatedResult;
	@Autowired PPDataAggregatedResult ppDataAggregatedResult;
	@Autowired GRDataAggregatedResult grDataAggregatedResult;
	@Autowired STOCKDataAggregatedResult stockDataAggregatedResult;
	@Autowired SupplyDataAggregatedResult supplyDataAggregatedResult;
	@Autowired JdbcTemplate jdbcTemplate;
	public List<Integer> getTB(String yearMonth,String vendor,String pdcl,String type){
		int notNullCount = 0;
		YearMonth curMonth = YearMonth.parse(yearMonth, DateTimeFormatter.ofPattern("yyyy-MM"));
	    YearMonth prevMonth = curMonth.minusMonths(1);//前一月的年月值
	    int month = curMonth.getMonthValue();// 当前月份值
	    int year = curMonth.getYear();// 当前年份值
	    //TB聚类
        int counttb = month>6?25-month:13-month;
        String sql = "SELECT ";
            for (int i = 0; i < counttb; i++) {
                sql += "SUM(tb" + i + ") as totalTB" + i + ", ";
        } 
        sql = sql.substring(0, sql.length() - 2); // Remove the last comma and space
        sql += " FROM data_entry WHERE year_month = ?";
        // Add conditions for vendor, pdcl, and type if they are not null
        if (vendor != "") {
            sql += " AND vendor = '" + vendor + "'";
        }
        if (pdcl != "") {
            sql += " AND pdcl = '" + pdcl+ "'";
        }
        if (type != "") {
            sql += " AND type = '" + type+ "'";
        }

        List<Object[]> resultstb = jdbcTemplate.query(sql, new Object[]{yearMonth}, (resultSet, rowNum) -> {
        	
            Object[] row = new Object[counttb];
    	    	for(int i=0;i<counttb;i++) {
                row[i] = resultSet.getInt("totalTB" + i);
            }
            return row;
        });
      //SOLD聚类
        int countsold = month>6?month-1:month+11;
        sql = "SELECT ";
            for (int i = 0; i < countsold; i++) {
                sql += "SUM(sold_info" + i + ") as totalSOLD" + i + ", ";
        } 
        sql = sql.substring(0, sql.length() - 2); // Remove the last comma and space
        sql += " FROM sold_data_entry WHERE year_month = ?";
        // Add conditions for vendor, pdcl, and type if they are not null
        if (vendor != "") {
            sql += " AND vendor = '" + vendor + "'";
        }
        if (pdcl != "") {
            sql += " AND pdcl = '" + pdcl+ "'";
        }
        if (type != "") {
            sql += " AND type = '" + type+ "'";
        }

        List<Object[]> resultssold = jdbcTemplate.query(sql, new Object[]{yearMonth}, (resultSet, rowNum) -> {
        	
            Object[] row = new Object[countsold];
    	    	for(int i=0;i<countsold;i++) {
                row[i] = resultSet.getInt("totalSOLD" + i);
            }
            return row;
        });
      //补充数据
        //对于month<7，补充12个月数据到最后，是明年的PP
        //对于month>6,补充12个月数据到最前面，是去年的PP,目前这个是12个0
        sql = "SELECT ";
        if(month<7){
            for(int i=13-month;i<19;i++) {
            	sql += "SUM(tb" + i + ") as totalSUPPLY" + i + ", ";
            }
        };
        sql = sql.substring(0, sql.length() - 2); // Remove the last comma and space
        sql += " FROM data_entry WHERE year_month = ?";
        if (vendor != "") {
            sql += " AND vendor = '" + vendor + "'";
        }
        if (pdcl != "") {
            sql += " AND pdcl = '" + pdcl+ "'";
        }
        if (type != "") {
            sql += " AND type = '" + type+ "'";
        }
        
        List<Object[]> resultssupply = jdbcTemplate.query(sql, new Object[]{yearMonth}, (resultSet, rowNum) -> {
        	
            Object[] row = new Object[6+month];
    	    	for(int i=13-month;i<19;i++) {
                row[i-13+month] = resultSet.getInt("totalSUPPLY" + i);
            }
            return row;
        });
      //缝合输出 
        List<Integer> outputtb = Arrays.stream(resultstb.get(0))
                .map(obj -> (Integer) obj)
                .collect(Collectors.toList());
        List<Integer> outputsold = Arrays.stream(resultssold.get(0))
                .map(obj -> (Integer) obj)
                .collect(Collectors.toList());
        List<Integer> outputsupply = Arrays.stream(resultssupply.get(0))
                .map(obj -> (Integer) obj)
                .collect(Collectors.toList());
        List<Integer> combined = Stream.concat(outputsold.stream(), outputtb.stream()).collect(Collectors.toList());
        if(month<7) {
            List<Integer> zeros = Collections.nCopies(6-month, 0);
            outputsupply.addAll(zeros); 
            combined = Stream.concat(combined.stream(), outputsupply.stream()).collect(Collectors.toList());;
        }
        else {
            outputsupply = Collections.nCopies(12, 0);
            combined = Stream.concat(outputsupply.stream(), combined.stream()).collect(Collectors.toList());;
        }
        return combined;
			     }
	
	 public List<Integer> getPP(String yearMonth, String vendor, String pdcl, String type) {
	        // 定义筛选规则
		 int notNullCount = 0;
			YearMonth curMonth = YearMonth.parse(yearMonth, DateTimeFormatter.ofPattern("yyyy-MM"));
		    YearMonth prevMonth = curMonth.minusMonths(1);//前一月的年月值
		    int month = curMonth.getMonthValue();// 当前月份值
		    int year = curMonth.getYear();// 当前年份值
		    //PP聚类
	        int countpp = month>6?25-month:13-month;
	        String sql = "SELECT ";
	            for (int i = 0; i < countpp; i++) {
	                sql += "SUM(pp" + i + ") as totalPP" + i + ", ";
	        } 
	        sql = sql.substring(0, sql.length() - 2); // Remove the last comma and space
	        sql += " FROM data_entry WHERE year_month = ?";
	        // Add conditions for vendor, pdcl, and type if they are not null
	        if (vendor != "") {
	            sql += " AND vendor = '" + vendor + "'";
	        }
	        if (pdcl != "") {
	            sql += " AND pdcl = '" + pdcl+ "'";
	        }
	        if (type != "") {
	            sql += " AND type = '" + type+ "'";
	        }

	        List<Object[]> resultspp = jdbcTemplate.query(sql, new Object[]{yearMonth}, (resultSet, rowNum) -> {
	        	
	            Object[] row = new Object[countpp];
	    	    	for(int i=0;i<countpp;i++) {
	                row[i] = resultSet.getInt("totalPP" + i);
	            }
	            return row;
	        });
	      //GR聚类
	        int countgr = month>6?month-1:month+11;
	        sql = "SELECT ";
	            for (int i = 0; i < countgr; i++) {
	                sql += "SUM(gr_info" + i + ") as totalGR" + i + ", ";
	        } 
	        sql = sql.substring(0, sql.length() - 2); // Remove the last comma and space
	        sql += " FROM gr_data_entry WHERE year_month = ?";
	        // Add conditions for vendor, pdcl, and type if they are not null
	        if (vendor != "") {
	            sql += " AND vendor = '" + vendor + "'";
	        }
	        if (pdcl != "") {
	            sql += " AND pdcl = '" + pdcl+ "'";
	        }
	        if (type != "") {
	            sql += " AND type = '" + type+ "'";
	        }

	        List<Object[]> resultsgr = jdbcTemplate.query(sql, new Object[]{yearMonth}, (resultSet, rowNum) -> {
	        	
	            Object[] row = new Object[countgr];
	    	    	for(int i=0;i<countgr;i++) {
	                row[i] = resultSet.getInt("totalGR" + i);
	            }
	            return row;
	        });
	      //补充数据
            //对于month<7，补充12个月数据到最后，是明年的PP
            //对于month>6,补充12个月数据到最前面，是去年的PP,目前这个是12个0
	        sql = "SELECT ";
	        if(month<7){
                for(int i=13-month;i<19;i++) {
                	sql += "SUM(pp" + i + ") as totalSUPPLY" + i + ", ";
                }
            };
            sql = sql.substring(0, sql.length() - 2); // Remove the last comma and space
            sql += " FROM data_entry WHERE year_month = ?";
            if (vendor != "") {
	            sql += " AND vendor = '" + vendor + "'";
	        }
	        if (pdcl != "") {
	            sql += " AND pdcl = '" + pdcl+ "'";
	        }
	        if (type != "") {
	            sql += " AND type = '" + type+ "'";
	        }
	        
            List<Object[]> resultssupply = jdbcTemplate.query(sql, new Object[]{yearMonth}, (resultSet, rowNum) -> {
	        	
	            Object[] row = new Object[6+month];
	    	    	for(int i=13-month;i<19;i++) {
	                row[i-13+month] = resultSet.getInt("totalSUPPLY" + i);
	            }
	            return row;
	        });
          //缝合输出 
            List<Integer> outputpp = Arrays.stream(resultspp.get(0))
                    .map(obj -> (Integer) obj)
                    .collect(Collectors.toList());
            List<Integer> outputgr = Arrays.stream(resultsgr.get(0))
                    .map(obj -> (Integer) obj)
                    .collect(Collectors.toList());
            List<Integer> outputsupply = Arrays.stream(resultssupply.get(0))
                    .map(obj -> (Integer) obj)
                    .collect(Collectors.toList());
            List<Integer> combined = Stream.concat(outputgr.stream(), outputpp.stream()).collect(Collectors.toList());
            if(month<7) {
                List<Integer> zeros = Collections.nCopies(6-month, 0);
                outputsupply.addAll(zeros); 
                combined = Stream.concat(combined.stream(), outputsupply.stream()).collect(Collectors.toList());;
            }
            else {
                outputsupply = Collections.nCopies(12, 0);
                combined = Stream.concat(outputsupply.stream(), combined.stream()).collect(Collectors.toList());;
            }
	        return combined;
	    }


	
	
	public List<Integer> getSTOCK(String yearMonth,String vendor,String pdcl,String type){
		 // 定义筛选规则
		 int notNullCount = 0;
			YearMonth curMonth = YearMonth.parse(yearMonth, DateTimeFormatter.ofPattern("yyyy-MM"));
		    YearMonth prevMonth = curMonth.minusMonths(1);//前一月的年月值
		    int month = curMonth.getMonthValue();// 当前月份值
		    int year = curMonth.getYear();// 当前年份值
		    List<Integer> output = new ArrayList<>();
		  //Stock聚类   
		    int countstock = month>6?month-1:month+11;
	        String sql = "SELECT ";
	            for (int i = 0; i < countstock; i++) {
	                sql += "SUM(stock_info" + i + ") as totalSTOCK" + i + ", ";
	        } 
	        sql = sql.substring(0, sql.length() - 2); // Remove the last comma and space
	        sql += " FROM stock_entry WHERE year_month = ?";
	        // Add conditions for vendor, pdcl, and type if they are not null
	        if (vendor != "") {
	            sql += " AND vendor = '" + vendor + "'";
	        }
	        if (pdcl != "") {
	            sql += " AND pdcl = '" + pdcl+ "'";
	        }
	        if (type != "") {
	            sql += " AND type = '" + type+ "'";
	        }

	        List<Object[]> resultsstock = jdbcTemplate.query(sql, new Object[]{yearMonth}, (resultSet, rowNum) -> {
	        	
	            Object[] row = new Object[countstock];
	    	    	for(int i=0;i<countstock;i++) {
	                row[i] = resultSet.getInt("totalSTOCK" + i);
	            }
	            return row;
	        });
	        List<Integer> outputstock = Arrays.stream(resultsstock.get(0))
                    .map(obj -> (Integer) obj)
                    .collect(Collectors.toList());
	      //补充数据+缝合输出
	        List<Integer> combined = null;
	      // SUPPLY补充，小于7右边补25-month个0，大于6左边补12个0，右边补25-month个0
	        if(month>6) {
				List<Integer> outputsupply = Collections.nCopies(12, 0);
				combined = Stream.concat(outputsupply.stream(), outputstock.stream()).collect(Collectors.toList());
				 outputsupply= Collections.nCopies(25-month, 0);
				 combined = Stream.concat(combined.stream(), outputsupply.stream()).collect(Collectors.toList());
				}
				
			else {
				List<Integer> outputsupply= Collections.nCopies(25-month, 0);
				combined = Stream.concat(outputstock.stream(), outputsupply.stream()).collect(Collectors.toList());
				}
	        return combined;
		//返回36个数据，分别是去年，今年和明年的stock
		
	}
	
}