package com.example.ppback.model;
import java.util.List;

import lombok.Data;

@Data
public class SoldEntryImportEntity {
    @ColumnIndex(value = 1) // Column index for "Sold-to Party"
    private String productNumber;
    @ColumnIndex(value = 6)
    private List<Integer> soldList;
    @ColumnIndex(value = 0)
	private String pdcl;
    @ColumnIndex(value = 4)
	private String businessUnit;
    @ColumnIndex(value = 5)
	private String profitCenter;
    @ColumnIndex(value = 100)//目前的表格中没有vendor
	private String vendor;
    @ColumnIndex(value = 200)//目前的表格中没有type
	private String type;
}