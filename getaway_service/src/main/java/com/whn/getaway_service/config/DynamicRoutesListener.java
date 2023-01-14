package com.whn.getaway_service.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.api.config.listener.Listener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.Executor;

/**
 * @author : WangRich
 * @Description : description
 * @date : 2022/9/27 22:54
 */
@Slf4j
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DynamicRoutesListener implements Listener {

    private final GatewayService gatewayService;

    @Override
    public Executor getExecutor() {
        log.info("getExecutor");
        return null;
    }

    /**
     * 使用JSON转换，将plain text 变为RouteDefinition
     * @param configInfo 配置信息
     */
    @Override
    public void receiveConfigInfo(String configInfo) {
        log.info("received routes changes {}",configInfo);

        List<RouteDefinition> definitionList =  JSON.parseArray(configInfo, RouteDefinition.class);
        gatewayService.updateRoutes(definitionList);
    }
}
