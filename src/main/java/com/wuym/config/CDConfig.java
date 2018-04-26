package com.wuym.config;

import com.wuym.modelBean.impl.Mylove;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by wuym on 2017/5/26.
 */
@Configuration
public class CDConfig {
    @Bean
    public Mylove mylove(){
        return new Mylove();
    }
}
