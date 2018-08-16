package com.champion.blog.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController extends BaseController{

    @RequestMapping("/index")
    public String indexHtml(){
        return "index";
    }
}
