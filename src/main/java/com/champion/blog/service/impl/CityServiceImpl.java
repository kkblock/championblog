package com.champion.blog.service.impl;

import com.champion.blog.model.City;
import com.champion.blog.model.PageModel;
import com.champion.blog.model.SpringbootPageable;
import com.champion.blog.service.CityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

@Service
public class CityServiceImpl implements CityService {

    private static final Logger LOG = LoggerFactory.getLogger(CityServiceImpl.class);

    private final MongoTemplate mongoTemplate;

    @Autowired
    public CityServiceImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Page<City> paginationQuery(Integer pageNum) {
        SpringbootPageable pageable = new SpringbootPageable();
        PageModel pm=new PageModel();
        Query query = new Query();
        List<Order> orders = new ArrayList<>();  //排序
        orders.add(new Order(Direction.DESC, "province_id"));
        Sort sort = new Sort(orders);
        // 开始页
        pm.setPagenumber(pageNum);
        // 每页条数
        pm.setPagesize(2);
        // 排序
        pm.setSort(sort);
        pageable.setPage(pm);
        // 查询出一共的条数
        Long count = mongoTemplate.count(query, City.class);
        // 查询
        List<City> list = mongoTemplate.find(query.with(pageable), City.class);
        // 将集合与分页结果封装
        Page<City> pagelist = new PageImpl<City>(list, pageable, count);
        return pagelist;
    }
}
