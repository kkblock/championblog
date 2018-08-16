package com.champion.blog.dao.impl;

import cn.hutool.core.lang.Assert;
import com.champion.blog.dao.CityDao;
import com.champion.blog.model.City;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

@Repository(value = "cityDao")
public class CityDaoImpl implements CityDao {

    private static final Logger log = LoggerFactory.getLogger(CityDaoImpl.class);

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * save city data
     * @param city city
     */
    @Override
    public void saveCity(City city) {
        mongoTemplate.save(city);
        log.info("add mongodata id is {}",city.getId());
    }

    /**
     * find city by id
     * @param id id
     * @return city
     */
    @Override
    public City findCityById(String id) {
        Query query = new Query(Criteria.where("id").is(id));
        City city = mongoTemplate.findOne(query,City.class);
        return city;
    }


    /**
     * update city info
     * @param city city
     */
    @Override
    public void updateCity(City city) {
        Query query = new Query(Criteria.where("id").is(city.getId()));
        Update update = new Update()
                .set("cityName",city.getCityName())
                .set("description",city.getDescription());
        //更新查询返回结果集的第一条
        UpdateResult updateResult = mongoTemplate.updateFirst(query,update,City.class);
        Assert.isTrue(updateResult.wasAcknowledged());
    }

    /**
     * delete city by id
     * @param id id
     */
    @Override
    public void deleteCityById(String id) {
        Query query = new Query(Criteria.where("id").is(id));
        DeleteResult deleteResult = mongoTemplate.remove(query,City.class);
        Assert.isTrue(deleteResult.wasAcknowledged());
    }
}
