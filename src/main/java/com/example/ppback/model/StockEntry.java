package com.example.ppback.model;

import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Document(collection = "StockEntry")
@Data
@NoArgsConstructor
@AllArgsConstructor
@CompoundIndexes({
    @CompoundIndex(name = "PDCL_yearMonth", def = "{'PDCL': 1,'yearMonth':1}"),
    @CompoundIndex(name = "Vendor_yearMonth", def = "{'Vendor': 1,'yearMonth':1}"),
    @CompoundIndex(name = "Type_yearMonth", def = "{'Type': 1,'yearMonth':1}"),
    @CompoundIndex(name = "PDCL_Vendor_yearMonth", def = "{'PDCL': 1, 'Vendor': 1,'yearMonth':1}"),
    @CompoundIndex(name = "PDCL_Type_yearMonth", def = "{'PDCL': 1, 'Type': 1,'yearMonth':1}"),
    @CompoundIndex(name = "Vendor_Type_yearMonth", def = "{'Vendor': 1, 'Type': 1,'yearMonth':1}"),
    @CompoundIndex(name = "Vendor_PDCL_Type_yearMonth", def = "{'Vendor': 1, 'PDCL': 1, 'Type': 1,'yearMonth':1}")
})
public class StockEntry {
	private String productNumber;
	private String PDCL;
	private String Type;
	private String Vendor;
	private String yearMonth;
	private double stockInfo0;//如果月份数小于7，则从去年1月开始，否则从今年1月开始，以上月截至
	private double stockInfo1;
	private double stockInfo2;
	private double stockInfo3;
	private double stockInfo4;
	private double stockInfo5;
	private double stockInfo6;
	private double stockInfo7;
	private double stockInfo8;
	private double stockInfo9;
	private double stockInfo10;
	private double stockInfo11;
	private double stockInfo12;
	private double stockInfo13;
	private double stockInfo14;
	private double stockInfo15;
	private double stockInfo16;
	private double stockInfo17;
	private double totalSTOCK0;
	private double totalSTOCK1;
	private double totalSTOCK2;
	private double totalSTOCK3;
	private double totalSTOCK4;
	private double totalSTOCK5;
	private double totalSTOCK6;
	private double totalSTOCK7;
	private double totalSTOCK8;
	private double totalSTOCK9;
	private double totalSTOCK10;
	private double totalSTOCK11;
	private double totalSTOCK12;
	private double totalSTOCK13;
	private double totalSTOCK14;
	private double totalSTOCK15;
	private double totalSTOCK16;
	private double totalSTOCK17;


}
