package com.champion.blog.web;

import com.champion.blog.constant.WebConst;
import com.champion.blog.dto.ErrorCode;
import com.champion.blog.dto.MetaDto;
import com.champion.blog.dto.Types;
import com.champion.blog.model.bo.ArchiveBo;
import com.champion.blog.model.bo.CommentBo;
import com.champion.blog.model.bo.RestResponseBo;
import com.champion.blog.model.vo.CommentVo;
import com.champion.blog.model.vo.ContentVo;
import com.champion.blog.model.vo.ContentVoExample;
import com.champion.blog.model.vo.MetaVo;
import com.champion.blog.service.CommentService;
import com.champion.blog.service.ContentService;
import com.champion.blog.service.MetaService;
import com.champion.blog.service.SiteService;
import com.champion.blog.utils.ChampionUtils;
import com.champion.blog.utils.Commons;
import com.champion.blog.utils.IPKit;
import com.champion.blog.utils.PatternKit;
import com.github.pagehelper.PageInfo;
import com.vdurmont.emoji.EmojiParser;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class IndexController extends BaseController {

    /**
     * 首页跳转
     *
     * @return index.html
     */
    @RequestMapping("/index")
    public String indexHtml() {
        return "index";
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(IndexController.class);

    @Resource
    private ContentService contentService;

    @Resource
    private CommentService commentService;

    @Resource
    private MetaService metaService;

    @Resource
    private SiteService siteService;

    /**
     * 首页
     *
     * @return this.index(request, 1, limit);
     */
    @GetMapping(value = "/")
    public String index(HttpServletRequest request, @RequestParam(value = "limit", defaultValue = "15") int limit) {
        return this.index(request, 1, limit);
    }

    /**
     * 首页分页
     *
     * @param request request
     * @param p       第几页
     * @param limit   每页大小
     * @return 主页
     */
    @GetMapping(value = "page/{p}")
    public String index(HttpServletRequest request, @PathVariable int p, @RequestParam(value = "limit", defaultValue = "15") int limit) {
        p = p < 0 || p > WebConst.MAX_PAGE ? 1 : p;
        PageInfo<ContentVo> articles = contentService.getContents(p, limit);
        List<ContentVo> newestArticles = contentService.getNewestArticles( 10);
        Map<String, List<ContentVo>> categoryContents = contentService.findContentsByCategory("java", "software");//这里根据本人情况设置指定的分类
        List<MetaDto> tags = metaService.getMetaList(Types.TAG.getType(), null, 20);//WebConst.MAX_POSTS
        request.setAttribute("tags", tags);
        request.setAttribute("articles", articles);
        request.setAttribute("newestArticles", newestArticles);
        request.setAttribute("categoryContents",categoryContents);
        if (p > 1) {
            this.title(request, "第" + p + "页");
        }
        return this.render("index");
    }

    /**
     * 文章页
     *
     * @param request 请求
     * @param cid     文章主键
     * @return this.render()
     */
    @GetMapping(value = {"article/{cid}", "article/{cid}.html"})
    public String getArticle(HttpServletRequest request, @PathVariable String cid) {
        ContentVo contents = contentService.getContents(cid);
        if (null == contents || "draft".equals(contents.getStatus())) {
            return this.render_404();
        }
        request.setAttribute("article", contents);
        request.setAttribute("is_post", true);
        completeArticle(request, contents);
        if (!checkHitsFrequency(request, cid)) {
            updateArticleHit(contents.getCid(), contents.getHits());
        }
        return this.render("post");


    }

    /**
     * 文章页(预览)
     *
     * @param request 请求
     * @param cid     文章主键
     * @return this.render()
     */
    @GetMapping(value = {"article/{cid}/preview", "article/{cid}.html"})
    public String articlePreview(HttpServletRequest request, @PathVariable String cid) {
        ContentVo contents = contentService.getContents(cid);
        if (null == contents) {
            return this.render_404();
        }
        request.setAttribute("article", contents);
        request.setAttribute("is_post", true);
        completeArticle(request, contents);
        if (!checkHitsFrequency(request, cid)) {
            updateArticleHit(contents.getCid(), contents.getHits());
        }
        return this.render("post");


    }

    /**
     * 抽取公共方法
     *
     * @param request  HttpServletRequest
     * @param contents ContentVo
     */
    private void completeArticle(HttpServletRequest request, ContentVo contents) {
        if (contents.getAllowComment()) {
            String cp = request.getParameter("cp");
            if (StringUtils.isBlank(cp)) {
                cp = "1";
            }
            request.setAttribute("cp", cp);
            PageInfo<CommentBo> commentsPaginator = commentService.getComments(contents.getCid(), Integer.parseInt(cp), 6);
            request.setAttribute("comments", commentsPaginator);
        }
    }

    /**
     * 注销
     *
     * @param session  HttpSession
     * @param response HttpServletResponse
     */
    @RequestMapping("logout")
    public void logout(HttpSession session, HttpServletResponse response) {
        ChampionUtils.logout(session, response);
    }

    /**
     * 评论操作
     */
    @PostMapping(value = "comment")
    @ResponseBody
    public RestResponseBo comment(HttpServletRequest request, HttpServletResponse response,
                                  @RequestParam Integer cid, @RequestParam Integer coid,
                                  @RequestParam String author, @RequestParam String mail,
                                  @RequestParam String url, @RequestParam String text, @RequestParam String _csrf_token) {

        String ref = request.getHeader("Referer");
        if (StringUtils.isBlank(ref) || StringUtils.isBlank(_csrf_token)) {
            return RestResponseBo.fail(ErrorCode.BAD_REQUEST);
        }

        String token = cache.hget(Types.CSRF_TOKEN.getType(), _csrf_token);
        if (StringUtils.isBlank(token)) {
            return RestResponseBo.fail(ErrorCode.BAD_REQUEST);
        }

        if (null == cid || StringUtils.isBlank(text)) {
            return RestResponseBo.fail("请输入完整后评论");
        }

        if (StringUtils.isNotBlank(author) && author.length() > 50) {
            return RestResponseBo.fail("姓名过长");
        }

        if (StringUtils.isNotBlank(mail) && !ChampionUtils.isEmail(mail)) {
            return RestResponseBo.fail("请输入正确的邮箱格式");
        }

        if (StringUtils.isNotBlank(url) && !PatternKit.isURL(url)) {
            return RestResponseBo.fail("请输入正确的URL格式");
        }

        if (text.length() > 200) {
            return RestResponseBo.fail("请输入200个字符以内的评论");
        }

        String val = IPKit.getIpAddrByRequest(request) + ":" + cid;
        Integer count = cache.hget(Types.COMMENTS_FREQUENCY.getType(), val);
        if (null != count && count > 0) {
            return RestResponseBo.fail("您发表评论太快了，请过会再试");
        }

        author = ChampionUtils.cleanXSS(author);
        text = ChampionUtils.cleanXSS(text);

        author = EmojiParser.parseToAliases(author);
        text = EmojiParser.parseToAliases(text);

        CommentVo comments = new CommentVo();
        comments.setAuthor(author);
        comments.setCid(cid);
        comments.setIp(request.getRemoteAddr());
        comments.setUrl(url);
        comments.setContent(text);
        comments.setMail(mail);
        comments.setParent(coid);
        try {
            String result = commentService.insertComment(comments);
            cookie("tale_remember_author", URLEncoder.encode(author, "UTF-8"), 7 * 24 * 60 * 60, response);
            cookie("tale_remember_mail", URLEncoder.encode(mail, "UTF-8"), 7 * 24 * 60 * 60, response);
            if (StringUtils.isNotBlank(url)) {
                cookie("tale_remember_url", URLEncoder.encode(url, "UTF-8"), 7 * 24 * 60 * 60, response);
            }
            // 设置对每个文章1分钟可以评论一次
            cache.hset(Types.COMMENTS_FREQUENCY.getType(), val, 1, 60);
            if (!WebConst.SUCCESS_RESULT.equals(result)) {
                return RestResponseBo.fail(result);
            }
            return RestResponseBo.ok();
        } catch (Exception e) {
            String msg = "评论发布失败";
            LOGGER.error(msg, e);
            return RestResponseBo.fail(msg);
        }
    }


    /**
     * 分类页
     *
     * @return this.categories(request, keyword, 1, limit);
     */
    @GetMapping(value = "category/{keyword}")
    public String categories(HttpServletRequest request, @PathVariable String keyword, @RequestParam(value = "limit", defaultValue = "15") int limit) {
        return this.categories(request, keyword, 1, limit);
    }

    @GetMapping(value = "category/{keyword}/{page}")
    public String categories(HttpServletRequest request, @PathVariable String keyword,
                             @PathVariable int page, @RequestParam(value = "limit", defaultValue = "15") int limit) {
        page = page < 0 || page > WebConst.MAX_PAGE ? 1 : page;
        MetaDto metaDto = metaService.getMeta(Types.CATEGORY.getType(), keyword);
        if (null == metaDto) {
            return this.render_404();
        }

        PageInfo<ContentVo> contentsPaginator = contentService.getArticles(metaDto.getMid(), page, limit);

        request.setAttribute("articles", contentsPaginator);
        request.setAttribute("meta", metaDto);
        request.setAttribute("type", "分类");
        request.setAttribute("keyword", keyword);

        return this.render("page-category");
    }


    /**
     * 归档页
     *
     * @return this.render(" archives ");
     */
    @GetMapping(value = "archives")
    public String archives(HttpServletRequest request) {
        List<ArchiveBo> archives = siteService.getArchives();
        request.setAttribute("archives", archives);
        return this.render("archives");
    }

    /**
     * 根据类别归档
     *
     * @param request request
     * @param limit   limit
     * @return this.archivesByCategories(request, 1, limit);
     */
    @GetMapping(value = "archives/category")
    public String archivesByCategories(HttpServletRequest request, @RequestParam(value = "limit", defaultValue = "15") int limit) {
        return this.archivesByCategories(request, 1, limit);
    }

    @GetMapping(value = "archives/category/{page}")
    public String archivesByCategories(HttpServletRequest request, @PathVariable int page, @RequestParam(value = "limit", defaultValue = "15") int limit) {
        page = page < 0 || page > WebConst.MAX_PAGE ? 1 : page;
        List<MetaVo> metas = metaService.getMetas(Types.CATEGORY.getType());

        ContentVoExample contentVoExample = new ContentVoExample();
        contentVoExample.setOrderByClause("created desc");
        ContentVoExample.Criteria criteria = contentVoExample.createCriteria();
        criteria.andTypeEqualTo(Types.ARTICLE.getType()).andStatusEqualTo("publish");
        List<String> metaCates = new ArrayList<>();
        for (MetaVo meta : metas) {
            metaCates.add(meta.getName());
        }
        criteria.andCategoriesIn(metaCates);
        PageInfo<ContentVo> contentsPaginator = contentService.getArticlesWithpage(contentVoExample, page, limit);
        request.setAttribute("articles", contentsPaginator);
        request.setAttribute("type", "归档");
        request.setAttribute("keyword", "类别");
        return this.render("page-category");
    }


    /**
     * 友链页
     *
     * @return this.render(" links ");
     */
    @GetMapping(value = "links")
    public String links(HttpServletRequest request) {
        List<MetaVo> links = metaService.getMetas(Types.LINK.getType());
        request.setAttribute("links", links);
        return this.render("links");
    }

    @GetMapping(value = "about")
    public String about(HttpServletRequest request) {
        return this.render("about");
    }

    /**
     * 自定义页面,如关于的页面
     */
    @GetMapping(value = "/{pagename}")
    public String page(@PathVariable String pagename, HttpServletRequest request) {
        ContentVo contents = contentService.getContents(pagename);
        if (null == contents) {
            return this.render_404();
        }
        if (contents.getAllowComment()) {
            String cp = request.getParameter("cp");
            if (StringUtils.isBlank(cp)) {
                cp = "1";
            }
            PageInfo<CommentBo> commentsPaginator = commentService.getComments(contents.getCid(), Integer.parseInt(cp), 6);
            request.setAttribute("comments", commentsPaginator);
        }
        request.setAttribute("article", contents);
        if (!checkHitsFrequency(request, String.valueOf(contents.getCid()))) {
            updateArticleHit(contents.getCid(), contents.getHits());
        }
        return this.render("page");
    }


    /**
     * 搜索页
     *
     * @param keyword keyword
     * @return this.search(request, keyword, 1, limit);
     */
    @GetMapping(value = "search/{keyword}")
    public String search(HttpServletRequest request, @PathVariable String keyword, @RequestParam(value = "limit", defaultValue = "15") int limit) {
        return this.search(request, keyword, 1, limit);
    }

    @GetMapping(value = "search/{keyword}/{page}")
    public String search(HttpServletRequest request, @PathVariable String keyword, @PathVariable int page, @RequestParam(value = "limit", defaultValue = "15") int limit) {
        page = page < 0 || page > WebConst.MAX_PAGE ? 1 : page;
        PageInfo<ContentVo> articles = contentService.getArticles(keyword, page, limit);
        request.setAttribute("articles", articles);
        request.setAttribute("type", "搜索");
        request.setAttribute("keyword", keyword);
        return this.render("page-category");
    }

    /**
     * 根据发布作者搜索所有文章
     *
     * @param request HttpServletRequest
     * @param uid     uid
     * @param limit   limit
     * @return page-category
     */
    @GetMapping(value = "posted/{uid}")
    public String searchByAuthor(HttpServletRequest request, @PathVariable int uid, @RequestParam(value = "limit", defaultValue = "15") int limit) {
        return this.searchByAuthor(request, uid, 1, limit);
    }

    @GetMapping(value = "posted/{uid}/{page}")
    public String searchByAuthor(HttpServletRequest request, @PathVariable int uid, @PathVariable int page, @RequestParam(value = "limit", defaultValue = "15") int limit) {
        page = page < 0 || page > WebConst.MAX_PAGE ? 1 : page;
        PageInfo<ContentVo> articles = contentService.getArticlesByUid(uid, page, limit);
        request.setAttribute("articles", articles);
        request.setAttribute("type", "作者");
        request.setAttribute("keyword", Commons.getUserName(uid));
        return this.render("page-category");
    }

    /**
     * 更新文章的点击率
     *
     * @param cid   cid
     * @param chits chits
     */
    private void updateArticleHit(Integer cid, Integer chits) {
        Integer hits = cache.hget("article" + cid, "hits");
        if (chits == null) {
            chits = 0;
        }
        hits = null == hits ? 1 : hits + 1;
        if (hits >= WebConst.HIT_EXCEED) {
            ContentVo temp = new ContentVo();
            temp.setCid(cid);
            temp.setHits(chits + hits);
            contentService.updateContentByCid(temp);
            cache.hset("article" + cid, "hits", 1);
        } else {
            cache.hset("article" + cid, "hits", hits);
        }
    }

    /**
     * 标签页
     *
     * @param name name
     * @return this.tags(request, name, 1, limit);
     */
    @GetMapping(value = "tag/{name}")
    public String tags(HttpServletRequest request, @PathVariable String name, @RequestParam(value = "limit", defaultValue = "15") int limit) {
        return this.tags(request, name, 1, limit);
    }

    /**
     * 标签分页
     *
     * @param request HttpServletRequest
     * @param name    name
     * @param page    page
     * @param limit   limit
     * @return this.render(" page - category ");
     */
    @GetMapping(value = "tag/{name}/{page}")
    public String tags(HttpServletRequest request, @PathVariable String name, @PathVariable int page, @RequestParam(value = "limit", defaultValue = "15") int limit) {

        page = page < 0 || page > WebConst.MAX_PAGE ? 1 : page;
//        对于空格的特殊处理
        name = name.replaceAll("\\+", " ");
        MetaDto metaDto = metaService.getMeta(Types.TAG.getType(), name);
        if (null == metaDto) {
            return this.render_404();
        }

        PageInfo<ContentVo> contentsPaginator = contentService.getArticles(metaDto.getMid(), page, limit);
        request.setAttribute("articles", contentsPaginator);
        request.setAttribute("meta", metaDto);
        request.setAttribute("type", "标签");
        request.setAttribute("keyword", name);

        return this.render("page-category");
    }

    /**
     * 设置cookie
     *
     * @param name     name
     * @param value    value
     * @param maxAge   maxAge
     * @param response HttpServletResponse
     */
    private void cookie(String name, String value, int maxAge, HttpServletResponse response) {
        Cookie cookie = new Cookie(name, value);
        cookie.setMaxAge(maxAge);
        cookie.setSecure(false);
        response.addCookie(cookie);
    }

    /**
     * 检查同一个ip地址是否在2小时内访问同一文章
     *
     * @param request HttpServletRequest
     * @param cid     cid
     * @return boolean
     */
    private boolean checkHitsFrequency(HttpServletRequest request, String cid) {
        String val = IPKit.getIpAddrByRequest(request) + ":" + cid;
        Integer count = cache.hget(Types.HITS_FREQUENCY.getType(), val);
        if (null != count && count > 0) {
            return true;
        }
        cache.hset(Types.HITS_FREQUENCY.getType(), val, 1, WebConst.HITS_LIMIT_TIME);
        return false;
    }

}
