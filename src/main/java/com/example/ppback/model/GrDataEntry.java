package com.example.ppback.model;

import java.util.List;

import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Document(collection = "grDataEntry")
@Data
@NoArgsConstructor
@AllArgsConstructor

@CompoundIndexes({
    @CompoundIndex(name = "yearMonth_idx", def = "{'yearMonth': 1}"),
    @CompoundIndex(name = "pdcl_idx", def = "{'pdcl': 1}"),
    @CompoundIndex(name = "vendor_idx", def = "{'vendor': 1}"),
    @CompoundIndex(name = "type_idx", def = "{'type': 1}"),
    @CompoundIndex(name = "pdcl_vendor_idx", def = "{'pdcl': 1, 'vendor': 1}"),
    @CompoundIndex(name = "pdcl_type_idx", def = "{'pdcl': 1, 'type': 1}"),
    @CompoundIndex(name = "vendor_type_idx", def = "{'vendor': 1, 'type': 1}"),
    @CompoundIndex(name = "vendor_pdcl_type_idx", def = "{'vendor': 1, 'pdcl': 1, 'type': 1}")
})
public class GrDataEntry {
	private String id;
	private String pdcl;
	private String businessUnit;
	private String profitCenter;
	private String productNumber;
	private List<Integer> grInfo;
	private double grInfo0;
	private double grInfo1;
	private double grInfo2;
	private double grInfo3;
	private double grInfo4;
	private double grInfo5;
	private double grInfo6;
	private double grInfo7;
	private double grInfo8;
	private double grInfo9;
	private double grInfo10;
	private double grInfo11;
	private double grInfo12;
	private double grInfo13;
	private double grInfo14;
	private double grInfo15;
	private double grInfo16;
	private double grInfo17;
	private String yearMonth;
	private String vendor;
	private String type;
}
