package com.champion.blog.service.impl;

import com.champion.blog.constant.WebConst;
import com.champion.blog.dao.ContentDao;
import com.champion.blog.model.vo.ContentVo;
import com.champion.blog.service.ContentService;
import com.champion.blog.utils.ChampionUtils;
import com.champion.blog.utils.DateKit;
import com.vdurmont.emoji.EmojiParser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class ContentServiceImpl implements ContentService {

    @Autowired
    private ContentDao contentDao;

    /**
     * 发布文章
     * @param contents Content
     */
    @Override
    public String publish(ContentVo contents) {

        //process check contents
        if (null == contents) {
            return "文章对象为空";
        }
        if (StringUtils.isBlank(contents.getTitle())) {
            return "文章标题不能为空";
        }
        if (StringUtils.isBlank(contents.getContent())) {
            return "文章内容不能为空";
        }
        int titleLength = contents.getTitle().length();
        if (titleLength > WebConst.MAX_TITLE_COUNT) {
            return "文章标题过长";
        }
        int contentLength = contents.getContent().length();
        if (contentLength > WebConst.MAX_TEXT_COUNT) {
            return "文章内容过长";
        }
        if (null == contents.getAuthorId()) {
            return "请登录后发布文章";
        }

        if (StringUtils.isNotBlank(contents.getSlug())) {
            if (contents.getSlug().length() < 5) {
                return "路径太短了";
            }
            if (!ChampionUtils.isPath(contents.getSlug())) {
                return "您输入的路径不合法";
            }
            long count = contentDao.countBySlug(contents.getSlug());
            if (count > 0) {
                return "该路径已经存在，请重新输入";
            }
        } else {
            contents.setSlug(null);
        }

        contents.setContent(EmojiParser.parseToAliases(contents.getContent()));

        int time = DateKit.getCurrentUnixTime();
        contents.setCreated(time);
        contents.setModified(time);
        contents.setHits(0);
        contents.setCommentsNum(0);

        String tags = contents.getTags();
        String categories = contents.getCategories();
        ContentVo contentVo = contentDao.save(contents);
        return null;
    }

    @Override
    public Page<ContentVo> getContents(Integer p, Integer limit) {
        return null;
    }

    @Override
    public ContentVo getContents(String id) {
        return null;
    }

    @Override
    public void updateContentByCid(ContentVo contentVo) {

    }

    @Override
    public Page<ContentVo> getArticles(Integer mid, int page, int limit) {
        return null;
    }

    @Override
    public Page<ContentVo> getArticles(String keyword, Integer page, Integer limit) {
        return null;
    }

    @Override
    public String deleteByCid(Integer cid) {
        return null;
    }

    @Override
    public String updateArticle(ContentVo contents) {
        return null;
    }

    @Override
    public void updateCategory(String ordinal, String newCatefory) {

    }
}
