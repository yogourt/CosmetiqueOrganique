package com.blogspot.android_czy_java.beautytips.newTip.model;

public class TipListItem {
    public String category;
    public String title;
    public String authorId;
    public long timestamp;

    public TipListItem(String title, String category, String authorId, long timestamp) {
        this.category = category;
        this.title = title;
        this.authorId = authorId;
        this.timestamp = timestamp;
    }
}
