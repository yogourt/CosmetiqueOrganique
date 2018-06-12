package com.blogspot.android_czy_java.beautytips.listView.model;

public class ListItem {
    private String mImage;
    private String mTitle;
    private String mId;
    private String mCategory;
    private String mAuthor;
    
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

    public String getAuthor() {
        return mAuthor;
    }

    public void setAuthor(String mAuthor) {
        this.mAuthor = mAuthor;
    }
}

