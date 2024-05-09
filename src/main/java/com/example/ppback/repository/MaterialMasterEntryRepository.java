package com.example.ppback.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ppback.model.MaterialMasterEntry;
@Repository
public interface MaterialMasterEntryRepository extends JpaRepository<MaterialMasterEntry, Long> {

}
