package com.blogspot.android_czy_java.beautytips.listView.model;

public class ListItem {
    public String image;
    public String title;
    public String subcategory;
    public String id;

    public ListItem(){}

    public String getImage() {
        return image;
    }

    public void setImage(String mImage) {
        this.image = mImage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String mTitle) {
        this.title = mTitle;
    }

    public String getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(String subcategory) {
        this.subcategory = subcategory;
    }


    public String getId() {
        return this.id;
    }

    public void setId(String mId) {
        this.id = mId;
    }

}
