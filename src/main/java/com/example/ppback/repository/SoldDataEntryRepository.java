package com.example.ppback.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.ppback.model.SoldDataEntry;

public interface SoldDataEntryRepository extends MongoRepository<SoldDataEntry, String> {

}
