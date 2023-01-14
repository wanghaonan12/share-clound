package com.whn.user_service.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whn.user_service.domain.UserOrder;
import com.whn.user_service.service.UserOrderService;
import com.whn.user_service.mapper.OrderMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author wangRich
* @description 针对表【order】的数据库操作Service实现
* @createDate 2023-01-06 11:49:24
*/
@Service
public class UserOrderServiceImpl extends ServiceImpl<OrderMapper, UserOrder>
    implements UserOrderService {

    @Override
    public Boolean create(UserOrder order) {
        return save(order);
    }

    @Override
    public Boolean update(UserOrder order) {
        return updateById(order);
    }

    @Override
    public List<UserOrder> finalOrderByUserId(String userId) {
        QueryWrapper<UserOrder> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        return list(queryWrapper);
    }

    @Override
    public UserOrder finalOrderById(String id) {
        return getById(id);
    }

    @Override
    public Boolean removeOrderByIds(List<String> ids) {
        return removeByIds(ids);
    }
}




