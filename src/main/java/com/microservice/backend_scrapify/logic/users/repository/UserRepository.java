package com.microservice.backend_scrapify.logic.users.repository;

import com.microservice.backend_scrapify.logic.users.entity.Users;
import com.microservice.backend_scrapify.logic.users.repository.customRepo.UsersCustomRepo;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<Users, String>, UsersCustomRepo {
}
