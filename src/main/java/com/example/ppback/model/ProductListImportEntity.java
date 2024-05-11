package com.example.ppback.model;
import lombok.Data;

@Data
public class ProductListImportEntity {
    @ColumnIndex(value = 0) 
    private String productNumber;
    @ColumnIndex(value = 1)//固定为第2列
	private String type;
}