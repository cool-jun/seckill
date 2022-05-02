package com.hj.seckill.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hj.seckill.pojo.User;
import com.hj.seckill.vo.LoginVo;
import com.hj.seckill.vo.RespBean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author hj
 * @since 2022-04-30
 */
public interface UserService extends IService<User> {

    RespBean doLogin(LoginVo loginVo, HttpServletRequest request, HttpServletResponse response);
}
