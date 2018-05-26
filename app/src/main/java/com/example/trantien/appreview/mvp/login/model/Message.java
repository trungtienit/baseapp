package com.example.trantien.appreview.mvp.login.model;

public class Message {
    String Userid;
    String mlat;
    String mlong;

    public Message(String userid, String mlat, String mlong) {
        Userid = userid;
        this.mlat = mlat;
        this.mlong = mlong;
    }

}
