package com.champion.blog.dao.mysql;

import com.champion.blog.model.vo.AttachVo;
import com.champion.blog.model.vo.AttachVoExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository(value = "attachDao")
public interface AttachVoMapper {
    long countByExample(AttachVoExample example);

    int deleteByExample(AttachVoExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(AttachVo record);

    int insertSelective(AttachVo record);

    List<AttachVo> selectByExample(AttachVoExample example);

    AttachVo selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") AttachVo record, @Param("example") AttachVoExample example);

    int updateByExample(@Param("record") AttachVo record, @Param("example") AttachVoExample example);

    int updateByPrimaryKeySelective(AttachVo record);

    int updateByPrimaryKey(AttachVo record);
}