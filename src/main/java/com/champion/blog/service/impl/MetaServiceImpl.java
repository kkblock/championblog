package com.champion.blog.service.impl;

import com.champion.blog.constant.WebConst;
import com.champion.blog.dao.mysql.MetaVoMapper;
import com.champion.blog.dto.MetaDto;
import com.champion.blog.dto.Types;
import com.champion.blog.exception.TipException;
import com.champion.blog.model.vo.ContentVo;
import com.champion.blog.model.vo.MetaVo;
import com.champion.blog.model.vo.MetaVoExample;
import com.champion.blog.model.vo.RelationshipVoKey;
import com.champion.blog.service.ContentService;
import com.champion.blog.service.MetaService;
import com.champion.blog.service.RelationshipService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(value = "metaService")
public class MetaServiceImpl implements MetaService {

    private static final Logger LOG = LoggerFactory.getLogger(MetaServiceImpl.class);

    @Autowired
    private MetaVoMapper metaDao;

    @Autowired
    private RelationshipService relationshipService;

    @Autowired
    private ContentService contentService;

    /**
     * 根据类型和名字查询项
     * @param type type
     * @param name name
     * @return MetaDto
     */
    @Override
    public MetaDto getMeta(String type, String name) {
        if (StringUtils.isNotBlank(type) && StringUtils.isNotBlank(name)) {
            return metaDao.selectDtoByNameAndType(name, type);
        }
        return null;
    }

    /**
     * 根据文章id获取项目个数
     * @param mid mid
     * @return 项目个数
     */
    @Override
    public Integer countMeta(Integer mid) {
        return metaDao.countWithSql(mid);
    }

    /**
     * 根据类型查询项目列表
     * @param types types
     * @return List<MetaVo>
     */
    @Override
    public List<MetaVo> getMetas(String types) {
        if (StringUtils.isNotBlank(types)) {
            MetaVoExample metaVoExample = new MetaVoExample();
            metaVoExample.setOrderByClause("sort desc, mid desc");
            metaVoExample.createCriteria().andTypeEqualTo(types);
            return metaDao.selectByExample(metaVoExample);
        }
        return null;
    }

    /**
     * 根据类型查询项目列表，带项目下面的文章数
     * @return List<MetaDto>
     */
    @Override
    public List<MetaDto> getMetaList(String type, String orderby, int limit) {
        if (StringUtils.isNotBlank(type)) {
            if (StringUtils.isBlank(orderby)) {
                orderby = "count desc, a.mid desc";
            }
            if (limit < 1 || limit > WebConst.MAX_POSTS) {
                limit = 10;
            }
            Map<String, Object> paraMap = new HashMap<>();
            paraMap.put("type", type);
            paraMap.put("order", orderby);
            paraMap.put("limit", limit);
            return metaDao.selectFromSql(paraMap);
        }
        return null;
    }

    /**
     * 删除项目
     * @param mid mid
     */
    @Override
    @Transactional
    public void delete(int mid) {
        MetaVo metas = metaDao.selectByPrimaryKey(mid);
        if (null != metas) {
            String type = metas.getType();
            String name = metas.getName();

            metaDao.deleteByPrimaryKey(mid);

            List<RelationshipVoKey> rlist = relationshipService.getRelationshipById(null, mid);
            if (null != rlist) {
                for (RelationshipVoKey r : rlist) {
                    ContentVo contents = contentService.getContents(String.valueOf(r.getCid()));
                    if (null != contents) {
                        ContentVo temp = new ContentVo();
                        temp.setCid(r.getCid());
                        if (type.equals(Types.CATEGORY.getType())) {
                            temp.setCategories(reMeta(name, contents.getCategories()));
                        }
                        if (type.equals(Types.TAG.getType())) {
                            temp.setTags(reMeta(name, contents.getTags()));
                        }
                        contentService.updateContentByCid(temp);
                    }
                }
            }
            relationshipService.deleteById(null, mid);
        }
    }

    /**
     * 保存项目
     * @param type type
     * @param name name
     * @param mid mid
     */
    @Override
    @Transactional
    public void saveMeta(String type, String name, Integer mid) {
        if (StringUtils.isNotBlank(type) && StringUtils.isNotBlank(name)) {
            MetaVoExample metaVoExample = new MetaVoExample();
            metaVoExample.createCriteria().andTypeEqualTo(type).andNameEqualTo(name);
            List<MetaVo> metaVos = metaDao.selectByExample(metaVoExample);
            MetaVo metas;
            if (metaVos.size() != 0) {
                throw new TipException("已经存在该项");
            } else {
                metas = new MetaVo();
                metas.setName(name);
                if (null != mid) {
                    MetaVo original = metaDao.selectByPrimaryKey(mid);
                    metas.setMid(mid);
                    metaDao.updateByPrimaryKeySelective(metas);
//                    更新原有文章的categories
                    contentService.updateCategory(original.getName(), name);
                } else {
                    metas.setType(type);
                    metaDao.insertSelective(metas);
                }
            }
        }
    }

    /**
     * 保存多个项目
     * @param cid cid
     * @param names names
     * @param type type
     */
    @Override
    @Transactional
    public void saveMetas(Integer cid, String names, String type) {
        if (null == cid) {
            throw new TipException("项目关联id不能为空");
        }
        if (StringUtils.isNotBlank(names) && StringUtils.isNotBlank(type)) {
            String[] nameArr = StringUtils.split(names, ",");
            for (String name : nameArr) {
                this.saveOrUpdate(cid, name, type);
            }
        }
    }

    private void saveOrUpdate(Integer cid, String name, String type) {
        MetaVoExample metaVoExample = new MetaVoExample();
        metaVoExample.createCriteria().andTypeEqualTo(type).andNameEqualTo(name);
        List<MetaVo> metaVos = metaDao.selectByExample(metaVoExample);

        int mid;
        MetaVo metas;
        if (metaVos.size() == 1) {
            metas = metaVos.get(0);
            mid = metas.getMid();
        } else if (metaVos.size() > 1) {
            throw new TipException("查询到多条数据");
        } else {
            metas = new MetaVo();
            metas.setSlug(name);
            metas.setName(name);
            metas.setType(type);
            metaDao.insertSelective(metas);
            mid = metas.getMid();
        }
        if (mid != 0) {
            Long count = relationshipService.countById(cid, mid);
            if (count == 0) {
                RelationshipVoKey relationships = new RelationshipVoKey();
                relationships.setCid(cid);
                relationships.setMid(mid);
                relationshipService.insertVo(relationships);
            }
        }
    }


    private String reMeta(String name, String metas) {
        String[] ms = StringUtils.split(metas, ",");
        StringBuilder sbuf = new StringBuilder();
        for (String m : ms) {
            if (!name.equals(m)) {
                sbuf.append(",").append(m);
            }
        }
        if (sbuf.length() > 0) {
            return sbuf.substring(1);
        }
        return "";
    }

    /**
     * 保存项目
     * @param metas metas
     */
    @Override
    @Transactional
    public void saveMeta(MetaVo metas) {
        if (null != metas) {
            metaDao.insertSelective(metas);
        }
    }

    /**
     * 更新项目
     * @param metas metas
     */
    @Override
    @Transactional
    public void update(MetaVo metas) {
        if (null != metas && null != metas.getMid()) {
            metaDao.updateByPrimaryKeySelective(metas);
        }
    }
}
