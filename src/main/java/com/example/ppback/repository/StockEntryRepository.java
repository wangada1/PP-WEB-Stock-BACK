package com.example.ppback.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.ppback.model.StockEntry;

public interface StockEntryRepository extends MongoRepository<StockEntry, String> {

}
