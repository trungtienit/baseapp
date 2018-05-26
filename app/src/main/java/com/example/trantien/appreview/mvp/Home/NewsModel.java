package com.example.trantien.appreview.mvp.Home;

import android.widget.ImageView;

/**
 * Created by QuocTuyen on 5/26/2018.
 */

public class NewsModel {
    private String image;
    private String title;
    private String url;

    public NewsModel(String image, String title, String url) {
        this.image = image;
        this.title = title;
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
