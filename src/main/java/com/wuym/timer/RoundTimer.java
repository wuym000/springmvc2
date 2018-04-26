package com.wuym.timer;

import com.wuym.cache.CacheUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * Created by wuym on 2017/7/5.
 */
@Service
public class RoundTimer {
    @Autowired
    private CacheUtil cacheUtil;
    //每50分轮询
    @Scheduled(cron = "0 0/50 * * * ? ")
    public void myTestWork() {
        String ticket = cacheUtil.ticket("", "", "");
        System.out.println("获得ticket------>" + ticket);
    }
}
