package com.example.ppback.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.ppback.model.InfoRecordEntry;

public interface InfoRecordEntryRepository extends MongoRepository<InfoRecordEntry, String> {

}
