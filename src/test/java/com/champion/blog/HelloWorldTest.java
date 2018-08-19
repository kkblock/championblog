package com.champion.blog;

import com.champion.blog.utils.EhcacheUtils;
import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HelloWorldTest {
    private static final Logger log = LoggerFactory.getLogger(HelloWorldTest.class);

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(new CityController()).build();
    }

    @Test
    public void testHelloWorld() throws Exception {
        log.debug("test city interface{}......","/city/1");
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/city/1")
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andReturn();
        String result = mvcResult.getResponse().getContentAsString();
        log.debug("test return data : {}",result);
    }

    @Test
    public void testCache() throws Exception{
        // 初始化Ehcache对象
        CacheManager cacheManager = new EhcacheUtils().getInstance();
        // 获取初始化的缓存对象
        Cache<String, String> mineCache = cacheManager.getCache("defaultCache", String.class, String.class);
        // 创建测试内容
        StringBuilder strTemp = new StringBuilder("测试");
        strTemp.append("测试一二三四五六七八九十");
        System.out.println("大小为：" + strTemp.toString().getBytes() + "Byte");
        // 存入第1条数据
        mineCache.put("key", strTemp.toString());
        // 取出并输出
        System.out.println("key:" + mineCache.get("key"));
        strTemp = new StringBuilder("测试2");
        // 存入第2条数据
        mineCache.put("key2", strTemp.toString());
        // 取出并输出
        System.out.println("key2:" + mineCache.get("key2"));
        // 取出并输出第一条数据,由于offheap的个存在所以当存入第2条数据时,第一条会被存储到offheap中而不会被淘汰
        System.out.println("key:" + mineCache.get("key"));
        // 关闭ehcache
        cacheManager.close();
    }
}
