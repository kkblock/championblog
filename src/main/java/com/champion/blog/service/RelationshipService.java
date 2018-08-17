package com.champion.blog.service;

import com.champion.blog.model.vo.RelationshipVoKey;

import java.util.List;

public interface RelationshipService {

    /**
     * 按主键删除
     * @param cid cid
     * @param mid mid
     */
    void deleteById(Integer cid, Integer mid);

    /**
     * 按主键统计条数
     * @param cid cid
     * @param mid mid
     * @return 条数
     */
    Long countById(Integer cid, Integer mid);


    /**
     * 保存对象
     * @param relationshipVoKey RelationshipVoKey
     */
    void insertVo(RelationshipVoKey relationshipVoKey);

    /**
     * 根据id搜索
     * @param cid cid
     * @param mid mid
     * @return List<RelationshipVoKey>
     */
    List<RelationshipVoKey> getRelationshipById(Integer cid, Integer mid);
}
