package com.champion.blog.service;

import com.champion.blog.dto.MetaDto;
import com.champion.blog.model.vo.MetaVo;

import java.util.List;

/**
 * 分类信息service接口
 */
public interface MetaService {

    /**
     * 根据类型和名字查询项
     * @param type type
     * @param name name
     * @return MetaDto
     */
    MetaDto getMeta(String type, String name);

    /**
     * 根据文章id获取项目个数
     * @param mid mid
     * @return 项目个数
     */
    Integer countMeta(Integer mid);

    /**
     * 根据类型查询项目列表
     * @param types types
     * @return List<MetaVo>
     */
    List<MetaVo> getMetas(String types);


    /**
     * 保存多个项目
     * @param cid cid
     * @param names names
     * @param type type
     */
    void saveMetas(Integer cid, String names, String type);

    /**
     * 保存项目
     * @param type type
     * @param name name
     * @param mid mid
     */
    void saveMeta(String type, String name, Integer mid);

    /**
     * 根据类型查询项目列表，带项目下面的文章数
     * @return List<MetaDto>
     */
    List<MetaDto> getMetaList(String type, String orderby, int limit);

    /**
     * 删除项目
     * @param mid mid
     */
    void delete(int mid);

    /**
     * 保存项目
     * @param metas metas
     */
    void saveMeta(MetaVo metas);

    /**
     * 更新项目
     * @param metas metas
     */
    void update(MetaVo metas);
}
