package com.example.ppback.model;

import javax.persistence.Column;
import javax.persistence.Index;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
@Table(
		uniqueConstraints = {@UniqueConstraint(columnNames = {"productNumber", "yearMonth","vendor","type","pdcl","vendorNumber"})},
		indexes = {
				@Index(columnList = "productNumber"),
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
public class DataEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = false)
    private String productNumber;
    
    @Column(nullable = true)
    private String pdcl;
    
    @Column(nullable = true)
    private String businessUnit;
    
    @Column(nullable = true)
    private String profitCenter;
    
    @Column(nullable = true)
    private String vendor;
    @Column(nullable = true)
    private String vendorNumber;
    @ColumnIndex(value = 12)
    @Column(nullable = true)
    private String type;
    @Column(nullable = true)
    private Integer pp0;
    @Column(nullable = true)
    @ColumnIndex(value = 88)
    private Integer pp1;
    @Column(nullable = true)
    @ColumnIndex(value = 89)
    private Integer pp2;
    @Column(nullable = true)
    @ColumnIndex(value = 90)
    private Integer pp3;
    @Column(nullable = true)
    @ColumnIndex(value = 91)
    private Integer pp4;
    @Column(nullable = true)
    @ColumnIndex(value = 92)
    private Integer pp5;
    @Column(nullable = true)
    @ColumnIndex(value = 93)
    private Integer pp6;
    @Column(nullable = true)
    @ColumnIndex(value = 94)
    private Integer pp7;
    @Column(nullable = true)
    @ColumnIndex(value = 95)
    private Integer pp8;
    @Column(nullable = true)
    @ColumnIndex(value = 96)
    private Integer pp9;
    @Column(nullable = true)
    @ColumnIndex(value = 97)
    private Integer pp10;
    @Column(nullable = true)
    @ColumnIndex(value = 98)
    private Integer pp11;
    @Column(nullable = true)
    @ColumnIndex(value = 99)
    private Integer pp12;
    @Column(nullable = true)
    @ColumnIndex(value = 100)
    private Integer pp13;
    @Column(nullable = true)
    @ColumnIndex(value = 101)
    private Integer pp14;
    @Column(nullable = true)
    @ColumnIndex(value = 102)
    private Integer pp15;
    @Column(nullable = true)
    @ColumnIndex(value = 103)
    private Integer pp16;
    @Column(nullable = true)
    @ColumnIndex(value = 104)
    private Integer pp17;
    @Column(nullable = true)
    @ColumnIndex(value = 105)
    private Integer pp18;
    
    @Column(nullable = true)
    private Integer tb0;
    @Column(nullable = true)
    @ColumnIndex(value = 21)
    private Integer tb1;
    @Column(nullable = true)
    @ColumnIndex(value = 22)
    private Integer tb2;
    @Column(nullable = true)
    @ColumnIndex(value = 23)
    private Integer tb3;
    @Column(nullable = true)
    @ColumnIndex(value = 24)
    private Integer tb4;
    @Column(nullable = true)
    @ColumnIndex(value = 25)
    private Integer tb5;
    @Column(nullable = true)
    @ColumnIndex(value = 26)
    private Integer tb6;
    @Column(nullable = true)
    @ColumnIndex(value = 27)
    private Integer tb7;
    @Column(nullable = true)
    @ColumnIndex(value = 28)
    private Integer tb8;
    @Column(nullable = true)
    @ColumnIndex(value = 29)
    private Integer tb9;
    @Column(nullable = true)
    @ColumnIndex(value = 30)
    private Integer tb10;
    @Column(nullable = true)
    @ColumnIndex(value = 31)
    private Integer tb11;
    @Column(nullable = true)
    @ColumnIndex(value = 32)
    private Integer tb12;
    @Column(nullable = true)
    @ColumnIndex(value = 33)
    private Integer tb13;
    @Column(nullable = true)
    @ColumnIndex(value = 34)
    private Integer tb14;
    @Column(nullable = true)
    @ColumnIndex(value = 35)
    private Integer tb15;
    @Column(nullable = true)
    @ColumnIndex(value = 36)
    private Integer tb16;
    @Column(nullable = true)
    @ColumnIndex(value = 37)
    private Integer tb17;
    @Column(nullable = true)
    @ColumnIndex(value = 38)
    private Integer tb18;
    @Column(nullable = true)
    //The total of tb
    @ColumnIndex(value = 39)
    private Integer tb19;
    
    @Column(nullable = true)
    private String yearMonth;
}
