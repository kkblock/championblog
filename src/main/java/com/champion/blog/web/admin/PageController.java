package com.champion.blog.web.admin;

import com.champion.blog.constant.WebConst;
import com.champion.blog.dto.LogActions;
import com.champion.blog.dto.Types;
import com.champion.blog.model.bo.RestResponseBo;
import com.champion.blog.model.vo.ContentVo;
import com.champion.blog.model.vo.ContentVoExample;
import com.champion.blog.model.vo.UserVo;
import com.champion.blog.service.ContentService;
import com.champion.blog.service.LogService;
import com.champion.blog.web.BaseController;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller()
@RequestMapping("admin/page")
public class PageController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PageController.class);

    @Resource
    private ContentService contentsService;

    @Resource
    private LogService logService;

    @GetMapping(value = "")
    public String index(HttpServletRequest request) {
        ContentVoExample contentVoExample = new ContentVoExample();
        contentVoExample.setOrderByClause("created desc");
        contentVoExample.createCriteria().andTypeEqualTo(Types.PAGE.getType());
        PageInfo<ContentVo> contentsPaginator = contentsService.getArticlesWithpage(contentVoExample, 1, WebConst.MAX_POSTS);
        request.setAttribute("articles", contentsPaginator);
        return "admin/page_list";
    }

    @GetMapping(value = "new")
    public String newPage(HttpServletRequest request) {
        return "admin/page_edit";
    }

    @GetMapping(value = "/{cid}")
    public String editPage(@PathVariable String cid, HttpServletRequest request) {
        ContentVo contents = contentsService.getContents(cid);
        request.setAttribute("contents", contents);
        return "admin/page_edit";
    }

    @PostMapping(value = "publish")
    @ResponseBody
    public RestResponseBo publishPage(@RequestParam String title, @RequestParam String content,
                                      @RequestParam String status, @RequestParam String slug,
                                      @RequestParam(required = false) Integer allowComment, @RequestParam(required = false) Integer allowPing, HttpServletRequest request) {

        UserVo users = this.user(request);
        ContentVo contents = new ContentVo();
        contents.setTitle(title);
        contents.setContent(content);
        contents.setStatus(status);
        contents.setSlug(slug);
        contents.setType(Types.PAGE.getType());
        if (null != allowComment) {
            contents.setAllowComment(allowComment == 1);
        }
        if (null != allowPing) {
            contents.setAllowPing(allowPing == 1);
        }
        contents.setAuthorId(users.getUid());
        String result = contentsService.publish(contents);
        if (!WebConst.SUCCESS_RESULT.equals(result)) {
            return RestResponseBo.fail(result);
        }
        return RestResponseBo.ok();
    }

    @PostMapping(value = "modify")
    @ResponseBody
    public RestResponseBo modifyArticle(@RequestParam Integer cid, @RequestParam String title,
                                        @RequestParam String content,
                                        @RequestParam String status, @RequestParam String slug,
                                        @RequestParam(required = false) Integer allowComment, @RequestParam(required = false) Integer allowPing, HttpServletRequest request) {

        UserVo users = this.user(request);
        ContentVo contents = new ContentVo();
        contents.setCid(cid);
        contents.setTitle(title);
        contents.setContent(content);
        contents.setStatus(status);
        contents.setSlug(slug);
        contents.setType(Types.PAGE.getType());
        if (null != allowComment) {
            contents.setAllowComment(allowComment == 1);
        }
        if (null != allowPing) {
            contents.setAllowPing(allowPing == 1);
        }
        contents.setAuthorId(users.getUid());
        String result = contentsService.updateArticle(contents);
        if (!WebConst.SUCCESS_RESULT.equals(result)) {
            return RestResponseBo.fail(result);
        }
        return RestResponseBo.ok();
    }

    @RequestMapping(value = "delete")
    @ResponseBody
    public RestResponseBo delete(@RequestParam int cid, HttpServletRequest request) {
        String result = contentsService.deleteByCid(cid);
        logService.insertLog(LogActions.DEL_ARTICLE.getAction(), cid + "", request.getRemoteAddr(), this.getUid(request));
        if (!WebConst.SUCCESS_RESULT.equals(result)) {
            return RestResponseBo.fail(result);
        }
        return RestResponseBo.ok();
    }
}
