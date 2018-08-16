package com.champion.blog.service;

import com.champion.blog.model.City;
import org.springframework.data.domain.Page;

public interface CityService {

    public Page<City> paginationQuery(Integer pageNum);

}
