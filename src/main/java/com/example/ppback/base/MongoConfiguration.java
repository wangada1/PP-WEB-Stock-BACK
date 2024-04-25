package com.example.ppback.base;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "com.example.ppback.repository")
public class MongoConfiguration {
    // 在这里定义您的索引
}
