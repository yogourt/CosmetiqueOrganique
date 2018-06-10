package com.blogspot.android_czy_java.beautytips.listView.firebase;

import android.support.annotation.NonNull;

import com.blogspot.android_czy_java.beautytips.listView.model.ListItem;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import javax.inject.Singleton;

import static com.blogspot.android_czy_java.beautytips.listView.view.MainActivityUtils.CATEGORY_ALL;


@Singleton
public class FirebaseHelper {

    public static FirebaseRecyclerOptions<ListItem> createFirebaseRecyclerOptions(String category) {
        return new FirebaseRecyclerOptions.Builder<ListItem>().
                setQuery(createQuery(category), new SnapshotParser<ListItem>() {
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

    private static Query createQuery(String category) {

        if(category.equals(CATEGORY_ALL)) {
            return FirebaseDatabase.getInstance().
                    getReference().
                    child("tipList");
        }

        return FirebaseDatabase.getInstance().
                getReference().
                child("tipList").
                orderByChild("category").equalTo(category);
    }
}
