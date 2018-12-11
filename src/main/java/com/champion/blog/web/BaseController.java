package com.champion.blog.web;

import com.champion.blog.model.vo.UserVo;
import com.champion.blog.utils.ChampionUtils;
import com.champion.blog.utils.EhcacheUtils;
import com.champion.blog.utils.MapCache;
import org.ehcache.Cache;
import org.ehcache.CacheManager;

import javax.servlet.http.HttpServletRequest;

/**
 * controller abstract , some public...
 */
public abstract class BaseController {

//    protected static String THEME = "themes/default";
      protected static String THEME = "themes/semantic";

    // 初始化Ehcache对象
//    protected  CacheManager cacheManager = new EhcacheUtils().getInstance();
    // 获取初始化的缓存对象
//    protected  Cache<String, String> mineCache = cacheManager.getCache("defaultCache", String.class, String.class);

    protected MapCache cache = MapCache.single();
    /**
     * 主页的页面主题
     * @param viewName viewName
     * @return total viewName
     */
    public String render(String viewName) {
        return THEME + "/" + viewName;
    }

    /**
     * set title
     * @param request HttpServletRequest
     * @param title title
     * @return BaseController
     */
    public BaseController title(HttpServletRequest request, String title) {
        request.setAttribute("title", title);
        return this;
    }

    /**
     * set keywords
     * @param request HttpServletRequest
     * @param keywords keywords
     * @return BaseController
     */
    public BaseController keywords(HttpServletRequest request, String keywords) {
        request.setAttribute("keywords", keywords);
        return this;
    }

    /**
     * 获取请求绑定的登录对象
     * @param request HttpServletRequest
     * @return this user
     */
    public UserVo user(HttpServletRequest request) {
        return ChampionUtils.getLoginUser(request);
    }

    /**
     * get this user's id
     * @param request HttpServletRequest
     * @return user's id
     */
    public Integer getUid(HttpServletRequest request){
        return this.user(request).getUid();
    }

    /**
     *  render 404 page
     * @return 404 page address
     */
    public String render_404() {
        return "comm/error_404";
    }
}
