package com.example.ppback.model;

import java.util.List;

import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Document(collection = "ppByQuarterPercent")
@Data
@NoArgsConstructor
@AllArgsConstructor

@CompoundIndexes({
    @CompoundIndex(name = "yearMonth_idx", def = "{'yearMonth': 1}")
})
public class PPByQuarterPercent {
	private List<Integer> data;
	private String yearMonth;
	private String pdcl;

}
