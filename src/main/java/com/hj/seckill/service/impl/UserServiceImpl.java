package com.hj.seckill.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hj.seckill.exception.GlobalException;
import com.hj.seckill.mapper.UserMapper;
import com.hj.seckill.pojo.User;
import com.hj.seckill.service.UserService;
import com.hj.seckill.utils.CookieUtil;
import com.hj.seckill.utils.MD5Util;
import com.hj.seckill.utils.UUIDUtil;
import com.hj.seckill.utils.ValidatorUtil;
import com.hj.seckill.vo.LoginVo;
import com.hj.seckill.vo.RespBean;
import com.hj.seckill.vo.RespBeanEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author hj
 * @since 2022-04-30
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public RespBean doLogin(LoginVo loginVo, HttpServletRequest request, HttpServletResponse response) {
        String mobile = loginVo.getMobile();
        String password = loginVo.getPassword();
//        //参数校验 f7230f74ff43122127856f1da81f2193
//        if (mobile.isEmpty() || password.isEmpty()) {
//            return RespBean.error(RespBeanEnum.LOGIN_ERROR);
//        }
//
//        if (!ValidatorUtil.isMobile(mobile)) {
//            return RespBean.error(RespBeanEnum.MOBILE_ERROR);
//        }

        User user = userMapper.selectById(mobile);
        if (user == null) {
//            return RespBean.error(RespBeanEnum.LOGIN_ERROR);
            throw new GlobalException(RespBeanEnum.LOGIN_ERROR);
        }
        if (!MD5Util.backendPasswordToDBPassword(password, user.getSalt()).equals(user.getPassword())) {
//            return RespBean.error(RespBeanEnum.LOGIN_ERROR);
            throw new GlobalException(RespBeanEnum.LOGIN_ERROR);
        }

        String ticket = UUIDUtil.uuid();
        //将用户信息存入redis
        redisTemplate.opsForValue().set("user:" + ticket, user);
//        request.getSession().setAttribute(ticket, user);
        CookieUtil.setCookie(request, response, "userTicket", ticket);
        return RespBean.success();
    }

    @Override
    public User getUserByCookie(String userTicket, HttpServletRequest request, HttpServletResponse response) {
        if (userTicket == null) {
            return null;
        }
        User user = (User) redisTemplate.opsForValue().get("user:" + userTicket);
        if (user != null) {
            CookieUtil.setCookie(request, response, "userTicket", userTicket);
        }
        return user;
    }
}
