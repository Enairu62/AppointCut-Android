package com.example.appointcut;

public class DataModel {

    private String hairTitle, hairDesc;
    private int hairImage;

    public DataModel(int image, String title, String desc) {
        this.hairImage = image;
        this.hairTitle = title;
        this.hairDesc = desc;

    }

    public String getTitle() {
        return hairTitle;
    }

    public void setTitle(String title) {
        this.hairTitle = title;
    }

    public String getDesc() {
        return hairDesc;
    }

    public void setDesc(String desc) {
        this.hairDesc = desc;
    }

    public int getImage() {
        return hairImage;
    }

    public void setImage(int image) {
        this.hairImage = image;
    }
}
