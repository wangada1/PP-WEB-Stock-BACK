package com.example.ppback.model;
import java.util.List;

import lombok.Data;

@Data
public class SoldEntryImportEntity {
    @ColumnIndex(value = 0) // Column index for "Sold-to Party"
    private String productNumber;
    @ColumnIndex(value = 3)
    private double soldInfo0;
    @ColumnIndex(value = 5)
	private double soldInfo1;
    @ColumnIndex(value = 7)
	private double soldInfo2;
    @ColumnIndex(value = 9)
	private double soldInfo3;
    @ColumnIndex(value = 11)
	private double soldInfo4;
    @ColumnIndex(value = 13)
	private double soldInfo5;
    @ColumnIndex(value = 15)
	private double soldInfo6;
    @ColumnIndex(value = 17)
	private double soldInfo7;
    @ColumnIndex(value = 19)
	private double soldInfo8;
    @ColumnIndex(value = 21)
	private double soldInfo9;
    @ColumnIndex(value = 23)
	private double soldInfo10;
    @ColumnIndex(value = 25)
	private double soldInfo11;
    @ColumnIndex(value = 27)
	private double soldInfo12;
    @ColumnIndex(value = 29)
	private double soldInfo13;
    @ColumnIndex(value = 31)
	private double soldInfo14;
    @ColumnIndex(value = 33)
	private double soldInfo15;
    @ColumnIndex(value = 35)
	private double soldInfo16;
    @ColumnIndex(value = 37)
	private double soldInfo17;
    @ColumnIndex(value = 2)
	private String MRPController;
}