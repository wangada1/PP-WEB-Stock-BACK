package com.example.ppback.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.example.ppback.model.PPByMonthEntry;

public interface PPByMonthEntryRepository extends MongoRepository<PPByMonthEntry, String> {

}