package com.example.ppback.model;

import java.util.List;
import java.util.ArrayList;
import lombok.Data;

@Data
public class STOCKDataAggregatedResult {
	
	private int totalSTOCK0;
	private int totalSTOCK1;
	private int totalSTOCK2;
	private int totalSTOCK3;
	private int totalSTOCK4;
	private int totalSTOCK5;
	private int totalSTOCK6;
	private int totalSTOCK7;
	private int totalSTOCK8;
	private int totalSTOCK9;
	private int totalSTOCK10;
	private int totalSTOCK11;
	private int totalSTOCK12;
	private int totalSTOCK13;
	private int totalSTOCK14;
	private int totalSTOCK15;
	private int totalSTOCK16;
	private int totalSTOCK17;

	
	private List<Integer> data;
	private String yearMonth;
	private String pdcl;
	private String vendor;
	private String type;
    
    // Getters and setters
    
	public List<Integer> iterator(int month) {
	    List<Integer> result = new ArrayList<>();
	    if (month < 7) {
	        for (int i = 0; i < 11 + month; i++) {
	            switch (i) {
	                case 0: result.add(totalSTOCK0); break;
	                case 1: result.add(totalSTOCK1); break;
	                case 2: result.add(totalSTOCK2); break;
	                case 3: result.add(totalSTOCK3); break;
	                case 4: result.add(totalSTOCK4); break;
	                case 5: result.add(totalSTOCK5); break;
	                case 6: result.add(totalSTOCK6); break;
	                case 7: result.add(totalSTOCK7); break;
	                case 8: result.add(totalSTOCK8); break;
	                case 9: result.add(totalSTOCK9); break;
	                case 10: result.add(totalSTOCK10); break;
	                case 11: result.add(totalSTOCK11); break;
	                case 12: result.add(totalSTOCK12); break;
	                case 13: result.add(totalSTOCK13); break;
	                case 14: result.add(totalSTOCK14); break;
	                case 15: result.add(totalSTOCK15); break;
	                case 16: result.add(totalSTOCK16); break;
	                case 17: result.add(totalSTOCK17); break;
	                default: break;
	            }
	        }
	    } else {
	        for (int i = 0; i < month - 1; i++) {
	            switch (i) {
	                case 0: result.add(totalSTOCK0); break;
	                case 1: result.add(totalSTOCK1); break;
	                case 2: result.add(totalSTOCK2); break;
	                case 3: result.add(totalSTOCK3); break;
	                case 4: result.add(totalSTOCK4); break;
	                case 5: result.add(totalSTOCK5); break;
	                case 6: result.add(totalSTOCK6); break;
	                case 7: result.add(totalSTOCK7); break;
	                case 8: result.add(totalSTOCK8); break;
	                case 9: result.add(totalSTOCK9); break;
	                case 10: result.add(totalSTOCK10); break;
	                case 11: result.add(totalSTOCK11); break;
	                case 12: result.add(totalSTOCK12); break;
	                case 13: result.add(totalSTOCK13); break;
	                case 14: result.add(totalSTOCK14); break;
	                case 15: result.add(totalSTOCK15); break;
	                case 16: result.add(totalSTOCK16); break;
	                case 17: result.add(totalSTOCK17); break;
	                default: break;
	            }
	        }
	    }
	    return result;
	}

}
