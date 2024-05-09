package com.example.ppback.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ppback.model.InfoRecordEntry;
@Repository
public interface InfoRecordEntryRepository extends JpaRepository<InfoRecordEntry, Long> {

}
