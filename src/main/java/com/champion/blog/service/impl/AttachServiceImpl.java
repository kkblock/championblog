package com.champion.blog.service.impl;

import com.champion.blog.dao.mysql.AttachVoMapper;
import com.champion.blog.model.vo.AttachVo;
import com.champion.blog.model.vo.AttachVoExample;
import com.champion.blog.service.AttachService;
import com.champion.blog.utils.DateKit;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service(value = "attachService")
public class AttachServiceImpl implements AttachService {

    private static final Logger LOG = LoggerFactory.getLogger(AttachServiceImpl.class);

    @Resource
    private AttachVoMapper attachDao;

    /**
     * 分页查询附件
     * @param page page
     * @param limit limit
     * @return PageInfo<AttachVo>
     */
    @Override
    public PageInfo<AttachVo> getAttachs(Integer page, Integer limit) {
        PageHelper.startPage(page, limit);
        AttachVoExample attachVoExample = new AttachVoExample();
        attachVoExample.setOrderByClause("id desc");
        List<AttachVo> attachVos = attachDao.selectByExample(attachVoExample);
        return new PageInfo<>(attachVos);
    }

    /**
     * 保存附件
     * @param fname fname
     * @param fkey fkey
     * @param ftype ftype
     * @param author author
     */
    @Override
    @Transactional
    public void save(String fname, String fkey, String ftype, Integer author) {
        AttachVo attach = new AttachVo();
        attach.setFname(fname);
        attach.setAuthorId(author);
        attach.setFkey(fkey);
        attach.setFtype(ftype);
        attach.setCreated(DateKit.getCurrentUnixTime());
        attachDao.insertSelective(attach);
    }

    /**
     * 根据附件id查询附件
     * @param id id
     * @return AttachVo
     */
    @Override
    public AttachVo selectById(Integer id) {
        if(null != id){
            return attachDao.selectByPrimaryKey(id);
        }
        return null;
    }

    @Override
    @Transactional
    public void deleteById(Integer id) {
        if (null != id) {
            attachDao.deleteByPrimaryKey( id);
        }
    }
}
