package com.champion.blog.dao;

import com.champion.blog.model.PrimaryMongoObject;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PrimaryRepository extends MongoRepository<PrimaryMongoObject, String> {
}
