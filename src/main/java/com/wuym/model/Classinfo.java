package com.wuym.model;

import com.wuym.modelBean.USB;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wuym on 2017/6/14.
 */
public class Classinfo implements USB {
    private int id;
    private int grand;
    private int classie;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGrand() {
        return grand;
    }

    public void setGrand(int grand) {
        this.grand = grand;
    }

    public int getClassie() {
        return classie;
    }

    public void setClassie(int classie) {
        this.classie = classie;
    }

    public boolean isClass(){

        return false;
    }

    @Override
    public List getMusicList() {
        ArrayList list = new ArrayList();
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        return list;
    }
}
