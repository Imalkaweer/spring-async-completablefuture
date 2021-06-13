package com.aynccompletablefuture.demoasync.repository;

import com.aynccompletablefuture.demoasync.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
}
