package com.champion.blog.dao.mysql;

import com.champion.blog.model.bo.ArchiveBo;
import com.champion.blog.model.vo.ContentVo;
import com.champion.blog.model.vo.ContentVoExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component(value = "contentDao")
public interface ContentVoMapper {
    long countByExample(ContentVoExample example);

    int deleteByExample(ContentVoExample example);

    int deleteByPrimaryKey(Integer cid);

    int insert(ContentVo record);

    int insertSelective(ContentVo record);

    List<ContentVo> selectByExampleWithBLOBs(ContentVoExample example);

    List<ContentVo> selectByExample(ContentVoExample example);

    ContentVo selectByPrimaryKey(Integer cid);

    int updateByExampleSelective(@Param("record") ContentVo record, @Param("example") ContentVoExample example);

    int updateByExampleWithBLOBs(@Param("record") ContentVo record, @Param("example") ContentVoExample example);

    int updateByExample(@Param("record") ContentVo record, @Param("example") ContentVoExample example);

    int updateByPrimaryKeySelective(ContentVo record);

    int updateByPrimaryKeyWithBLOBs(ContentVo record);

    int updateByPrimaryKey(ContentVo record);

    /**
     * 查询已发布得文章
     * @return List<ArchiveBo>
     */
    List<ArchiveBo> findReturnArchiveBo();

    /**
     * 通过mid 查询已经发布得文章
     * @param mid mid
     * @return List<ContentVo>
     */
    List<ContentVo> findByCatalog(Integer mid);

}