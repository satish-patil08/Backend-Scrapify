package com.microservice.backend_scrapify.logic.category.repository.customRepo;

import com.microservice.backend_scrapify.logic.category.entity.Category;

import java.util.Optional;

public interface CategoryCustomRepo {
    Optional<Category> existByName(String name);
}
