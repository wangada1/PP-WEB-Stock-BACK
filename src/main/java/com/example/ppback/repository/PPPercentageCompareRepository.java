package com.example.ppback.repository;
import org.springframework.data.mongodb.repository.MongoRepository;
import com.example.ppback.model.PPPercentageCompare;
public interface PPPercentageCompareRepository extends MongoRepository<PPPercentageCompare, String>  {

}