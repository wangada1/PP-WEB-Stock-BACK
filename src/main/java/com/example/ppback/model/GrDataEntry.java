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
public class GrDataEntry {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	@Column(nullable = true)
	private String pdcl;
	@Column(nullable = true)
	private String businessUnit;
	@Column(nullable = true)
	private String profitCenter;
	@Column(nullable = true)
	private String productNumber;
	@Column(nullable = true)
	private double grInfo0;
	@Column(nullable = true)
	private double grInfo1;
	@Column(nullable = true)
	private double grInfo2;
	@Column(nullable = true)
	private double grInfo3;
	@Column(nullable = true)
	private double grInfo4;
	@Column(nullable = true)
	private double grInfo5;
	@Column(nullable = true)
	private double grInfo6;
	@Column(nullable = true)
	private double grInfo7;
	@Column(nullable = true)
	private double grInfo8;
	@Column(nullable = true)
	private double grInfo9;
	@Column(nullable = true)
	private double grInfo10;
	@Column(nullable = true)
	private double grInfo11;
	@Column(nullable = true)
	private double grInfo12;
	@Column(nullable = true)
	private double grInfo13;
	@Column(nullable = true)
	private double grInfo14;
	@Column(nullable = true)
	private double grInfo15;
	@Column(nullable = true)
	private double grInfo16;
	@Column(nullable = true)
	private double grInfo17;
	@Column(nullable = false)
	private String yearMonth;
	@Column(nullable = true)
	private String vendor;
	@Column(nullable = true)
    private String vendorNumber;
	@Column(nullable = true)
	private String type;
}
