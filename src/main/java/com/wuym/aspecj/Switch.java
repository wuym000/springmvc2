package com.wuym.aspecj;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

/**
 * Created by wuym on 2017/5/31.
 */
@Aspect
@Component
public class Switch {
    @Before("execution(* com.wuym.modelBean.Computer.playSound(..))")
    public void on(){
        System.out.println("turn on the lignhts");
    }
    @After("execution(* com.wuym.modelBean.Computer.playSound(..))")
    public void off(){
        System.out.println("turn off the lignhts");
    }
}
