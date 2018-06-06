package com.blogspot.android_czy_java.beautytips.listView;

import android.support.annotation.NonNull;

import com.blogspot.android_czy_java.beautytips.model.ListItem;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import javax.inject.Singleton;

@Singleton
class FirebaseHelper {

    static FirebaseRecyclerOptions<ListItem> createFirebaseRecyclerOptions() {
        return new FirebaseRecyclerOptions.Builder<ListItem>().
                setQuery(createQuery(), new SnapshotParser<ListItem>() {
                    @NonNull
                    @Override
                    public ListItem parseSnapshot(@NonNull DataSnapshot snapshot) {
                        ListItem item = snapshot.getValue(ListItem.class);
                        String id = snapshot.getKey();
                        if(item != null)
                        item.setId(id);
                        return item;
                    }
                }).
                build();
    }

    private static Query createQuery() {
        return FirebaseDatabase.getInstance().
                getReference().
                child("tipList");
    }
}
