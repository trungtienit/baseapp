package com.example.trantien.appreview.base.drawer.dto;

public class HeaderDTO {
    public boolean isSelected;
    public String title;
    public String url;

    public HeaderDTO(String title,String url, boolean isSelected) {
        this.title = title;
        this.isSelected = isSelected;
        this.url=url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setName(String name) {
        this.title = name;
    }
}
