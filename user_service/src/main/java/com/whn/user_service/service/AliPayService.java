package com.whn.user_service.service;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author : WangRich
 * @Description : description
 * @date : 2023/1/6 10:49
 */
@Service
public interface AliPayService {

    /**
     *  创建订单并支付
     * @param total 价格
     * @param userId 用户
     * @return 回调
     */
    String createAliPay(String total,String userId);

    /**
     *  支付回调
     * @param map map
     * @return 支付成功
     */
    String aliPayNotify(Map<String, String> map);
}
