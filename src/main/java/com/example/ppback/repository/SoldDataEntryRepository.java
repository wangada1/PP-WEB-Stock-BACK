package com.example.ppback.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.ppback.model.SoldDataEntry;

public interface SoldDataEntryRepository extends JpaRepository<SoldDataEntry, Long> {

}
