package com.example.ppback.model;

import java.util.List;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import lombok.Data;

@Data
@Component
public class SupplyDataAggregatedResult {
    
    private int totalSUPPLY0;
    private int totalSUPPLY1;
    private int totalSUPPLY2;
    private int totalSUPPLY3;
    private int totalSUPPLY4;
    private int totalSUPPLY5;
    private int totalSUPPLY6;
    private int totalSUPPLY7;
    private int totalSUPPLY8;
    private int totalSUPPLY9;
    private int totalSUPPLY10;
    private int totalSUPPLY11;
    private int totalSUPPLY12;
    private int totalSUPPLY13;
    private int totalSUPPLY14;
    private int totalSUPPLY15;
    private int totalSUPPLY16;
    private int totalSUPPLY17;
    private int totalSUPPLY18;
    
    private List<Integer> data;
    private String yearMonth;
    private String pdcl;
    private String vendor;
    private String type;
    
    // Getters and setters
    
    public List<Integer> iterator(int month) {
        List<Integer> result = new ArrayList<>();
        if(month < 7) {
            for (int i = 13-month; i < 19; i++) {
                switch (i) {
                    case 0: result.add(totalSUPPLY0); break;
                    case 1: result.add(totalSUPPLY1); break;
                    case 2: result.add(totalSUPPLY2); break;
                    case 3: result.add(totalSUPPLY3); break;
                    case 4: result.add(totalSUPPLY4); break;
                    case 5: result.add(totalSUPPLY5); break;
                    case 6: result.add(totalSUPPLY6); break;
                    case 7: result.add(totalSUPPLY7); break;
                    case 8: result.add(totalSUPPLY8); break;
                    case 9: result.add(totalSUPPLY9); break;
                    case 10: result.add(totalSUPPLY10); break;
                    case 11: result.add(totalSUPPLY11); break;
                    case 12: result.add(totalSUPPLY12); break;
                    case 13: result.add(totalSUPPLY13); break;
                    case 14: result.add(totalSUPPLY14); break;
                    case 15: result.add(totalSUPPLY15); break;
                    case 16: result.add(totalSUPPLY16); break;
                    case 17: result.add(totalSUPPLY17); break;
                    case 18: result.add(totalSUPPLY18); break;
                    default: break;
                }
            }
        } else {
            for (int i = 0; i < 0; i++) {
                switch (i) {
                    case 0: result.add(totalSUPPLY0); break;
                    case 1: result.add(totalSUPPLY1); break;
                    case 2: result.add(totalSUPPLY2); break;
                    case 3: result.add(totalSUPPLY3); break;
                    case 4: result.add(totalSUPPLY4); break;
                    case 5: result.add(totalSUPPLY5); break;
                    case 6: result.add(totalSUPPLY6); break;
                    case 7: result.add(totalSUPPLY7); break;
                    case 8: result.add(totalSUPPLY8); break;
                    case 9: result.add(totalSUPPLY9); break;
                    case 10: result.add(totalSUPPLY10); break;
                    case 11: result.add(totalSUPPLY11); break;
                    case 12: result.add(totalSUPPLY12); break;
                    case 13: result.add(totalSUPPLY13); break;
                    case 14: result.add(totalSUPPLY14); break;
                    case 15: result.add(totalSUPPLY15); break;
                    case 16: result.add(totalSUPPLY16); break;
                    case 17: result.add(totalSUPPLY17); break;
                    case 18: result.add(totalSUPPLY18); break;
                    default: break;
                }
            }
        }
        return result;
    }
}
