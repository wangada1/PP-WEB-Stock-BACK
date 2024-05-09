package com.example.ppback.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.example.ppback.model.DataEntry;
import com.example.ppback.model.PPDataAggregatedResult;

import java.util.List;

public interface PPDataGroupByPDCLandVendorRepository extends JpaRepository<DataEntry, Long> {
    @Query("SELECT " +
            "SUM(d.pp0), SUM(d.pp1), SUM(d.pp2), SUM(d.pp3), SUM(d.pp4), SUM(d.pp5), " +
            "SUM(d.pp6), SUM(d.pp7), SUM(d.pp8), SUM(d.pp9), SUM(d.pp10), SUM(d.pp11), " +
            "SUM(d.pp12), SUM(d.pp13), SUM(d.pp14), SUM(d.pp15), SUM(d.pp16), SUM(d.pp17), SUM(d.pp18) " +
            "FROM DataEntry d " +
            "WHERE d.pdcl = ?1 AND d.vendor = ?2 AND d.yearMonth = ?3")
    List<Object[]> getTotalPP(String pdcl, String vendor, String yearMonth);
}
