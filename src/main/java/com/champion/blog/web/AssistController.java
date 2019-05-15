package com.champion.blog.web;

import com.champion.blog.utils.CaptchaUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Descroption: 辅助Controller
 **/
@Controller
@RequestMapping(value = "/assist")
public class AssistController {

    @GetMapping("/captcha")
    @ResponseBody
    public void captcha(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        CaptchaUtil.outputCaptcha(request, response);
    }

}
