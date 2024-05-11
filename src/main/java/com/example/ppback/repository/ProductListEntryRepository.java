package com.example.ppback.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ppback.model.ProductListEntry;

@Repository
public interface ProductListEntryRepository extends JpaRepository<ProductListEntry, Long> {

}
