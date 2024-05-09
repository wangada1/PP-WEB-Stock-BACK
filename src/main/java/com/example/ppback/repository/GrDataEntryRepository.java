package com.example.ppback.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ppback.model.GrDataEntry;
@Repository
public interface GrDataEntryRepository extends JpaRepository<GrDataEntry, Long> {

}
