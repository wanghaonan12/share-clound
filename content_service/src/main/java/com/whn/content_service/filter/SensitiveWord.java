package com.whn.content_service.filter;

import com.whn.content_service.utils.WordFilter;
import lombok.SneakyThrows;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

@Aspect
@Component
public class SensitiveWord {
    /**
     *  @Around 表示作用的对象，在这里表示作用在post和put请求上
     */
    @Around("@annotation(org.springframework.web.bind.annotation.PostMapping)||@annotation(org.springframework.web.bind.annotation.PutMapping)")
    @SneakyThrows
    public Object doBefore(ProceedingJoinPoint point) {
//        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
//        if (request.getRequestURI().contains("/word/sensitiveword")) {
//            return point.proceed();
//        }
        //所有的参数对象
        for (Object arg : point.getArgs()) {
            //参数对象,通过反射将String类型的值进行敏感词过滤
            Class<?> aClass = arg.getClass();
            //递归遍历,将所有String参数进行敏感词匹配
            foundString(aClass,arg);
        }
        return  point.proceed();
    }
    @SneakyThrows
    public Class<?> foundString(Class clazz,Object arg ){
        Field[] declaredFields = clazz.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            Class<?> type = declaredField.getType();
            if (type==String.class&&!Modifier.toString(declaredField.getModifiers()).contains("final")){
                //如果是String类型,进行关键词匹配 且要排除final修饰的字段
                declaredField.setAccessible(true);
                String value=(String)declaredField.get(arg);
                declaredField.set(arg, WordFilter.replaceWords(value));
            }else if (type.getPackage()!=null&&type.getPackage().getName().contains("com.bysk")){
                Method[] methods = clazz.getMethods();
                for (Method method : methods) {
                    String name = method.getName();
                    if (name.toLowerCase().contains("get"+declaredField.getName().toLowerCase())){
                        Object invoke = method.invoke(arg);
                        this.foundString(type,invoke);
                        break;
                    }
                }
            }
        }
        return  clazz;
    }
}
