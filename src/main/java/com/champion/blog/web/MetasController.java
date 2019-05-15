package com.champion.blog.web;

import com.champion.blog.dto.Types;
import com.champion.blog.model.vo.MetaVo;
import com.champion.blog.service.MetaService;
import com.champion.blog.utils.ResponseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @Descroption:
 **/
@Controller
@RequestMapping(value = "/metas")
public class MetasController {

    private static final Logger LOG = LoggerFactory.getLogger(MetasController.class);

    @Resource
    private MetaService metaService;

    @PostMapping("/alltags")
    @ResponseBody
    public void getAllTags(HttpServletRequest request, HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        List<MetaVo> metas = metaService.getMetas(Types.TAG.getType());
        List<String> tags = new ArrayList<>();
        if (Objects.nonNull(metas)) {
            metas.forEach(d->{
                tags.add(d.getName());
            });
        }
        ResponseUtils.write(response,tags);
    }
}
