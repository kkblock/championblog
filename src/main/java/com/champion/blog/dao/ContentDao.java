package com.champion.blog.dao;

import com.champion.blog.model.vo.ContentVo;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ContentDao extends MongoRepository<ContentVo,String> {

    long countBySlug(String slug);
}
