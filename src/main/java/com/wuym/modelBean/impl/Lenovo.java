package com.wuym.modelBean.impl;

import com.wuym.modelBean.Computer;
import com.wuym.modelBean.Hifi;
import com.wuym.modelBean.USB;

import java.util.List;

/**
 * Created by wuym on 2017/5/28.
 */
public class Lenovo implements Computer {
    private String volum;
    private String title;
    private USB usb;
    private Hifi hifi;
    @Override
    public void playSound() {
        System.out.println(this.title + "PC is playing music--->"+"vol="+volum);
        List<String> musList = this.usb.getMusicList();
        for(String music:musList){
            System.out.println("track--" + music);
        }
        System.out.println("--play end--");
    }

    public String getVolum() {
        return volum;
    }

    public void setVolum(String volum) {
        this.volum = volum;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public USB getUsb() {
        return usb;
    }

    public void setUsb(USB usb) {
        this.usb = usb;
    }

    public Hifi getHifi() {
        return hifi;
    }

    public void setHifi(Hifi hifi) {
        this.hifi = hifi;
    }
}
