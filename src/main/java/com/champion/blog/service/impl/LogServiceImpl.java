package com.champion.blog.service.impl;

import com.champion.blog.constant.WebConst;
import com.champion.blog.dao.mysql.LogVoMapper;
import com.champion.blog.model.vo.LogVo;
import com.champion.blog.model.vo.LogVoExample;
import com.champion.blog.service.LogService;
import com.champion.blog.utils.DateKit;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service(value = "logService")
public class LogServiceImpl implements LogService {
    private static final Logger LOG = LoggerFactory.getLogger(LogServiceImpl.class);

    @Resource
    private LogVoMapper logDao;

    /**
     * 保存操作日志
     * @param logVo LogVo
     */
    @Override
    public void insertLog(LogVo logVo) {
        logDao.insert(logVo);
    }

    /**
     *  保存
     * @param action action
     * @param data data
     * @param ip ip
     * @param authorId authorId
     */
    @Override
    public void insertLog(String action, String data, String ip, Integer authorId) {
        LogVo logs = new LogVo();
        logs.setAction(action);
        logs.setData(data);
        logs.setIp(ip);
        logs.setAuthorId(authorId);
        logs.setCreated(DateKit.getCurrentUnixTime());
        logDao.insert(logs);
    }

    /**
     * 获取日志分页
     * @param page 当前页
     * @param limit 每页条数
     * @return 日志
     */
    @Override
    public List<LogVo> getLogs(int page, int limit) {
        LOG.debug("Enter getLogs method:page={},linit={}",page,limit);
        if (page <= 0) {
            page = 1;
        }
        if (limit < 1 || limit > WebConst.MAX_POSTS) {
            limit = 10;
        }
        LogVoExample logVoExample = new LogVoExample();
        logVoExample.setOrderByClause("id desc");
        PageHelper.startPage((page - 1) * limit, limit);
        List<LogVo> logVos = logDao.selectByExample(logVoExample);
        LOG.debug("Exit getLogs method");
        return logVos;
    }

}
