package com.example.ppback.model;

import java.util.List;
import java.util.ArrayList;
import lombok.Data;

@Data
public class SOLDDataAggregatedResult {
	
	private int totalSOLD0;
	private int totalSOLD1;
	private int totalSOLD2;
	private int totalSOLD3;
	private int totalSOLD4;
	private int totalSOLD5;
	private int totalSOLD6;
	private int totalSOLD7;
	private int totalSOLD8;
	private int totalSOLD9;
	private int totalSOLD10;
	private int totalSOLD11;
	private int totalSOLD12;
	private int totalSOLD13;
	private int totalSOLD14;
	private int totalSOLD15;
	private int totalSOLD16;
	private int totalSOLD17;
	private int totalSOLD18;
	
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
		        case 0: result.add(totalSOLD0); break;
	            case 1: result.add(totalSOLD1); break;
	            case 2: result.add(totalSOLD2); break;
	            case 3: result.add(totalSOLD3); break;
	            case 4: result.add(totalSOLD4); break;
	            case 5: result.add(totalSOLD5); break;
	            case 6: result.add(totalSOLD6); break;
	            case 7: result.add(totalSOLD7); break;
	            case 8: result.add(totalSOLD8); break;
	            case 9: result.add(totalSOLD9); break;
	            case 10: result.add(totalSOLD10); break;
	            case 11: result.add(totalSOLD11); break;
	            case 12: result.add(totalSOLD12); break;
	            case 13: result.add(totalSOLD13); break;
	            case 14: result.add(totalSOLD14); break;
	            case 15: result.add(totalSOLD15); break;
	            case 16: result.add(totalSOLD16); break;
	            case 17: result.add(totalSOLD17); break;
	            case 18: result.add(totalSOLD18); break;
	            default: break;
		        }
		    }
		} else {
		    for (int i = 0; i < month - 1; i++) {
		        switch (i) {
		        case 0: result.add(totalSOLD0); break;
	            case 1: result.add(totalSOLD1); break;
	            case 2: result.add(totalSOLD2); break;
	            case 3: result.add(totalSOLD3); break;
	            case 4: result.add(totalSOLD4); break;
	            case 5: result.add(totalSOLD5); break;
	            case 6: result.add(totalSOLD6); break;
	            case 7: result.add(totalSOLD7); break;
	            case 8: result.add(totalSOLD8); break;
	            case 9: result.add(totalSOLD9); break;
	            case 10: result.add(totalSOLD10); break;
	            case 11: result.add(totalSOLD11); break;
	            case 12: result.add(totalSOLD12); break;
	            case 13: result.add(totalSOLD13); break;
	            case 14: result.add(totalSOLD14); break;
	            case 15: result.add(totalSOLD15); break;
	            case 16: result.add(totalSOLD16); break;
	            case 17: result.add(totalSOLD17); break;
	            case 18: result.add(totalSOLD18); break;
	            default: break;
		        }
		    }
		}
		return result;
	}
}
