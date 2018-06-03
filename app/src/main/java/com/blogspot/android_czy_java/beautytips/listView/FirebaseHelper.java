package com.blogspot.android_czy_java.beautytips.listView;

import com.blogspot.android_czy_java.beautytips.model.ListItem;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;


class FirebaseHelper {

    static FirebaseRecyclerOptions<ListItem> createFirebaseRecyclerOptions() {
        return new FirebaseRecyclerOptions.Builder<ListItem>()
                .setQuery(createQuery(), ListItem.class)
                .build();
    }

    private static Query createQuery() {
        return FirebaseDatabase.getInstance().
                getReference().
                child("tipList");
    }
}
