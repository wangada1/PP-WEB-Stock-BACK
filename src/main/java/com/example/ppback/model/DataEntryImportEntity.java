package com.example.ppback.model;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class DataEntryImportEntity {
    @ColumnIndex(value = 0) // Column index for "Sold-to Party"
    private String productNumber;
    
    @ColumnIndex(value = 2) 
    private String vendor;

    @ColumnIndex(value = 8) 
    private String mdltPN; 
    
    @ColumnIndex(value = 85) 
    private Integer pp0;
    @ColumnIndex(value = 86) 
    private Integer pp1;
    @ColumnIndex(value = 87) 
    private Integer pp2;
    @ColumnIndex(value = 88) 
    private Integer pp3;
    @ColumnIndex(value = 89) 
    private Integer pp4;
    @ColumnIndex(value = 90) 
    private Integer pp5;
    @ColumnIndex(value = 91) 
    private Integer pp6;
    @ColumnIndex(value = 92) 
    private Integer pp7;
    @ColumnIndex(value = 93) 
    private Integer pp8;
    @ColumnIndex(value = 94) 
    private Integer pp9;
    @ColumnIndex(value = 95) 
    private Integer pp10;
    @ColumnIndex(value = 96) 
    private Integer pp11;
    @ColumnIndex(value = 97) 
    private Integer pp12;
    @ColumnIndex(value = 98) 
    private Integer pp13;
    @ColumnIndex(value = 99) 
    private Integer pp14;
    @ColumnIndex(value = 100) 
    private Integer pp15;
    @ColumnIndex(value = 101) 
    private Integer pp16;
    @ColumnIndex(value = 102) 
    private Integer pp17;
    @ColumnIndex(value = 103) 
    private Integer pp18;
   
    
    @ColumnIndex(value = 18) 
    private Integer tb0;
    @ColumnIndex(value = 19) 
    private Integer tb1;
    @ColumnIndex(value = 20)
    private Integer tb2;
    @ColumnIndex(value = 21) 
    private Integer tb3;
    @ColumnIndex(value = 22) 
    private Integer tb4;
    @ColumnIndex(value = 23) 
    private Integer tb5;
    @ColumnIndex(value = 24)
    private Integer tb6;
    @ColumnIndex(value = 25) 
    private Integer tb7;
    @ColumnIndex(value = 26) 
    private Integer tb8;
    @ColumnIndex(value = 27) 
    private Integer tb9;
    @ColumnIndex(value = 28) 
    private Integer tb10;
    @ColumnIndex(value = 29)
    private Integer tb11;
    @ColumnIndex(value = 30) 
    private Integer tb12;
    @ColumnIndex(value = 31) 
    private Integer tb13;
    @ColumnIndex(value = 32) 
    private Integer tb14;
    @ColumnIndex(value = 33) 
    private Integer tb15;
    @ColumnIndex(value = 34) 
    private Integer tb16;
    @ColumnIndex(value = 35) 
    private Integer tb17;
    @ColumnIndex(value = 36) 
    private Integer tb18;
    //The total of tb
    @ColumnIndex(value = 37)
    private Integer tb19;
    
    
    
    @ColumnIndex(value = 124) 
    private Integer gr0;
    @ColumnIndex(value = 125)
    private Integer gr1;
    @ColumnIndex(value = 126) 
    private Integer gr2;
    @ColumnIndex(value = 127) 
    private Integer gr3;
    @ColumnIndex(value = 128)
    private Integer gr4;
    @ColumnIndex(value = 129) 
    private Integer gr5;
    
    public List<Integer> getGrValues() {
        List<Integer> grValues = new ArrayList<>();
        
        // Iterate through gr0 to gr5 fields and add their values to the list
        for (int i = 0; i <= 5; i++) {
            try {
                // Use reflection to access the gr fields based on the index
                int grValue = (int) getClass().getDeclaredField("gr" + i).get(this);
                grValues.add(grValue);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                // Handle any exceptions that may occur during reflection
                e.printStackTrace();
            }
        }
        
        return grValues;
    }
    
    public List<Integer> getPpValues() {
        List<Integer> ppValues = new ArrayList<>();
        
        // Iterate through pp0 to pp18 fields and add their values to the list
        for (int i = 0; i <= 18; i++) {
            try {
                // Use reflection to access the pp fields based on the index
                int ppValue = (int) getClass().getDeclaredField("pp" + i).get(this);
                ppValues.add(ppValue);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                // Handle any exceptions that may occur during reflection
                e.printStackTrace();
            }
        }
        
        return ppValues;
    }

    // Getter method to retrieve a list of tb values
    public List<Integer> getTbValues() {
        List<Integer> tbValues = new ArrayList<>();
        
        // Iterate through tb0 to tb19 fields and add their values to the list
        for (int i = 0; i <= 19; i++) {
            try {
                // Use reflection to access the tb fields based on the index
                int tbValue = (int) getClass().getDeclaredField("tb" + i).get(this);
                tbValues.add(tbValue);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                // Handle any exceptions that may occur during reflection
                e.printStackTrace();
            }
        }
        
        return tbValues;
    }
  

  
}



