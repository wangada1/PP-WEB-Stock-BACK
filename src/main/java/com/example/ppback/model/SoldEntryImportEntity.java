package com.example.ppback.model;
import java.util.List;

import lombok.Data;

@Data
public class SoldEntryImportEntity {
    @ColumnIndex(value = 0) // Column index for "Sold-to Party"
    private String productNumber;
    @ColumnIndex(value = 3)
    private List<Integer> soldList;
    @ColumnIndex(value = 2)
	private String MRPController;
}