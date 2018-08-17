package com.champion.blog.service;

import com.champion.blog.dto.MetaDto;
import com.champion.blog.model.bo.ArchiveBo;
import com.champion.blog.model.bo.BackResponseBo;
import com.champion.blog.model.bo.StatisticsBo;
import com.champion.blog.model.vo.CommentVo;
import com.champion.blog.model.vo.ContentVo;

import java.util.List;

/**
 * 站点服务
 */
public interface SiteService {

    /**
     * 最新收到的评论
     * @param limit limit
     * @return List<CommentVo>
     */
    List<CommentVo> recentComments(int limit);

    /**
     * 最新发表的文章
     *
     * @param limit limit
     * @return List<ContentVo>
     */
    List<ContentVo> recentContents(int limit);

    /**
     * 查询一条评论
     * @param coid coid
     * @return CommentVo
     */
    CommentVo getComment(Integer coid);

    /**
     * 系统备份
     * @param bk_type bk_type
     * @param bk_path bk_path
     * @param fmt fmt
     * @return BackResponseBo
     */
    BackResponseBo backup(String bk_type, String bk_path, String fmt) throws Exception;


    /**
     * 获取后台统计数据
     * @return StatisticsBo
     */
    StatisticsBo getStatistics();

    /**
     * 查询文章归档
     * @return List<ArchiveBo>
     */
    List<ArchiveBo> getArchives();

    /**
     * 获取分类/标签列表
     * @return List<MetaDto>
     */
    List<MetaDto> metas(String type, String orderBy, int limit);
}
