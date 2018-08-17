package com.champion.blog.service;

import com.champion.blog.model.vo.AttachVo;
import com.github.pagehelper.PageInfo;

public interface AttachService {

    /**
     * 分页查询附件
     * @param page page
     * @param limit limit
     * @return PageInfo<AttachVo>
     */
    PageInfo<AttachVo> getAttachs(Integer page, Integer limit);

    /**
     * 保存附件
     * @param fname fname
     * @param fkey fkey
     * @param ftype ftype
     * @param author author
     */
    void save(String fname, String fkey, String ftype, Integer author);

    /**
     * 根据附件id查询附件
     * @param id id
     * @return AttachVo
     */
    AttachVo selectById(Integer id);

    /**
     * 删除附件
     * @param id id
     */
    void deleteById(Integer id);
}
