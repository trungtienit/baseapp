package com.example.trantien.appreview.mvp;

import android.widget.ImageView;

/**
 * Created by QuocTuyen on 5/26/2018.
 */

public class NewsModel {
    private ImageView image;
    private String title;

    public NewsModel(ImageView image, String title) {
        this.image = image;
        this.title = title;
    }

    public ImageView getImage() {
        return image;
    }

    public void setImage(ImageView image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
