package com.whn.content_service.auth;

import com.whn.content_service.utils.JwtOperator;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

    @Around("@annotation(com.whn.content_service.auth.CheckLogin)")
    public Object checkLogin(ProceedingJoinPoint point) throws Throwable {
        checkToken();
        return point.proceed();
    }

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
     * @return 用户id
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

    /**
     * 通过ProceedingJoinPoint对象的getArgs()我们可以得到传进来的参数。
     * 通过ProceedingJoinPoint对象的proceed()我们可以得到拿到切面方法返回值的对象。
     * @param pjp
     * @return
     * 环绕通知    首先是:包名  然后是: 类名  然后是方法名:方法名   括号内是:参数
     */
//    @Around(value = "execution(* com.whn.content_service.controller..*.*(..))")
    public Object handleControllerMethod(ProceedingJoinPoint pjp) throws Throwable {
        HttpServletRequest request=getHttpServletRequest();
        StringBuffer requestUrl = request.getRequestURL();
        log.info("HttpAspect handleControllerMethod filter start,requestURL:"+requestUrl);
        //获取参数
        Object[] objs = pjp.getArgs();
        for (Object obj:objs){
            log.info("参数:"+obj);
        }
        //获取返回对象
        Object object = pjp.proceed();
        log.info("获得返回对象 :{}",object);
        log.info("HttpAspect handleControllerMethod filter end");

        return pjp.proceed();//代理方法的返回值
    }
}
