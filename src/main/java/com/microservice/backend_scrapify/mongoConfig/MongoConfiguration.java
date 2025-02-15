package com.microservice.backend_scrapify.mongoConfig;

import com.microservice.backend_scrapify.utils.threadLocals.MongoConnectionStorage;
import com.mongodb.ConnectionString;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
public class MongoConfiguration {

    @Bean
    @Lazy
    public MongoTemplate mongoTemplate() {
        ConnectionString connectionString = new ConnectionString(MongoConnectionStorage.getConnection());
        return new MongoTemplate(new DatabaseConfiguration(connectionString));
    }
}