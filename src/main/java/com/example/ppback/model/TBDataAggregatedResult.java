package com.example.ppback.model;

import java.util.List;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import lombok.Data;

@Data
@Component
public class TBDataAggregatedResult {
	
    private int totalTB0;
    private int totalTB1;
    private int totalTB2;
    private int totalTB3;
    private int totalTB4;
    private int totalTB5;
    private int totalTB6;
    private int totalTB7;
    private int totalTB8;
    private int totalTB9;
    private int totalTB10;
    private int totalTB11;
    private int totalTB12;
    private int totalTB13;
    private int totalTB14;
    private int totalTB15;
    private int totalTB16;
    private int totalTB17;
    private int totalTB18;
	
	private List<Integer> data;
	private String yearMonth;
	private String pdcl;
	private String vendor;
	private String type;
    
    // Getters and setters
    
	public List<Integer> iterator(int month) {
        List<Integer> result = new ArrayList<>();
        if(month<7) {
            for (int i = 0; i < 13 -month; i++) {
                switch (i) {
                    case 0: result.add(totalTB0); break;
                    case 1: result.add(totalTB1); break;
                    case 2: result.add(totalTB2); break;
                    case 3: result.add(totalTB3); break;
                    case 4: result.add(totalTB4); break;
                    case 5: result.add(totalTB5); break;
                    case 6: result.add(totalTB6); break;
                    case 7: result.add(totalTB7); break;
                    case 8: result.add(totalTB8); break;
                    case 9: result.add(totalTB9); break;
                    case 10: result.add(totalTB10); break;
                    case 11: result.add(totalTB11); break;
                    case 12: result.add(totalTB12); break;
                    case 13: result.add(totalTB13); break;
                    case 14: result.add(totalTB14); break;
                    case 15: result.add(totalTB15); break;
                    case 16: result.add(totalTB16); break;
                    case 17: result.add(totalTB17); break;
                    case 18: result.add(totalTB18); break;
                    default: break;
                }
            }
        }
        else {
        	for (int i = 0; i < 25 -month; i++) {
                switch (i) {
                    case 0: result.add(totalTB0); break;
                    case 1: result.add(totalTB1); break;
                    case 2: result.add(totalTB2); break;
                    case 3: result.add(totalTB3); break;
                    case 4: result.add(totalTB4); break;
                    case 5: result.add(totalTB5); break;
                    case 6: result.add(totalTB6); break;
                    case 7: result.add(totalTB7); break;
                    case 8: result.add(totalTB8); break;
                    case 9: result.add(totalTB9); break;
                    case 10: result.add(totalTB10); break;
                    case 11: result.add(totalTB11); break;
                    case 12: result.add(totalTB12); break;
                    case 13: result.add(totalTB13); break;
                    case 14: result.add(totalTB14); break;
                    case 15: result.add(totalTB15); break;
                    case 16: result.add(totalTB16); break;
                    case 17: result.add(totalTB17); break;
                    case 18: result.add(totalTB18); break;
                    default: break;
                }
            }
        }
        return result;
    }
}
