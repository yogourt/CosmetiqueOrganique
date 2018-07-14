package com.blogspot.android_czy_java.beautytips.listView.model;

import android.support.annotation.Keep;

@Keep
public class ListItem {
    public String image;
    public String title;
    public String tipId;
    public String category;
    public String subcategory;
    public String authorId;
    public long favNum;
    public boolean inFav;
    public int matches;
    
    public ListItem() {}

    public ListItem(String image, String title, String category, long favNum) {
        this.image = image;
        this.title = title;
        this.category = category;
        this.favNum = favNum;
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

    public String getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(String subcategory) {
        this.subcategory = subcategory;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String mAuthor) {
        this.authorId = mAuthor;
    }

    public long getFavNum() {
        return favNum;
    }

    public void setFavNum(long favNum) {
        this.favNum = favNum;
    }

    public boolean getInFav() {
        return inFav;
    }

    public void setInFav(boolean inFav) {
        this.inFav = inFav;
    }

    public void setMatches(int matches) {
        this.matches = matches;
    }

    public int getMatches() {
        return  matches;
    }

}

