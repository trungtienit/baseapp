package com.example.trantien.appreview.base.drawer.dto;

public class BodyDTO {
    public int id;
    public int icon;
    public String title;
    public boolean isSelected;

    public BodyDTO(int icon, String title, boolean isSelected) {
        this.icon = icon;
        this.title = title;
        this.isSelected = isSelected;
    }
}
