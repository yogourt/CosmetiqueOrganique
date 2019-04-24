package com.blogspot.android_czy_java.beautytips.listView.model;

import java.io.Serializable;

public class ListItem  implements Serializable {

    private static final long serialVersionUID = 0L;

    public String id;
    public String image;
    public String title;
    public String subcategory;
    public int matches;

    public ListItem(){}

    public ListItem(String image, String title, String subcategory) {
        this.image = image;
        this.title = title;
        this.subcategory = subcategory;
    }

    public ListItem(String image, String title, String subcategory, String id, int matches) {
        this.image = image;
        this.title = title;
        this.id = id;

        //this is needed for serialization purposes
        this.subcategory = subcategory;
        this.matches = matches;
    }

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

    public void setMatches(int matches) {
        this.matches = matches;
    }

    public int getMatches() {
        return  matches;
    }

}
