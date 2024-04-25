package com.example.ppback.service;

import com.mongodb.MongoClientSettings;
import com.mongodb.ConnectionString;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class MongoDBService2 {
    private static final Logger LOGGER = Logger.getLogger(MongoDBService.class.getName());
    private static MongoClient mongoClient;
    private static MongoDatabase database;
    private static MongoCollection<Document> collection; // 将集合对象设置为静态属性

    // 静态初始化块，用于初始化 MongoClient 和集合对象
    static {
        // 连接到 MongoDB
        mongoClient = MongoClients.create("mongodb://localhost:27017");
        // 连接到 MongoDB
        database = mongoClient.getDatabase("DataEntry");
        collection = database.getCollection("dataEntry");
    }

    public static String findTypeByPN(String pn) {//这个方法找不到对应的Type，需要检查
        // 查询目标集合中指定 PN 值的第一个文档
        Document document = collection.find(new Document("productNumber", pn)).first();
        if (document != null) {
            // 获取文档的 type 值
            String type = document.getString("type");
            return type;
        }
        return null; // 如果没有找到匹配的文档，则返回 null
    }
}
