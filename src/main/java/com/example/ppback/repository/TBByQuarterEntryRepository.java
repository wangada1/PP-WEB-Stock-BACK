package com.example.ppback.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.example.ppback.model.TBByQuarterEntry;

public interface TBByQuarterEntryRepository extends MongoRepository<TBByQuarterEntry, String> {

}
