package com.wuym.modelBean.impl;

import com.wuym.modelBean.Hifi;

/**
 * Created by wuym on 2017/5/28.
 */
public class Edifier implements Hifi {
    private String title = "Edifier-R208PF";
    @Override
    public void play() {
        System.out.println("playingsong---");
    }

    @Override
    public String getTitle() {
        return this.title;
    }
}
