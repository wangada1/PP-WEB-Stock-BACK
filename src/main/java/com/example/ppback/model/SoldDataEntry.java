package com.example.ppback.model;

import java.util.List;

import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Document(collection = "soldDataEntry")
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
public class SoldDataEntry {
	private String id;
	private String pdcl;
	private String vendor;
	private String type;
	private String MRPController;
	private String productNumber;
	private String yearMonth;
	private List<Integer> soldInfo;
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
