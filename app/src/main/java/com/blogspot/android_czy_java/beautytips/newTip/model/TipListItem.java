package com.blogspot.android_czy_java.beautytips.newTip.model;

import android.support.annotation.Keep;

@Keep
public class TipListItem {
    public String category;
    public String title;
    public String authorId;

    public TipListItem(String title, String category, String authorId) {
        this.category = category;
        this.title = title;
        this.authorId = authorId;
    }
}
