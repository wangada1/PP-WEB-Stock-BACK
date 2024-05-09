package com.example.ppback.service;

import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class MongoDBService {
    private static final Logger LOGGER = Logger.getLogger(MongoDBService.class.getName());
   /* private static MongoClient mongoClient;
    private static MongoDatabase database;
    private static MongoCollection<Document> collection; // 将集合对象设置为静态属性

    // 静态初始化块，用于初始化 MongoClient 和集合对象
    static {
        // 连接到 MongoDB
        mongoClient = MongoClients.create("mongodb://localhost:27017");
        // 连接到 MongoDB
        database = mongoClient.getDatabase("DataEntry");
        collection = database.getCollection("InfoRecordEntry");
    }

    // 输入 PN 值和目标集合名，输出目标集合中拥有相同 PN 值的第一个元素的 vendor 值
    public static String findVendorByPN(String pn) {
        // 查询目标集合中指定 PN 值的第一个文档
        Document document = collection.find(new Document("productNumber", pn))
                .projection(Projections.include("vendor"))
                .first();
        if (document != null) {
            // 获取文档的 vendor 值
            String vendor = document.getString("vendor");
            return vendor;
        }
        
        return null; // 如果没有找到匹配的文档，则返回 null
    }*/
}
