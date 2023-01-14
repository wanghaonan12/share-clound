package com.whn.getaway_service.config;

import com.alibaba.cloud.nacos.NacosConfigManager;
import com.alibaba.cloud.nacos.NacosConfigProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * @author : WangRich
 * @Description : description
 * @date : 2022/9/27 22:54
 */
@Slf4j
//@Configuration
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DynamicRoutesLoader implements InitializingBean {

    private final NacosConfigManager configManager;

    private final NacosConfigProperties configProperties;

    private final DynamicRoutesListener dynamicRoutesListenerDemo;

    private static final  String ROUTES_CONFIG = "routes-config.json";

    @Override
    public void afterPropertiesSet() throws Exception {
        //首次加载配置
        String routes = configManager.getConfigService().getConfig(ROUTES_CONFIG,configProperties.getGroup(),10000);
        dynamicRoutesListenerDemo.receiveConfigInfo(routes);
        //注册监听器
        configManager.getConfigService().addListener(ROUTES_CONFIG,configProperties.getGroup(),dynamicRoutesListenerDemo);
    }
}
