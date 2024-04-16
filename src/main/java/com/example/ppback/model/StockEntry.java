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
    @CompoundIndex(name = "yearMonth_idx", def = "{'yearMonth': 1}"),
    @CompoundIndex(name = "PDCL_idx", def = "{'PDCL': text}"),
    @CompoundIndex(name = "productNumber_idx", def = "{'productNumber': text}"),
    @CompoundIndex(name = "PDCL_vendor_idx", def = "{'PDCL': text, 'vendor': text}"),
    @CompoundIndex(name = "PDCL_type_idx", def = "{'PDCL': text, 'type': text}"),
    @CompoundIndex(name = "vendor_type_idx", def = "{'vendor': text, 'type': text}"),
    @CompoundIndex(name = "vendor_PDCL_type_idx", def = "{'vendor': text, 'PDCL': text, 'type': text}")
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

}
