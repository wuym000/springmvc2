package com.wuym.aspecj;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * Created by wuym on 2017/6/1.
 * 定义复用切点
 */
@Aspect
@Component
public class Plug {
    @Pointcut("execution(* com.wuym.modelBean.Computer.playSound(..))")
    public void plug(){}
    @Before("plug()")
    public void on(){
        System.out.println("cha chatou");
    }
    @After("plug()")
    public void off(){
        System.out.println("ba chatou");
    }
}
