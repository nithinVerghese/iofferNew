package com.accentrs.iofferbh.model.drawer;


import java.io.Serializable;

public class DrawerModel implements Serializable {

    private String title;

    public DrawerModel() {
    }

    public DrawerModel(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
