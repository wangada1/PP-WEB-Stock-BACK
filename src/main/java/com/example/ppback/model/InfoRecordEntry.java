package com.example.ppback.model;

import java.util.List;

import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Document(collection = "InfoRecordEntry")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class InfoRecordEntry {
	private String vendor;
	@Indexed(unique = false)
	private String productNumber;
}
