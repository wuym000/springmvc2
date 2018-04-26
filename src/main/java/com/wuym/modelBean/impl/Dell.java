package com.wuym.modelBean.impl;

import com.wuym.modelBean.Computer;
import com.wuym.modelBean.Hifi;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by wuym on 2017/5/28.
 */
public class Dell implements Computer {
    private Hifi hifi;
    private String volum;

    public Dell(Hifi hifi, String volum) {
        this.hifi = hifi;
        this.volum = volum;
    }

    @Override
    public void playSound() {
        System.out.println("PC is playing music--->"+hifi.getTitle()+"vol="+volum);
        hifi.play();
        System.out.println("--play end--");
    }
}
