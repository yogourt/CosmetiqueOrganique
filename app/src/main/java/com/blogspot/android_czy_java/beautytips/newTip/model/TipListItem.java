package com.blogspot.android_czy_java.beautytips.newTip.model;

public class TipListItem {
    public String category;
    public String title;
    public String author;
    public long timestamp;

    public TipListItem(String title, String category, String author, long timestamp) {
        this.category = category;
        this.title = title;
        this.author = author;
        this.timestamp = timestamp;
    }
}
