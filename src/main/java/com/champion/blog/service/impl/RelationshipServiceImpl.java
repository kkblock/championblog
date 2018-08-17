package com.champion.blog.service.impl;

import com.champion.blog.dao.mysql.RelationshipVoMapper;
import com.champion.blog.model.vo.RelationshipVoExample;
import com.champion.blog.model.vo.RelationshipVoKey;
import com.champion.blog.service.RelationshipService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service(value = "relationshipService")
public class RelationshipServiceImpl implements RelationshipService {

    private static final Logger LOG = LoggerFactory.getLogger(RelationshipServiceImpl.class);

    @Autowired
    private RelationshipVoMapper relationshipVoMapper;

    /**
     * 按主键删除
     * @param cid cid
     * @param mid mid
     */
    @Override
    public void deleteById(Integer cid, Integer mid) {
        RelationshipVoExample relationshipVoExample = new RelationshipVoExample();
        RelationshipVoExample.Criteria criteria = relationshipVoExample.createCriteria();
        if (cid != null) {
            criteria.andCidEqualTo(cid);
        }
        if (mid != null) {
            criteria.andMidEqualTo(mid);
        }
        relationshipVoMapper.deleteByExample(relationshipVoExample);
    }

    /**
     * 根据id搜索
     * @param cid cid
     * @param mid mid
     * @return List<RelationshipVoKey>
     */
    @Override
    public List<RelationshipVoKey> getRelationshipById(Integer cid, Integer mid) {
        RelationshipVoExample relationshipVoExample = new RelationshipVoExample();
        RelationshipVoExample.Criteria criteria = relationshipVoExample.createCriteria();
        if (cid != null) {
            criteria.andCidEqualTo(cid);
        }
        if (mid != null) {
            criteria.andMidEqualTo(mid);
        }
        return relationshipVoMapper.selectByExample(relationshipVoExample);
    }

    /**
     * 保存对象
     * @param relationshipVoKey RelationshipVoKey
     */
    @Override
    public void insertVo(RelationshipVoKey relationshipVoKey) {
        relationshipVoMapper.insert(relationshipVoKey);
    }

    /**
     * 按主键统计条数
     * @param cid cid
     * @param mid mid
     * @return 条数
     */
    @Override
    public Long countById(Integer cid, Integer mid) {
        LOG.debug("Enter countById method:cid={},mid={}",cid,mid);
        RelationshipVoExample relationshipVoExample = new RelationshipVoExample();
        RelationshipVoExample.Criteria criteria = relationshipVoExample.createCriteria();
        if (cid != null) {
            criteria.andCidEqualTo(cid);
        }
        if (mid != null) {
            criteria.andMidEqualTo(mid);
        }
        long num = relationshipVoMapper.countByExample(relationshipVoExample);
        LOG.debug("Exit countById method return num={}",num);
        return num;
    }

}
