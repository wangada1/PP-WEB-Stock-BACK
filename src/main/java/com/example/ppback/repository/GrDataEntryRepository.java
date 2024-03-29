package com.example.ppback.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.ppback.model.GrDataEntry;

public interface GrDataEntryRepository extends MongoRepository<GrDataEntry, String> {

}
