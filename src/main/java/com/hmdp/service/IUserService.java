package com.hmdp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hmdp.dto.LoginFormDTO;
import com.hmdp.dto.Result;
import com.hmdp.entity.User;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author 虎哥
 * @since 2021-12-22
 */
public interface IUserService extends IService<User> {

    /**
     * 发生短信验证码
     *
     * @param phone 手机号码
     * @return Result
     */
    Result sendCode(String phone);

    /**
     * 登录
     *
     * @param loginForm 参数
     * @return Result
     */
    Result login(LoginFormDTO loginForm);
}
