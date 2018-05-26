package com.example.trantien.appreview.mvp.login.model;

public class User {
    String password;
    String email;
    String status;

    String id_fb;
    String phoneNumber;
    String address;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        return status;
    }


    public String getId_fb() {
        return id_fb;
    }

    public void setId_fb(String id_fb) {
        this.id_fb = id_fb;
    }

    public String getPassword() {
        return password;
    }


    public void setPassword(String password) {
        this.password = password;
    }


    public void setStatus(String status) {
        this.status = status;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
