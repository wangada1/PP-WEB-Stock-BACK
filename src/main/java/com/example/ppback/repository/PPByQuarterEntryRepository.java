package com.example.ppback.repository;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.ppback.model.PPByMonthEntry;
import com.example.ppback.model.PPByQuarterEntry;
public interface PPByQuarterEntryRepository extends MongoRepository<PPByQuarterEntry, String>  {

}
