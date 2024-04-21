package com.example.ppback.model;
import java.util.List;

import org.springframework.stereotype.Component;

import java.util.ArrayList;

import lombok.Data;
@Data
@Component
public class PPDataAggregatedResult {
	
    private int totalPP0;
    private int totalPP1;
    private int totalPP2;
    private int totalPP3;
    private int totalPP4;
    private int totalPP5;
    private int totalPP6;
    private int totalPP7;
    private int totalPP8;
    private int totalPP9;
    private int totalPP10;
    private int totalPP11;
    private int totalPP12;
    private int totalPP13;
    private int totalPP14;
    private int totalPP15;
    private int totalPP16;
    private int totalPP17;
    private int totalPP18;
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
                    case 0: result.add(totalPP0); break;
                    case 1: result.add(totalPP1); break;
                    case 2: result.add(totalPP2); break;
                    case 3: result.add(totalPP3); break;
                    case 4: result.add(totalPP4); break;
                    case 5: result.add(totalPP5); break;
                    case 6: result.add(totalPP6); break;
                    case 7: result.add(totalPP7); break;
                    case 8: result.add(totalPP8); break;
                    case 9: result.add(totalPP9); break;
                    case 10: result.add(totalPP10); break;
                    case 11: result.add(totalPP11); break;
                    case 12: result.add(totalPP12); break;
                    case 13: result.add(totalPP13); break;
                    case 14: result.add(totalPP14); break;
                    case 15: result.add(totalPP15); break;
                    case 16: result.add(totalPP16); break;
                    case 17: result.add(totalPP17); break;
                    case 18: result.add(totalPP18); break;
                    default: break;
                }
            }
        }
        else {
        	for (int i = 0; i < 25 -month; i++) {
                switch (i) {
                    case 0: result.add(totalPP0); break;
                    case 1: result.add(totalPP1); break;
                    case 2: result.add(totalPP2); break;
                    case 3: result.add(totalPP3); break;
                    case 4: result.add(totalPP4); break;
                    case 5: result.add(totalPP5); break;
                    case 6: result.add(totalPP6); break;
                    case 7: result.add(totalPP7); break;
                    case 8: result.add(totalPP8); break;
                    case 9: result.add(totalPP9); break;
                    case 10: result.add(totalPP10); break;
                    case 11: result.add(totalPP11); break;
                    case 12: result.add(totalPP12); break;
                    case 13: result.add(totalPP13); break;
                    case 14: result.add(totalPP14); break;
                    case 15: result.add(totalPP15); break;
                    case 16: result.add(totalPP16); break;
                    case 17: result.add(totalPP17); break;
                    case 18: result.add(totalPP18); break;
                    default: break;
                }
            }
        }
        return result;
    }
}
