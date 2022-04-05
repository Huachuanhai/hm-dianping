package com.hmdp.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.stream.CollectorUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.hmdp.dto.Result;
import com.hmdp.entity.ShopType;
import com.hmdp.mapper.ShopTypeMapper;
import com.hmdp.service.IShopTypeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hmdp.utils.constant.redis.RedisKeyConstants;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 虎哥
 * @since 2021-12-22
 */
@Service
public class ShopTypeServiceImpl extends ServiceImpl<ShopTypeMapper, ShopType> implements IShopTypeService {

    @Resource
    private RedisTemplate redisTemplate;

    @Override
    public List<ShopType> queryList() {
        String key = RedisKeyConstants.CACHE_SHOP_TYPE_KEY;
        ListOperations<String, ShopType> listOperations = redisTemplate.opsForList();
        //1.从缓存中查询商铺分类信息
        List<ShopType> shopTypeList = listOperations.range(key, 0, -1);

        //2.判断缓存中有没有数据
        if(CollUtil.isNotEmpty(shopTypeList)){
            //3.有，返回
            return shopTypeList;
        }
        //4.无，从数据库中查询
        shopTypeList = query().list();
        //5.从数据库中查询的信息放到redis缓存
        listOperations.rightPushAll(key, shopTypeList);
        //6.返回
        return shopTypeList;
    }
}
