package com.champion.blog;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@ComponentScan(basePackages = {
        "com.champion.blog.web", "com.champion.blog.service", "com.champion.blog.dao", "com.champion.blog.config",
        "com.champion.blog.properties","com.champion.blog.task","com.champion.blog.constant", "com.champion.blog.interceptor",
        "com.champion.blog.exception","com.champion.blog.utils"
})
@MapperScan(value = "com.champion.blog.dao")
@ImportResource(locations = {"classpath:/applicationContext.xml"})
@PropertySource(value = "classpath:/env.properties")
@EnableScheduling
@EnableCaching
@EnableTransactionManagement
public class ChampionblogApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChampionblogApplication.class, args);
    }
}
