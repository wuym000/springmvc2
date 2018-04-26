package com.wuym.cache;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

/**
 * Created by wuym on 2017/6/29.
 */
@Component
public class CacheUtil {

    @Cacheable(value="apiticket", key="'ticket'")
    public String ticket(String apikey,String a, String b){
        System.out.println("jumpinto");
        return "testticketstr";
    }
}
