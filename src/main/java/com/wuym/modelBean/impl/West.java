package com.wuym.modelBean.impl;

import com.wuym.modelBean.USB;

import java.util.List;

/**
 * Created by wuym on 2017/5/28.
 */
public class West implements USB {
    private String band;
    private List<String> musicList;
    @Override
    public List getMusicList() {
        return this.musicList;
    }

    public String getBand() {
        return band;
    }

    public void setBand(String band) {
        this.band = band;
    }

    public void setMusicList(List<String> musicList) {
        this.musicList = musicList;
    }
}
