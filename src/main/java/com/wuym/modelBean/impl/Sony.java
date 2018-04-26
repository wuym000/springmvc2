package com.wuym.modelBean.impl;

import com.wuym.modelBean.Computer;
import com.wuym.modelBean.Hifi;

/**
 * Created by wuym on 2017/5/28.
 */
public class Sony implements Computer {
    private Hifi hifi;
    private String volum;
    private String title;

    public Sony(Hifi hifi, String volum, String title) {
        this.hifi = hifi;
        this.volum = volum;
        this.title = title;
    }

    @Override
    public void playSound() {
        System.out.println(title + "PC is playing music--->"+hifi.getTitle()+"vol="+volum);
        hifi.play();
        System.out.println("--play end--");
    }
}
