package com.microservice.backend_scrapify.logic.category.repository.customRepo;

import com.microservice.backend_scrapify.logic.category.entity.Category;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.Optional;

public class CategoryCustomRepoImpl implements CategoryCustomRepo {

    private final MongoTemplate mongoTemplate;

    public CategoryCustomRepoImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Optional<Category> existByName(String name) {
        Query query = new Query();

        query.addCriteria(Criteria.where("name").is(name));
        return Optional.ofNullable(mongoTemplate.findOne(query, Category.class));
    }
}
