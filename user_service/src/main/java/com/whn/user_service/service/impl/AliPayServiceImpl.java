package com.whn.user_service.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.whn.user_service.config.AlipayConfig;
import com.whn.user_service.server.WebSocketServer;
import com.whn.user_service.domain.UserOrder;
import com.whn.user_service.po.BoundsDetailPo;
import com.whn.user_service.service.AliPayService;
import com.whn.user_service.service.BoundsDetailService;
import com.whn.user_service.service.UserOrderService;
import com.whn.user_service.utils.GenerateNum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author : WangRich
 * @Description : description
 * @date : 2023/1/6 10:52
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AliPayServiceImpl implements AliPayService {
    private final BoundsDetailService boundsDetailService;
    private final UserOrderService orderService;
    private final WebSocketServer webscoketServer;
    private static final String GATEWAY_URL = "https://openapi.alipaydev.com/gateway.do";
    private static final String FORMAT = "JSON";
    private static final String CHARSET = "UTF-8";
    //签名方式
    private static final String SIGN_TYPE = "RSA2";
    private static final String PRODUCT_CODE = "FAST_INSTANT_TRADE_PAY";
    private static final String SUBJECT = "购买积分";

    public final AlipayConfig aliPayConfig;

    @Override
    public String createAliPay(String total, String userId) {
        // 1. 创建Client，通用SDK提供的Client，负责调用支付宝的API
        AlipayClient alipayClient = new DefaultAlipayClient(GATEWAY_URL,
                aliPayConfig.getAppId(),
                aliPayConfig.getAppPrivateKey(),
                FORMAT,
                CHARSET,
                aliPayConfig.getAlipayPublicKey(),
                SIGN_TYPE);
        // 2. 创建 Request并设置Request参数 发送请求的 Request类
        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
        request.setNotifyUrl(aliPayConfig.getNotifyUrl());
        String outTradeNo = GenerateNum.generateOrder();
        JSONObject bizContent = new JSONObject();
        // 我们自己生成的订单编号
        bizContent.put("out_trade_no", outTradeNo);
        // 订单的总金额
        bizContent.put("total_amount", total);
        // 支付的名称
        bizContent.put("subject", SUBJECT);
        // 固定配置
        bizContent.put("product_code", PRODUCT_CODE);
        request.setBizContent(bizContent.toString());
        // 执行请求，拿到响应的结果，返回给浏览器
        String form = "ERROR";
        try {
            // 调用SDK生成表单
            form = alipayClient.pageExecute(request).getBody();
            // 提交创建订单
            orderService.create(UserOrder.builder().id(outTradeNo).total(total).userId(userId).orderName(SUBJECT).status("待支付").build());
        } catch (AlipayApiException e) {
            log.error("支付失败");
            e.printStackTrace();
        }
        return form;
    }

    @Override
    public String aliPayNotify(Map<String, String> map) {
        if ("TRADE_SUCCESS".equals(map.get("trade_status"))) {
            String payTime = map.get("gmt_create");
            String tradeNo = map.get("out_trade_no");
            String tradeName = map.get("subject");
            String payAmount = map.get("total_amount");
            payAmount = payAmount.substring(0, payAmount.indexOf("."));
            log.info("[支付成功] {交易时间：{},订单号：{},订单名称：{},交易金额：{}}", payTime, tradeNo, tradeName, payAmount);
            UserOrder order = orderService.finalOrderById(tradeNo);
            order.setStatus("以支付到账");
            String userId = order.getUserId();
            Boolean update = orderService.update(order);
            if (update) {
                try {
                    boundsDetailService.createBounds(BoundsDetailPo.builder().userId(userId).value(Integer.valueOf(payAmount)).event("1").description(SUBJECT).build());
                    String content ="[支付成功] 交易时间："+payTime+",订单号："+tradeNo+",订单名称："+tradeName+",交易金额："+payAmount;
                    webscoketServer.sendMessage(content,userId);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return "success";
    }
}
