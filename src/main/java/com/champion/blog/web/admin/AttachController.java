package com.champion.blog.web.admin;

import com.champion.blog.utils.ChampionUtils;
import com.champion.blog.web.BaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 附件管理
 */
@Controller
@RequestMapping("admin/attach")
public class AttachController extends BaseController {

    private static final Logger LOG = LoggerFactory.getLogger(AttachController.class);

    public static final String CLASSPATH = ChampionUtils.getUploadFilePath();
}
