package com.blogspot.android_czy_java.beautytips.listView.firebase;

import android.support.annotation.NonNull;

import com.blogspot.android_czy_java.beautytips.listView.model.ListItem;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import javax.inject.Singleton;

import static com.blogspot.android_czy_java.beautytips.listView.view.MainActivityUtils.CATEGORY_ALL;
import static com.blogspot.android_czy_java.beautytips.listView.view.MainActivityUtils.CATEGORY_FAVOURITES;
import static com.blogspot.android_czy_java.beautytips.listView.view.MainActivityUtils.CATEGORY_YOUR_TIPS;


@Singleton
public class FirebaseHelper {

    public static FirebaseRecyclerOptions<ListItem> createFirebaseRecyclerOptions(String category) {
        return new FirebaseRecyclerOptions.Builder<ListItem>().
                setQuery(createQuery(category), new SnapshotParser<ListItem>() {
                    @NonNull
                    @Override
                    public ListItem parseSnapshot(@NonNull DataSnapshot snapshot) {
                        ListItem item = snapshot.getValue(ListItem.class);
                        String author = String.valueOf(snapshot.child("author").getValue());
                        String id = snapshot.getKey();
                        if (item != null) {
                            item.setId(id);
                            if (!author.equals("null")) item.setAuthor(author);
                        }
                        return item;
                    }
                }).
                build();
    }

    private static Query createQuery(String category) {

        if (category.equals(CATEGORY_ALL)) {
            return FirebaseDatabase.getInstance().
                    getReference().
                    child("tipList");
        }

        if (category.equals(CATEGORY_YOUR_TIPS)) {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (user != null)
                return FirebaseDatabase.getInstance()
                        .getReference()
                        .child("tipList")
                        .orderByChild("author").equalTo(user.getDisplayName());
        }

        if(category.equals(CATEGORY_FAVOURITES)) {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (user != null)
                return FirebaseDatabase.getInstance()
                        .getReference()
                        .child("tipList")
                        .orderByChild(user.getUid()).equalTo(true);
        }

        return FirebaseDatabase.getInstance().
                getReference().
                child("tipList").
                orderByChild("category").equalTo(category);
    }
}
