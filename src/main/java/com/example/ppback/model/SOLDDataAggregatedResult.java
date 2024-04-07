package com.example.ppback.model;

import java.util.List;
import java.util.ArrayList;
import lombok.Data;

@Data
public class SOLDDataAggregatedResult {
	
    private int totalSold0;
    private int totalSold1;
    private int totalSold2;
    private int totalSold3;
    private int totalSold4;
    private int totalSold5;
    private int totalSold6;
    private int totalSold7;
    private int totalSold8;
    private int totalSold9;
    private int totalSold10;
    private int totalSold11;
    private int totalSold12;
    private int totalSold13;
    private int totalSold14;
    private int totalSold15;
    private int totalSold16;
    private int totalSold17;
    private int totalSold18;
	
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
		            case 0: result.add(totalSold0); break;
		            case 1: result.add(totalSold1); break;
		            case 2: result.add(totalSold2); break;
		            case 3: result.add(totalSold3); break;
		            case 4: result.add(totalSold4); break;
		            case 5: result.add(totalSold5); break;
		            case 6: result.add(totalSold6); break;
		            case 7: result.add(totalSold7); break;
		            case 8: result.add(totalSold8); break;
		            case 9: result.add(totalSold9); break;
		            case 10: result.add(totalSold10); break;
		            case 11: result.add(totalSold11); break;
		            case 12: result.add(totalSold12); break;
		            case 13: result.add(totalSold13); break;
		            case 14: result.add(totalSold14); break;
		            case 15: result.add(totalSold15); break;
		            case 16: result.add(totalSold16); break;
		            case 17: result.add(totalSold17); break;
		            case 18: result.add(totalSold18); break;
		            default: break;
		        }
		    }
		} else {
		    for (int i = 0; i < month - 1; i++) {
		        switch (i) {
		            case 0: result.add(totalSold0); break;
		            case 1: result.add(totalSold1); break;
		            case 2: result.add(totalSold2); break;
		            case 3: result.add(totalSold3); break;
		            case 4: result.add(totalSold4); break;
		            case 5: result.add(totalSold5); break;
		            case 6: result.add(totalSold6); break;
		            case 7: result.add(totalSold7); break;
		            case 8: result.add(totalSold8); break;
		            case 9: result.add(totalSold9); break;
		            case 10: result.add(totalSold10); break;
		            case 11: result.add(totalSold11); break;
		            case 12: result.add(totalSold12); break;
		            case 13: result.add(totalSold13); break;
		            case 14: result.add(totalSold14); break;
		            case 15: result.add(totalSold15); break;
		            case 16: result.add(totalSold16); break;
		            case 17: result.add(totalSold17); break;
		            case 18: result.add(totalSold18); break;
		            default: break;
		        }
		    }
		}
		return result;
	}
}
