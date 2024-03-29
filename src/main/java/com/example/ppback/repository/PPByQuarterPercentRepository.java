package com.example.ppback.repository;
import org.springframework.data.mongodb.repository.MongoRepository;
import com.example.ppback.model.PPByQuarterPercent;
public interface PPByQuarterPercentRepository extends MongoRepository<PPByQuarterPercent, String>  {

}
