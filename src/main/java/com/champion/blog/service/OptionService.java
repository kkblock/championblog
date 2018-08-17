package com.champion.blog.service;

import com.champion.blog.model.vo.OptionVo;

import java.util.List;
import java.util.Map;

public interface OptionService {

    /**
     * 插入
     * @param optionVo OptionVo
     */
    void insertOption(OptionVo optionVo);

    /**
     * 插入
     * @param name name
     * @param value value
     */
    void insertOption(String name, String value);

    /**
     * 批量查询
     * @return List<OptionVo>
     */
    List<OptionVo> getOptions();

    /**
     * 保存一组配置
     * @param options options
     */
    void saveOptions(Map<String, String> options);

    /**
     * 通过名称来获取
     * @param name name
     * @return OptionVo
     */
    OptionVo getOptionByName(String name);
}
