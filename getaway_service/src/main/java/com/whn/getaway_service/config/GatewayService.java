package com.whn.getaway_service.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionWriter;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * @author : WangRich
 * @Description : description
 * @date : 2022/9/27 22:54
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class GatewayService {
    private final RouteDefinitionWriter routeDefinitionWriter;

    private final ApplicationEventPublisher publisher;

    public void updateRoutes(List<RouteDefinition> routes) {
        if (CollectionUtils.isEmpty(routes)){
            log.info("No routes found");
        }
        routes.forEach(route -> {
            try {
                routeDefinitionWriter.save(Mono.just(route)).subscribe();
                publisher.publishEvent(new RefreshRoutesEvent(this));
            }catch (Exception e){
                log.error("cannot update route id={}",route.getId());
            }
        });
    }
}