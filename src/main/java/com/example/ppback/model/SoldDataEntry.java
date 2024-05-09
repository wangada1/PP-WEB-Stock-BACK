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
public class SoldDataEntry {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	private String pdcl;
	@Column(nullable = true)
    private String vendorNumber;
	private String vendor;
	private String type;
	private String MRPController;
	private String productNumber;
	private String yearMonth;
	private double soldInfo0;
	private double soldInfo1;
	private double soldInfo2;
	private double soldInfo3;
	private double soldInfo4;
	private double soldInfo5;
	private double soldInfo6;
	private double soldInfo7;
	private double soldInfo8;
	private double soldInfo9;
	private double soldInfo10;
	private double soldInfo11;
	private double soldInfo12;
	private double soldInfo13;
	private double soldInfo14;
	private double soldInfo15;
	private double soldInfo16;
	private double soldInfo17;

}
