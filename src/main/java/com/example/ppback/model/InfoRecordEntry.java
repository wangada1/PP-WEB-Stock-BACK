package com.example.ppback.model;

import java.util.List;

import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Document(collection = "InfoRecordEntry")
@Data
@NoArgsConstructor
@AllArgsConstructor

@CompoundIndexes({
    @CompoundIndex(name = "yearMonth_idx", def = "{'yearMonth': 1}"),
    @CompoundIndex(name = "vendor_idx", def = "{'vendor': 1}"),
    @CompoundIndex(name = "productNumber_idx", def = "{'productNumber': 1}"),
})
public class InfoRecordEntry {
	private String vendor;
	private String productNumber;
	private String yearMonth;
}