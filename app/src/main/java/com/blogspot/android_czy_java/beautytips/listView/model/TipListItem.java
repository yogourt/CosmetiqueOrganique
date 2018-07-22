package com.blogspot.android_czy_java.beautytips.listView.model;

import android.support.annotation.Keep;

@Keep
public class TipListItem extends ListItem{
    public String category;
    public String authorId;
    public long favNum;
    public boolean inFav;
    
    public TipListItem() {}

    public TipListItem(String image, String title, String category, long favNum) {
        this.image = image;
        this.title = title;
        this.category = category;
        this.favNum = favNum;
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

}

