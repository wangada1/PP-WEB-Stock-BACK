package com.example.ppback.model;
import lombok.Data;

@Data
public class MaterialMasterImportEntity {
    @ColumnIndex(value = 0) 
    private String productNumber;
    @ColumnIndex(value = 1)
	private String profitCenter;
}