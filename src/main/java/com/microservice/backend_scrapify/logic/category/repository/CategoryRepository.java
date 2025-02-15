package com.microservice.backend_scrapify.logic.category.repository;

import com.microservice.backend_scrapify.logic.category.entity.Category;
import com.microservice.backend_scrapify.logic.category.repository.customRepo.CategoryCustomRepo;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CategoryRepository extends MongoRepository<Category, Long>, CategoryCustomRepo {
}
