package com.example.ppback.model;
import lombok.Data;

@Data
public class InfoRecordImportEntity {
    @ColumnIndex(value = 1) 
    private String productNumber;
    @ColumnIndex(value = 0)
	private String vendor;
}