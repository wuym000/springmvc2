package com.wuym.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by wuym on 2017/6/12.
 * 控制器通知统一处理异常
 */
@ControllerAdvice
public class AppWideExceptionHandler {

    @ExceptionHandler(NumberFormatException.class)
    public ModelAndView nfExceptionHandler(){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("error/numberFormat");

        return mav;
    }
}
