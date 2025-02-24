package com.microservice.backend_scrapify.logic.repository;

import com.microservice.backend_scrapify.logic.entity.ScrapifyData;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ScrapifyRepository extends MongoRepository<ScrapifyData, Long> {
}
