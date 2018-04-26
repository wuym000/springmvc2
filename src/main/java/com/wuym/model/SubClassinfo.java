package com.wuym.model;

import com.wuym.modelBean.Hifi;

public class SubClassinfo extends Classinfo implements Hifi {
    private String subField;
    private String subSubSubb1Subb;
    private int subIntField;

    public String getSubField() {
        return subField;
    }

    public void setSubField(String subField) {
        this.subField = subField;
    }

    @Override
    public void play() {

    }

    @Override
    public String getTitle() {
        return null;
    }

    public String getSubSubSubb1Subb() {
        return subSubSubb1Subb;
    }

    public void setSubSubSubb1Subb(String subSubSubb1Subb) {
        this.subSubSubb1Subb = subSubSubb1Subb;
    }

    public int getSubIntField() {
        return subIntField;
    }

    public void setSubIntField(int subIntField) {
        this.subIntField = subIntField;
    }
}
