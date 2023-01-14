package com.whn.getaway_service.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.whn.getaway_service.common.ResponseResult;
import com.whn.getaway_service.common.ResultCode;
import com.whn.getaway_service.utils.JwtOperator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.net.URI;
import java.util.List;

/**
 * @author : WangRich
 * @Description : description
 * @date : 2022/10/9 15:42
 */
@Component
@Slf4j
public class LoginGatewayFilterFactory extends AbstractGatewayFilterFactory<LoginGatewayFilterFactory.Config> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    private static final String TOKEN_HEADER = "X-Token";

    @Resource
    private JwtOperator jwtOperator;

    public LoginGatewayFilterFactory() {
        super(Config.class);
    }

    @Override
    public List<String> shortcutFieldOrder() {
        return List.of("enabled");
    }

    @Override
    public GatewayFilter apply(Config config) {
        log.info("LoginGatewayFilterFactory begin");
        return (exchange, chain) -> {
            if (!config.isEnabled()){
                return chain.filter(exchange);
            }
            ServerHttpRequest request = exchange.getRequest();
            URI uri = request.getURI();
            if ("/user/login".equals(uri.getPath())){
                return chain.filter(exchange);
            }
            HttpHeaders headers = request.getHeaders();
            String token = headers.getFirst(TOKEN_HEADER);
            log.info("token:"+token);
            ServerHttpResponse response = exchange.getResponse();
            DataBufferFactory bufferFactory = response.bufferFactory();
            if (token == null){
                log.error("token 为空");
                try {
                    DataBuffer wrap = bufferFactory.wrap(objectMapper.writeValueAsBytes(ResponseResult.failure(ResultCode.PARAM_IS_BLANK,"LoginGatewayFilterFactory----token为null")));
                    return response.writeWith(Mono.fromSupplier(() -> wrap));
                }catch (JsonProcessingException e){
                    log.error("token 为空");
                }
            }

            if (!jwtOperator.validateToken(token)){
                log.error("token 无效");
                try {
                    DataBuffer wrap = bufferFactory.wrap(objectMapper.writeValueAsBytes(ResponseResult.failure(ResultCode.PARAM_IS_BLANK,"LoginGatewayFilterFactory----token不合法")));
                    return response.writeWith(Mono.fromSupplier(() -> wrap));
                }catch (JsonProcessingException e){
                    log.error("token 无效");
                }
            }

            log.info("LoginGatewayFilterFactory end");
            ServerHttpRequest.Builder builder =exchange.getRequest().mutate();
            return chain.filter(exchange.mutate().request(builder.build()).build());

        };
    }

    public static class Config {
        private Boolean enabled;
        public Config() {

        }

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }
    }
}
