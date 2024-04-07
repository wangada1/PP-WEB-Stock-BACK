package com.example.ppback.repository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Aggregation;
import com.example.ppback.model.DataEntry;
import com.example.ppback.model.PPDataAggregatedResult;

import java.util.List;

public interface PPDataGroupByPDCLandVendorRepository extends MongoRepository<DataEntry, String> {
    @Aggregation(pipeline = {
    	"{$match: {pdcl: ?0, vendor: ?1, yearMonth: ?2}}",
        "{$group: {_id: null, "
        + "totalPP0: {$sum: \"$pp0\"},"
        + "totalPP1: {$sum: \"$pp1\"},"
        + "totalPP2: {$sum: \"$pp2\"},"
        + "totalPP3: {$sum: \"$pp3\"},"
        + "totalPP4: {$sum: \"$pp4\"},"
        + "totalPP5: {$sum: \"$pp5\"},"
        + "totalPP6: {$sum: \"$pp6\"},"
        + "totalPP7: {$sum: \"$pp7\"},"
        + "totalPP8: {$sum: \"$pp8\"},"
        + "totalPP9: {$sum: \"$pp9\"},"
        + "totalPP10: {$sum: \"$pp10\"},"
        + "totalPP11: {$sum: \"$pp11\"},"
        + "totalPP12: {$sum: \"$pp12\"},"
        + "totalPP13: {$sum: \"$pp13\"},"
        + "totalPP14: {$sum: \"$pp14\"},"
        + "totalPP15: {$sum: \"$pp15\"},"
        + "totalPP16: {$sum: \"$pp16\"},"
        + "totalPP17: {$sum: \"$pp17\"},"
        + "totalPP18: {$sum: \"$pp18\"},"})
    List<PPDataAggregatedResult> getTotalPP(String pdcl, String vendor, String yearMonth);
}
