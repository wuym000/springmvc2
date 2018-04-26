package com.wuym.model;

/**
 * Created by wuym on 2017/6/9.
 */
public class Page {
    private Long max;
    private int count;

    public Page(Long max, int count) {
        this.max = max;
        this.count = count;
    }

    public Page(){

    }
    public Long getMax() {
        return max;
    }

    public void setMax(Long max) {
        this.max = max;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
