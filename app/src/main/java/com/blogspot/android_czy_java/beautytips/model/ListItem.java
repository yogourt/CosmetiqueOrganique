package com.blogspot.android_czy_java.beautytips.model;

public class ListItem {
    private String mImage;
    private String mTitle;
    private String mId;
    private String mCategory;
    
    public ListItem() {}

    public ListItem(String image, String title, String category) {
        mImage = image;
        mTitle = title;
        mCategory = category;
    }

    public String getImage() {
        return mImage;
    }

    public void setImage(String mImage) {
        this.mImage = mImage;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getId() {
        return this.mId;
    }

    public void setId(String mId) {
        this.mId = mId;
    }

    public String getCategory() {
        return mCategory;
    }

    public void setCategory(String mCategory) {
        this.mCategory = mCategory;
    }
}
