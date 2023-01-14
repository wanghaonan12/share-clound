package com.whn.content_service.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

/**
 * 同意异常处理
 * @author : WangRich
 * @Description : description
 * @date : 2023/1/2 13:28
 */
@ControllerAdvice
public class UniformExceptionController {


    /**
     * 专门用来捕获和处理Controller层的空指针异常
     * @param e 异常
     * @return ModelAndView
     */
    @ExceptionHandler(NullPointerException.class)
    public ModelAndView nullPointerExceptionHandler(NullPointerException e){
        ModelAndView mv = new ModelAndView(new MappingJackson2JsonView());
        mv.addObject("data",null);
        mv.addObject("code",99999);
        mv.addObject("msg","空指针啦赶紧去后端处理!");
        return mv;
    }

    /**
     * 专门用来捕获和处理Controller层的运行时异常
     * @param e 异常
     * @return ModelAndView
     */
    @ExceptionHandler(RuntimeException.class)
    public ModelAndView runtimeExceptionHandler(RuntimeException e){
        ModelAndView mv = new ModelAndView(new MappingJackson2JsonView());
        mv.addObject("data",null);
        mv.addObject("code",99999);
        mv.addObject("msg","运行时出错啦，快去找处理，不然你想等他自己好吗？咱就不能好好使用接口，服了你！");
        return mv;
    }

    /**
     * 专门用来捕获和处理Controller层的异常
     * @param e 异常
     * @return ModelAndView
     */
    @ExceptionHandler(Exception.class)
    public ModelAndView exceptionHandler(Exception e){
        ModelAndView mv = new ModelAndView(new MappingJackson2JsonView());
        mv.addObject("data",null);
        mv.addObject("code",99999);
        mv.addObject("msg","好家伙哥伦布发现新大陆，你的操作发现新异常！在下佩服！");
        return mv;
    }
}
