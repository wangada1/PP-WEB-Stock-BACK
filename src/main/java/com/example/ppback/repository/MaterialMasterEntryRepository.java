package com.example.ppback.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.ppback.model.MaterialMasterEntry;

public interface MaterialMasterEntryRepository extends MongoRepository<MaterialMasterEntry, String> {

}
