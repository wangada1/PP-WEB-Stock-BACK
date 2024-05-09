package com.example.ppback.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ppback.model.DataEntry;

@Repository
public interface DataEntryRepository extends JpaRepository<DataEntry, Long> {

}
