package com.champion.blog.service.impl;

import com.champion.blog.dao.mysql.UserVoMapper;
import com.champion.blog.exception.TipException;
import com.champion.blog.model.vo.UserVo;
import com.champion.blog.model.vo.UserVoExample;
import com.champion.blog.service.UserService;
import com.champion.blog.utils.ChampionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * User service impl
 */
@Service(value = "userService")
public class UserServiceImpl implements UserService {

    private static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserVoMapper userDao;

    /**
     * 增加一条用户信息,用户名或邮箱不能为空,用户密码采用MD5username + passwd 加密
     * @param userVo 用户数据
     * @return uid
     */
    @Override
    @Transactional
    public Integer insertUser(UserVo userVo) {
        Integer uid = null;
        //用户名或邮箱不能为空
        if (StringUtils.isNotBlank(userVo.getUsername()) && StringUtils.isNotBlank(userVo.getEmail())) {
            //用户密码加密
            String encodePwd = ChampionUtils.MD5encode(userVo.getUsername() + userVo.getPassword());
            userVo.setPassword(encodePwd);
            userDao.insertSelective(userVo);
        }
        return userVo.getUid();
    }

    /**
     * 根据用户ID进行查询
     * @param uid  uid
     * @return UserVo
     */
    @Override
    public UserVo queryUserById(Integer uid) {
        UserVo userVo = null;
        if (uid != null) {
            userVo = userDao.selectByPrimaryKey(uid);
        }
        return userVo;
    }

    /**
     * UserVo
     * @param username username
     * @param password password
     * @return UserVo
     */
    @Override
    public UserVo login(String username, String password) {
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            throw new TipException("用户名和密码不能为空");
        }
        UserVoExample example = new UserVoExample();
        UserVoExample.Criteria criteria = example.createCriteria();
        criteria.andUsernameEqualTo(username);
        long count = userDao.countByExample(example);
        if (count < 1) {
            throw new TipException("不存在该用户");
        }
        String pwd = ChampionUtils.MD5encode(username + password);
        criteria.andPasswordEqualTo(pwd);
        List<UserVo> userVos = userDao.selectByExample(example);
        if (userVos.size() != 1) {
            throw new TipException("用户名或密码错误");
        }
        return userVos.get(0);
    }

    /**
     * 根据用户ID更新用户信息
     * @param userVo userVo
     */
    @Override
    @Transactional
    public void updateByUid(UserVo userVo) {
        if (null == userVo || null == userVo.getUid()) {
            throw new TipException("userVo is null");
        }
        int i = userDao.updateByPrimaryKeySelective(userVo);
        if (i != 1) {
            throw new TipException("update user by uid and retrun is not one");
        }
    }

}
