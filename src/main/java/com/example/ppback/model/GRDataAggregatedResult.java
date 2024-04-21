package com.example.ppback.model;
import java.util.List;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
 
import lombok.Data;
@Data
@Component
public class GRDataAggregatedResult {
	
    private int totalGR0;
    private int totalGR1;
    private int totalGR2;
    private int totalGR3;
    private int totalGR4;
    private int totalGR5;
    private int totalGR6;
    private int totalGR7;
    private int totalGR8;
    private int totalGR9;
    private int totalGR10;
    private int totalGR11;
    private int totalGR12;
    private int totalGR13;
    private int totalGR14;
    private int totalGR15;
    private int totalGR16;
    private int totalGR17;
    private int totalGR18;
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
		            case 0: result.add(totalGR0); break;
		            case 1: result.add(totalGR1); break;
		            case 2: result.add(totalGR2); break;
		            case 3: result.add(totalGR3); break;
		            case 4: result.add(totalGR4); break;
		            case 5: result.add(totalGR5); break;
		            case 6: result.add(totalGR6); break;
		            case 7: result.add(totalGR7); break;
		            case 8: result.add(totalGR8); break;
		            case 9: result.add(totalGR9); break;
		            case 10: result.add(totalGR10); break;
		            case 11: result.add(totalGR11); break;
		            case 12: result.add(totalGR12); break;
		            case 13: result.add(totalGR13); break;
		            case 14: result.add(totalGR14); break;
		            case 15: result.add(totalGR15); break;
		            case 16: result.add(totalGR16); break;
		            case 17: result.add(totalGR17); break;
		            case 18: result.add(totalGR18); break;
		            default: break;
		        }
		    }
		} else {
		    for (int i = 0; i < month - 1; i++) {
		        switch (i) {
		            case 0: result.add(totalGR0); break;
		            case 1: result.add(totalGR1); break;
		            case 2: result.add(totalGR2); break;
		            case 3: result.add(totalGR3); break;
		            case 4: result.add(totalGR4); break;
		            case 5: result.add(totalGR5); break;
		            case 6: result.add(totalGR6); break;
		            case 7: result.add(totalGR7); break;
		            case 8: result.add(totalGR8); break;
		            case 9: result.add(totalGR9); break;
		            case 10: result.add(totalGR10); break;
		            case 11: result.add(totalGR11); break;
		            case 12: result.add(totalGR12); break;
		            case 13: result.add(totalGR13); break;
		            case 14: result.add(totalGR14); break;
		            case 15: result.add(totalGR15); break;
		            case 16: result.add(totalGR16); break;
		            case 17: result.add(totalGR17); break;
		            case 18: result.add(totalGR18); break;
		            default: break;
		        }
		    }
		}
		return result;

	}
}