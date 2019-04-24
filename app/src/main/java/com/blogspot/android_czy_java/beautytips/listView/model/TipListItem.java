package com.blogspot.android_czy_java.beautytips.listView.model;

import android.support.annotation.Keep;

import java.io.Serializable;

@Keep
public class TipListItem extends ListItem implements Serializable{

    private static final long serialVersionUID = 1L;

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

    public TipListItem(String image, String title, String subcategory, String id, int matches,
                       String category, String authorId, long favNum, boolean inFav) {
        super(image, title, subcategory, id, matches);
        this.category = category;
        this.authorId = authorId;
        this.favNum = favNum;
        this.inFav = inFav;
    }

    public TipListItem(String id, String authorId, String title, int favNum) {
        this.id = id;
        this.authorId = authorId;
        this.title = title;
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

