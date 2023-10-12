package com.example.ppback.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Document(collection = "dataEntry")
@Data
@NoArgsConstructor
@AllArgsConstructor

@CompoundIndexes({
    @CompoundIndex(name = "yearMonth_idx", def = "{'yearMonth': 1}")
})
public class DataEntry {
	@Id
	private String id; 
	private String productNumber;
	private String vendor;
	private String mdltPN;
	private List<Integer> ppInfo;
	private List<Integer> grInfo;
	private List<Integer> tbInfo;
	private String yearMonth;
}