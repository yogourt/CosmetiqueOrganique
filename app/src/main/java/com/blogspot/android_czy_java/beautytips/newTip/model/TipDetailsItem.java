package com.blogspot.android_czy_java.beautytips.newTip.model;

import android.support.annotation.Nullable;

public class TipDetailsItem {

    //keys in firebase db
    public String description;
    public String ingredient1;
    public String ingredient2;
    public String ingredient3;
    public String ingredient4;

    public TipDetailsItem(String description, String ingredient1,
                          String ingredient2, String ingredient3, String ingredient4) {
        this.description = description;
        this.ingredient1 = ingredient1;
        this.ingredient2 = ingredient2;
        this.ingredient3 = ingredient3;
        this.ingredient4 = ingredient4;
    }

    public TipDetailsItem(String description, String ingredient1, String ingredient2,
                          String ingredient3) {
        this.description = description;
        this.ingredient1 = ingredient1;
        this.ingredient2 = ingredient2;
        this.ingredient3 = ingredient3;
    }

    public TipDetailsItem(String description, String ingredient1, String ingredient2) {
        this.description = description;
        this.ingredient1 = ingredient1;
        this.ingredient2 = ingredient2;
    }

    public TipDetailsItem(String description, String ingredient1) {
        this.description = description;
        this.ingredient1 = ingredient1;
    }

}
