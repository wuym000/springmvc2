package com.wuym.modelBean.impl;

import com.wuym.modelBean.Computer;
import com.wuym.modelBean.USB;

import java.util.List;

/**
 * Created by wuym on 2017/5/28.
 */
public class DivPc implements Computer {
    private String volum;
    private String title;
    private USB usb;

    public DivPc(String volum, String title, USB usb) {
        this.volum = volum;
        this.title = title;
        this.usb = usb;
    }

    @Override
    public void playSound() {
        System.out.println(this.title + "PC is playing music--->"+"vol="+volum);
        List<String> musList = this.usb.getMusicList();
        for(String music:musList){
            System.out.println("track--" + music);
        }
        System.out.println("--play end--");
    }
}
