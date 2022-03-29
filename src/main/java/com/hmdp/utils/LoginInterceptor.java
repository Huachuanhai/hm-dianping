package com.hmdp.utils;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.hmdp.dto.UserDTO;
import com.hmdp.entity.User;
import com.hmdp.utils.constant.UserConstant;
import com.hmdp.utils.constant.redis.RedisExpirationTimeConstant;
import com.hmdp.utils.constant.redis.RedisKeyConstants;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 拦截器
 *
 * @author 21027
 * @date 2022/3/27 21:49
 */
public class LoginInterceptor implements HandlerInterceptor {

    private StringRedisTemplate stringRedisTemplate;

    public LoginInterceptor(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //1.获取session
//        HttpSession session = request.getSession();
        //1.获取请求头中的token
        String token = request.getHeader("authorization");
        if (StrUtil.isBlank(token)) {
            //4.不存在，拦截，返回401状态码
            response.setStatus(401);
            return false;
        }
        //2.获取session中的用户
//        Object user = session.getAttribute(UserConstant.SESSION_USER);
        //2.基于token获取redis中的用户
        String key = RedisKeyConstants.LOGIN_TOKEN + token;
        Map<Object, Object> userMap = stringRedisTemplate.opsForHash().entries(key);

        //3.判断用户是否存在
        if (userMap.isEmpty()) {
            //4.不存在，拦截，返回401状态码
            response.setStatus(401);
            return false;
        }
        //5.将查询到的Hash数据转为UserDTO
        UserDTO userDTO = BeanUtil.fillBeanWithMap(userMap, new UserDTO(), false);
        //6.存在，保存用户信息到 ThreadLocal
        UserHolder.saveUser(BeanUtil.copyProperties(userDTO, UserDTO.class));
        //7.熟悉token有效期
        stringRedisTemplate.expire(key, RedisExpirationTimeConstant.LOGIN_USER_TTL, TimeUnit.MINUTES);
        //8.放行
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //移除用户
        UserHolder.removeUser();
    }
}
