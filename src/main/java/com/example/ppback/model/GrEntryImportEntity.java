package com.example.ppback.model;
import java.util.List;

import lombok.Data;

@Data
public class GrEntryImportEntity {
    @ColumnIndex(value = 0) // Column index for "Sold-to Party"
    private String productNumber;
    @ColumnIndex(value = 8)
    private double grInfo0;

    @ColumnIndex(value = 9)
    private double grInfo1;
    @ColumnIndex(value = 10)
    private double grInfo2;

    @ColumnIndex(value = 11)
    private double grInfo3;

    @ColumnIndex(value = 12)
    private double grInfo4;

    @ColumnIndex(value = 13)
    private double grInfo5;

    @ColumnIndex(value = 14)
    private double grInfo6;

    @ColumnIndex(value = 15)
    private double grInfo7;

    @ColumnIndex(value = 16)
    private double grInfo8;

    @ColumnIndex(value = 17)
    private double grInfo9;

    @ColumnIndex(value = 18)
    private double grInfo10;

    @ColumnIndex(value = 19)
    private double grInfo11;

    @ColumnIndex(value = 20)
    private double grInfo12;

    @ColumnIndex(value = 21)
    private double grInfo13;

    @ColumnIndex(value = 22)
    private double grInfo14;

    @ColumnIndex(value = 23)
    private double grInfo15;

    @ColumnIndex(value = 24)
    private double grInfo16;

    @ColumnIndex(value = 25)
    private double grInfo17;

    @ColumnIndex(value = 7)
	private String pdcl;
    @ColumnIndex(value = 6)
	private String businessUnit;
    @ColumnIndex(value = 5)
	private String profitCenter;
    @ColumnIndex(value = 4)
	private String vendor;
    @ColumnIndex(value = 26)//目前的GR表格中没有type
	private String type;
}