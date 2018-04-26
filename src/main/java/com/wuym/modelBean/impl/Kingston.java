package com.wuym.modelBean.impl;

import com.wuym.modelBean.USB;

import java.util.List;

/**
 * Created by wuym on 2017/5/28.
 */
public class Kingston implements USB{
    private String band;
    private List<String> musicList;

    public Kingston(String band, List<String> musicList) {
        this.band = band;
        this.musicList = musicList;
    }

    @Override
    public List getMusicList() {
        return this.musicList;
    }
}
