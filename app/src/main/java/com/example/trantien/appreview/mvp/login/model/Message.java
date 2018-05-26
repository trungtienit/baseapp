package com.example.trantien.appreview.mvp.login.model;

public class Message {
    String userid;
    String mlat;
    String mlong;

    public Message(String muserid, String mlat, String mlong) {
        this.userid = muserid;
        this.mlat = mlat;
        this.mlong = mlong;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        userid = userid;
    }

    public String getMlat() {
        return mlat;
    }

    public void setMlat(String mlat) {
        this.mlat = mlat;
    }

    public String getMlong() {
        return mlong;
    }

    public void setMlong(String mlong) {
        this.mlong = mlong;
    }

    public Message() {
    }
}
