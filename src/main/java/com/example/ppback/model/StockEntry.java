package com.example.ppback.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
@Table(
		uniqueConstraints = {@UniqueConstraint(columnNames = {"productNumber", "yearMonth","vendor","type","pdcl","vendorNumber"})},
		indexes = {
				@Index(columnList = "pdcl"),
				@Index(columnList = "vendor"),
				@Index(columnList = "type"),
				@Index(columnList = "pdcl,type"),
				@Index(columnList = "pdcl,vendor"),
				@Index(columnList = "type,vendor"),
				@Index(columnList = "pdcl,vendor,type"),
		}
)
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockEntry {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	private String productNumber;
	private String PDCL;
	private String Type;
	@Column(nullable = true)
    private String vendorNumber;
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
