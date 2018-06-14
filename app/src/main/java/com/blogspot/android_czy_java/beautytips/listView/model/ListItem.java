package com.blogspot.android_czy_java.beautytips.listView.model;

public class ListItem {
    private String image;
    private String title;
    private String tipId;
    private String category;
    private String authorId;
    
    public ListItem() {}

    public ListItem(String image, String title, String category) {
        this.image = image;
        this.title = title;
        this.category = category;
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

    public String getId() {
        return this.tipId;
    }

    public void setId(String mId) {
        this.tipId = mId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String mCategory) {
        this.category = mCategory;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String mAuthor) {
        this.authorId = mAuthor;
    }
}

