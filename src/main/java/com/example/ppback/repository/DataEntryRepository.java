package com.example.ppback.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.ppback.model.DataEntry;

public interface DataEntryRepository extends MongoRepository<DataEntry, String> {

}
