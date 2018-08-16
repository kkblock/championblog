package com.champion.blog.dao;

import com.champion.blog.model.City;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CityDaoTest {
    private static final Logger log = LoggerFactory.getLogger(CityDaoTest.class);

    private static final String _id = "5b6819f2165fde47c1617c8a";

    @Autowired
    private CityDao cityDao;

    @Test
    public void saveCity() throws Exception {
        City city = new City();
        city.setId(_id);
        city.setProvinceId(10L);
        city.setCityName("Ren Qiu");
        city.setDescription("this is a description");
        cityDao.saveCity(city);
    }

    @Test
    public void findCityById() throws Exception {
        City city = cityDao.findCityById(_id);
        log.info("find city id = {} ,{}",1,city.toString());
    }

    @Test
    public void updateCity() throws Exception {
        City city = new City();
        city.setId(_id);
        city.setCityName("cangzhou");
        city.setDescription("cangzhou's description");
        cityDao.updateCity(city);
    }

    @Test
    public void deleteCityById() throws Exception {
        cityDao.deleteCityById(_id);
    }
}
