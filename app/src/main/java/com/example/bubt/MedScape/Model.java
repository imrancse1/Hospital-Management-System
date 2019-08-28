package com.example.bubt.MedScape;

public class Model {

    String title;
    String desc;
    String Phone;
    int icon;

    public String getTitle() {
        return this.title;
    }

    public String getDesc() {
        return this.desc;
    }

    public String getPhone() { return this.Phone; }

    public int getIcon() {
        return this.icon;
    }


    public Model(String title, String desc, int icon, String phone) {

        this.title = title;
        this.desc = desc;
        this.icon = icon;
        this.Phone =phone;

    }
}
