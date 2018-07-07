package com.blogspot.android_czy_java.beautytips.newTip.model;

import android.support.annotation.Keep;

@Keep
public class TipListItem {
    public String category;
    public String title;
    public String authorId;
    public String tags;

    public TipListItem(String title, String category, String authorId, String tags) {
        this.category = category;
        this.title = title;
        this.authorId = authorId;
        this.tags = tags;
    }
}
