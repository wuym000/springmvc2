package com.wuym.modelBean.impl;

import com.wuym.modelBean.CompactDisc;
import org.springframework.stereotype.Component;

/**
 * Created by wuym on 2017/5/26.
 */
@Component("lonelyHeartsClub")
public class SgtPeppers implements CompactDisc {
    private String title = "SGTPEPPERS";
    private String artist = "The Beatles";
    public void play() {
        System.out.println("playing" + title  + "," + artist);
    }

    @Override
    public String getTitle() {
        return title;
    }


    public String getArtist() {
        return artist;
    }
}
