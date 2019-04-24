package com.blogspot.android_czy_java.beautytips.detail.model;

public class Comment {

    public String a;
    public String b;
    public String c;
    public boolean d;

    public Comment(){}

    public Comment(String authorNickname, String authorId, String comment, boolean visible) {
        this.a = authorNickname;
        this.b = authorId;
        this.c = comment;
        this.d = visible;
    }

    public String getAuthorNickname() {
        return a;
    }

    public void setAuthorNickname(String authorNickname) {
        this.a = authorNickname;
    }

    public String getComment() {
        return c;
    }

    public void setComment(String comment) {
        this.c = comment;
    }

    public Boolean getVisible() {
        return d;
    }

    public void setVisible(Boolean visible) {
        this.d = visible;
    }


}
