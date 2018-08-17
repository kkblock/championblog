package com.champion.blog.service;

import com.champion.blog.model.bo.CommentBo;
import com.champion.blog.model.vo.CommentVo;
import com.champion.blog.model.vo.CommentVoExample;
import com.github.pagehelper.PageInfo;

public interface CommentService {

    /**
     * 保存对象
     * @param commentVo CommentVo
     */
    String insertComment(CommentVo commentVo);

    /**
     * 获取文章下的评论
     * @param cid cid
     * @param page page
     * @param limit limit
     * @return CommentBo
     */
    PageInfo<CommentBo> getComments(Integer cid, int page, int limit);

    /**
     * 获取文章下的评论
     * @param commentVoExample CommentVoExample
     * @param page page
     * @param limit limit
     * @return CommentVo
     */
    PageInfo<CommentVo> getCommentsWithPage(CommentVoExample commentVoExample, int page, int limit);


    /**
     * 根据主键查询评论
     * @param coid coid
     * @return CommentVo
     */
    CommentVo getCommentById(Integer coid);


    /**
     * 删除评论，暂时没用
     * @param coid coid
     * @param cid cid
     */
    void delete(Integer coid, Integer cid);

    /**
     * 更新评论状态
     * @param comments CommentVo
     */
    void update(CommentVo comments);
}
