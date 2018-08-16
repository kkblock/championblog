package com.champion.blog.dao;

import com.champion.blog.model.City;

public interface CityDao {

    void saveCity(City city);

    City findCityById(String id);

    void updateCity(City city);

    void deleteCityById(String id);

}
