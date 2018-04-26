package com.wuym.modelBean.impl;

import com.wuym.modelBean.CompactDisc;
import com.wuym.modelBean.MediaPlayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by wuym on 2017/5/26.
 */
@Component
public class CDplayer implements MediaPlayer {

    private CompactDisc cd;
    @Autowired
    public CDplayer(SgtPeppers cd){
        this.cd = cd;
    }

    public void setCd(SgtPeppers cd) {
        this.cd = cd;
    }

    public void play() {
        System.out.println("playing..." + cd.getTitle());
    }
}
