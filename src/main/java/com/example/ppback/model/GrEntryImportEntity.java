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
}