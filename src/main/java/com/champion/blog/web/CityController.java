package com.champion.blog.web;

import com.champion.blog.model.City;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/city")
public class CityController extends BaseController {

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public City findOneCity(@PathVariable("id") Long id, HttpServletRequest request) {
        City city = new City();
        city.setProvinceId(100L);
        city.setCityName("沧州");
        city.setDescription("老家");
        return city;
    }

}
