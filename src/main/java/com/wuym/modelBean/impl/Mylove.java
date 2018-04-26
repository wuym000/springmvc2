package com.wuym.modelBean.impl;

import com.wuym.modelBean.CompactDisc;

/**
 * Created by wuym on 2017/5/26.
 */
public class Mylove implements CompactDisc {
    private String title = "EmtpyStreet";
    private String artist = "WestLife";
    @Override
    public void play() {
        System.out.println("playing" + title  + "," + artist);
    }

    @Override
    public String getTitle() {
        return title;
    }
}
