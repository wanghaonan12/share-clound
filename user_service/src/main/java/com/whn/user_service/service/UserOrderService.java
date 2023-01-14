package com.whn.user_service.service;

import com.whn.user_service.domain.UserOrder;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author wangRich
 * @description 针对表【order】的数据库操作Service
 * @createDate 2023-01-06 11:49:24
 */
public interface UserOrderService extends IService<UserOrder> {

    /**
     *  创建订单
     * @param order order
     * @return 状态
     */
    Boolean create(UserOrder order);

    /**
     * 更新订单
     * @param order order
     * @return 状态
     */
    Boolean update(UserOrder order);

    /**
     *  根据用户id查找订单
     * @param userId  userId
     * @return  订单列表
     */
    List<UserOrder> finalOrderByUserId(String userId);

    /**
     *  根据id查找订单
     * @param id 订单id
     * @return order
     */
    UserOrder finalOrderById(String id);

    /**
     * 根据id删除订单
     * @param ids order id 列表
     * @return 状态
     */
    Boolean removeOrderByIds(List<String> ids);
}
