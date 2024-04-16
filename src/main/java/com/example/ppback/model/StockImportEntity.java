package com.example.ppback.model;
import java.util.List;

import lombok.Data;

@Data
public class StockImportEntity {
    @ColumnIndex(value = 0) 
    private String productNumber;
    @ColumnIndex(value = 7)
	private String PDCL;
    @ColumnIndex(value = 8)
	private double stockInfo0; //如果月份数小于7，则从去年1月开始，否则从今年1月开始，以上月截至
    @ColumnIndex(value = 9)
    private double stockInfo1;
    @ColumnIndex(value = 10)
    private double stockInfo2;
    @ColumnIndex(value = 11)
    private double stockInfo3;
    @ColumnIndex(value = 12)
    private double stockInfo4;
    @ColumnIndex(value = 13)
    private double stockInfo5;
    @ColumnIndex(value = 14)
    private double stockInfo6;
    @ColumnIndex(value = 15)
    private double stockInfo7;
    @ColumnIndex(value = 16)
    private double stockInfo8;
    @ColumnIndex(value = 17)
    private double stockInfo9;
    @ColumnIndex(value = 18)
    private double stockInfo10;
    @ColumnIndex(value = 19)
    private double stockInfo11;
    @ColumnIndex(value = 20)
    private double stockInfo12;
    @ColumnIndex(value = 21)
    private double stockInfo13;
    @ColumnIndex(value = 22)
    private double stockInfo14;
    @ColumnIndex(value = 23)
    private double stockInfo15;
    @ColumnIndex(value = 24)
    private double stockInfo16;
    @ColumnIndex(value = 25)
    private double stockInfo17;

}