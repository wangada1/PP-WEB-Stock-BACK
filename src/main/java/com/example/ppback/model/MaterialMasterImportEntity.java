package com.example.ppback.model;
import java.util.List;

import lombok.Data;

@Data
public class MaterialMasterImportEntity {
    @ColumnIndex(value = 2) 
    private String productNumber;
    @ColumnIndex(value = 31)
	private String profitCenter;
    @ColumnIndex(value = 0)
	private String yearMonth;
}