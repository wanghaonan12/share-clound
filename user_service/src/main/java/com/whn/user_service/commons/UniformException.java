package com.whn.user_service.commons;

import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author : WangRich
 * @Description : description
 * @date : 2023/1/2 13:05
 */
@Controller
public class UniformException implements HandlerExceptionResolver {
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception e) {
        ModelAndView mv = new ModelAndView(new MappingJackson2JsonView());
        mv.addObject("code","99999");
        if(e instanceof NullPointerException){
            mv.addObject("msg","空指针啦赶紧去后端处理！");
        }else if(e instanceof ClassCastException){
            mv.addObject("msg","类型转换出错啦，快去找处理，不然你想等他自己好吗？咱就不能好好使用接口，服了你！");
        }else{
            mv.addObject("msg","好家伙哥伦布发现新大陆，你的操作发现新异常！在下佩服！");
        }
        mv.addObject("data",e);
        return mv;
    }
}
