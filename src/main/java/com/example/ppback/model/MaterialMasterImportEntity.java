package com.example.ppback.model;
import java.util.List;

import lombok.Data;

@Data
public class MaterialMasterImportEntity {
    @ColumnIndex(value = 0) 
    private String productNumber;
    @ColumnIndex(value = 1)
	private String profitCenter;
}