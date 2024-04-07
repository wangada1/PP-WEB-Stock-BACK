package com.example.ppback.model;
import java.util.List;

import lombok.Data;

@Data
public class GrEntryImportEntity {
    @ColumnIndex(value = 0) // Column index for "Sold-to Party"
    private String productNumber;
    @ColumnIndex(value = 8)
    private List<Integer> grList;
    @ColumnIndex(value = 7)
	private String pdcl;
    @ColumnIndex(value = 6)
	private String businessUnit;
    @ColumnIndex(value = 5)
	private String profitCenter;
    @ColumnIndex(value = 4)
	private String vendor;
    @ColumnIndex(value = 100)//目前的GR表格中没有type
	private String type;
}