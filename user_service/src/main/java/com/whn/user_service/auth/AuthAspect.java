package com.whn.user_service.auth;

import com.whn.user_service.utils.JwtOperator;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Objects;

/**
 * @author : WangRich
 * @Description : description
 * @date : 2022/10/4 13:59
 */
@Slf4j
@Aspect
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AuthAspect {

    private final JwtOperator jwtOperator;

    @Around("@annotation(com.whn.user_service.auth.CheckLogin)")
    public Object checkLogin(ProceedingJoinPoint point) throws Throwable {
        checkToken();
        return point.proceed();
    }

    /**
     * 登陆验证
     */
    private void checkToken() {
        try {
            HttpServletRequest request = getHttpServletRequest();
            String token = request.getHeader("X-Token");
            Boolean isValid = jwtOperator.validateToken(token);
            if (!isValid) {
                throw new RuntimeException("Token不合法isValid");
            }
            Claims claims = jwtOperator.getClaimsFromToken(token);
            request.setAttribute("id", claims.get("id"));
        } catch (Throwable throwable) {
            throw new RuntimeException("Token不合法catch");
        }
    }

    /**
     *  获取id
     * @return
     */
    public String getAttributeId() {
        Claims claims;
        HttpServletRequest request;
        try {
            request = getHttpServletRequest();
            String token = request.getHeader("X-Token");
            Boolean isValid = jwtOperator.validateToken(token);
            if (!isValid) {
                throw new RuntimeException("Token不合法isValid");
            }
             claims = jwtOperator.getClaimsFromToken(token);
            request.setAttribute("id", claims.get("id"));
        } catch (Throwable throwable) {
            throw new RuntimeException("Token不合法catch");
        }
        log.info((String) request.getAttribute("id"));
        return (String) claims.get("id");
    }

    public static HttpServletRequest getHttpServletRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes attributes = (ServletRequestAttributes) requestAttributes;
        assert attributes != null;
        return attributes.getRequest();
    }
}
