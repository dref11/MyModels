package com.zybooks.mmimages;

import android.graphics.Bitmap;

//Class used to populate the models view
public class DataObject {

    Long id;
    Bitmap image;
    String title;

    public DataObject(Long id, Bitmap image, String title) {
        this.id = id;
        this.image = image;
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public Bitmap getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }
}