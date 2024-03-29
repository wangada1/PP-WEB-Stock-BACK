package com.example.ppback.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.example.ppback.model.TBByMonthEntry;

public interface TBByMonthEntryRepository extends MongoRepository<TBByMonthEntry, String> {

}
