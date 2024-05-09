package com.example.ppback.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.ppback.model.StockEntry;

public interface StockEntryRepository extends JpaRepository<StockEntry, Long> {

}
