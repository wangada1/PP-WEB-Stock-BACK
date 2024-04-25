package com.example.ppback.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Document(collection = "dataEntry")
@Data
@NoArgsConstructor
@AllArgsConstructor

@CompoundIndexes({
    @CompoundIndex(name = "pdcl_yearMonth", def = "{'pdcl': 1,'yearMonth':1}"),
    @CompoundIndex(name = "vendor_yearMonth", def = "{'vendor': 1,'yearMonth':1}"),
    @CompoundIndex(name = "type_yearMonth", def = "{'type': 1,'yearMonth':1}"),
    @CompoundIndex(name = "pdcl_vendor_yearMonth", def = "{'pdcl': 1, 'vendor': 1,'yearMonth':1}"),
    @CompoundIndex(name = "pdcl_type_yearMonth", def = "{'pdcl': 1, 'type': 1,'yearMonth':1}"),
    @CompoundIndex(name = "vendor_type_yearMonth", def = "{'vendor': 1, 'type': 1,'yearMonth':1}"),
    @CompoundIndex(name = "vendor_pdcl_type_yearMonth", def = "{'vendor': 1, 'pdcl': 1, 'type': 1,'yearMonth':1}")
})
public class DataEntry {
	@Id
	private String id;
	@Indexed(unique = false)
	private String productNumber;
	private String pdcl;
	private String businessUnit;
	private String profitCenter;
	private String vendor;
	@ColumnIndex(value = 12)
	private String type;
	@ColumnIndex(value = 87)
    private Integer pp0;
    @ColumnIndex(value = 88)
    private Integer pp1;
    @ColumnIndex(value = 89)
    private Integer pp2;
    @ColumnIndex(value = 90)
    private Integer pp3;
    @ColumnIndex(value = 91)
    private Integer pp4;
    @ColumnIndex(value = 92)
    private Integer pp5;
    @ColumnIndex(value = 93)
    private Integer pp6;
    @ColumnIndex(value = 94)
    private Integer pp7;
    @ColumnIndex(value = 95)
    private Integer pp8;
    @ColumnIndex(value = 96)
    private Integer pp9;
    @ColumnIndex(value = 97)
    private Integer pp10;
    @ColumnIndex(value = 98)
    private Integer pp11;
    @ColumnIndex(value = 99)
    private Integer pp12;
    @ColumnIndex(value = 100)
    private Integer pp13;
    @ColumnIndex(value = 101)
    private Integer pp14;
    @ColumnIndex(value = 102)
    private Integer pp15;
    @ColumnIndex(value = 103)
    private Integer pp16;
    @ColumnIndex(value = 104)
    private Integer pp17;
    @ColumnIndex(value = 105)
    private Integer pp18;


    @ColumnIndex(value = 20)
    private Integer tb0;
    @ColumnIndex(value = 21)
    private Integer tb1;
    @ColumnIndex(value = 22)
    private Integer tb2;
    @ColumnIndex(value = 23)
    private Integer tb3;
    @ColumnIndex(value = 24)
    private Integer tb4;
    @ColumnIndex(value = 25)
    private Integer tb5;
    @ColumnIndex(value = 26)
    private Integer tb6;
    @ColumnIndex(value = 27)
    private Integer tb7;
    @ColumnIndex(value = 28)
    private Integer tb8;
    @ColumnIndex(value = 29)
    private Integer tb9;
    @ColumnIndex(value = 30)
    private Integer tb10;
    @ColumnIndex(value = 31)
    private Integer tb11;
    @ColumnIndex(value = 32)
    private Integer tb12;
    @ColumnIndex(value = 33)
    private Integer tb13;
    @ColumnIndex(value = 34)
    private Integer tb14;
    @ColumnIndex(value = 35)
    private Integer tb15;
    @ColumnIndex(value = 36)
    private Integer tb16;
    @ColumnIndex(value = 37)
    private Integer tb17;
    @ColumnIndex(value = 38)
    private Integer tb18;
    //The total of tb
    @ColumnIndex(value = 39)
    private Integer tb19;
	private String yearMonth;
	private String totalPP;
}
