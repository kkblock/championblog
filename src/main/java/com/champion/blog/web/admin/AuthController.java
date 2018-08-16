package com.champion.blog.web.admin;

import com.champion.blog.constant.WebConst;
import com.champion.blog.exception.ResultBody;
import com.champion.blog.model.vo.UserVo;
import com.champion.blog.utils.ChampionUtils;
import com.champion.blog.utils.EhcacheUtils;
import com.champion.blog.web.BaseController;
import org.apache.commons.lang3.StringUtils;
import org.ehcache.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 用户后台登录/登出 controller
 */
@Controller
@RequestMapping("/admin")
public class AuthController extends BaseController {

    private static final Logger LOG = LoggerFactory.getLogger(AuthController.class);

    @GetMapping(value = "/login")
    public String login() {
        return "admin/login";
    }

    /**
     * POST请求验证登录
     * @param username 用户名
     * @param password 密码
     * @param remeber_me 是否记住用户
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return ResultBody
     */
    @PostMapping(value = "/login")
    @ResponseBody
    public ResultBody doLogin(@RequestParam String username,
                              @RequestParam String password,
                              @RequestParam(required = false) String remeber_me,
                              HttpServletRequest request,
                              HttpServletResponse response){
        Cache<String,String> mineCache = EhcacheUtils.build().getminiCache();
        Integer error_count = Integer.valueOf(mineCache.get("login_error_count"));
        try {
            UserVo user = null;

            //将此用户信息放入session中
            request.getSession().setAttribute(WebConst.LOGIN_SESSION_KEY, user);

            //判断是否设置cookie
            if (StringUtils.isNotBlank(remeber_me)) {
                ChampionUtils.setCookie(response, user.getUid());
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
