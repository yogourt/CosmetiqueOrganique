package com.blogspot.android_czy_java.beautytips.detail.model;

public class Comment {

    public String author;
    public String authorId;
    public String comment;
    public Boolean visible;

    public Comment(){}

    public Comment(String authorNickname, String authorId, String comment, Boolean visible) {
        this.author = authorNickname;
        this.authorId = authorId;
        this.comment = comment;
        this.visible = visible;
    }

    public String getAuthorNickname() {
        return author;
    }

    public void setAuthorNickname(String authorNickname) {
        this.author = authorNickname;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Boolean getVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }


}
