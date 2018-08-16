package com.champion.blog.service;

import com.champion.blog.model.vo.ContentVo;
import org.springframework.data.domain.Page;

public interface ContentService {

    /**
     * 发布文章
     * @param contents Content
     */
    String publish(ContentVo contents);

    /**
     * 查询文章返回多条数据
     * @param p 当前页
     * @param limit 每页条数
     * @return ContentVo
     */
    Page<ContentVo> getContents(Integer p, Integer limit);

    /**
     * 根据id或slug获取文章
     *
     * @param id id
     * @return ContentVo
     */
    ContentVo getContents(String id);

    /**
     * 根据主键更新
     * @param contentVo contentVo
     */
    void updateContentByCid(ContentVo contentVo);

    /**
     * 查询分类/标签下的文章归档
     * @param mid mid
     * @param page page
     * @param limit limit
     * @return ContentVo
     */
    Page<ContentVo> getArticles(Integer mid, int page, int limit);

    /**
     * 搜索、分页
     * @param keyword keyword
     * @param page page
     * @param limit limit
     * @return ContentVo
     */
    Page<ContentVo> getArticles(String keyword,Integer page,Integer limit);

    /**
     * 根据文章id删除
     * @param cid cid
     */
    String deleteByCid(Integer cid);

    /**
     * 编辑文章
     * @param contents contents
     */
    String updateArticle(ContentVo contents);

    /**
     * 更新原有文章的category
     * @param ordinal ordinal
     * @param newCatefory newCatefory
     */
    void updateCategory(String ordinal,String newCatefory);

}
