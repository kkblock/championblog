package com.champion.blog.service.impl;

import com.champion.blog.dao.mysql.ContentVoMapper;
import com.champion.blog.model.vo.ContentVo;
import com.champion.blog.model.vo.ContentVoExample;
import com.champion.blog.service.ContentService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class ContentServiceImpl implements ContentService {


    @Override
    public String publish(ContentVo contents) {
        return null;
    }

    @Override
    public Page<ContentVo> getContents(Integer p, Integer limit) {
        return null;
    }

    @Override
    public ContentVo getContents(String id) {
        return null;
    }

    @Override
    public void updateContentByCid(ContentVo contentVo) {

    }

    @Override
    public PageInfo<ContentVo> getArticles(Integer mid, int page, int limit) {
        return null;
    }

    @Override
    public PageInfo<ContentVo> getArticles(String keyword, Integer page, Integer limit) {
        return null;
    }

    @Override
    public PageInfo<ContentVo> getArticlesWithpage(ContentVoExample commentVoExample, Integer page, Integer limit) {
        return null;
    }

    @Override
    public String deleteByCid(Integer cid) {
        return null;
    }

    @Override
    public String updateArticle(ContentVo contents) {
        return null;
    }

    @Override
    public void updateCategory(String ordinal, String newCatefory) {

    }
}
