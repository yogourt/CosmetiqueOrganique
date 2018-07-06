package com.blogspot.android_czy_java.beautytips.listView.firebase;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.blogspot.android_czy_java.beautytips.listView.ListViewViewModel;
import com.blogspot.android_czy_java.beautytips.listView.model.ListItem;
import com.blogspot.android_czy_java.beautytips.listView.view.ListViewAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

import static com.blogspot.android_czy_java.beautytips.listView.view.MyDrawerLayoutListener.CATEGORY_ALL;
import static com.blogspot.android_czy_java.beautytips.listView.view.MyDrawerLayoutListener.CATEGORY_FAVOURITES;
import static com.blogspot.android_czy_java.beautytips.listView.view.MyDrawerLayoutListener.CATEGORY_YOUR_TIPS;


public class FirebaseHelper {

    private ListViewViewModel viewModel;

    public FirebaseHelper(ListViewViewModel viewModel) {
        this.viewModel = viewModel;
    }

    public void setItemListToViewModel() {
        createQuery(viewModel.getCategory())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        final List<ListItem> list = new ArrayList<>();
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            ListItem item = snapshot.getValue(ListItem.class);
                            String author = String.valueOf(snapshot.child("author").getValue());
                            String id = snapshot.getKey();
                            if (item != null) {
                                item.setId(id);
                                if (!author.equals("null")) item.setAuthorId(author);

                                if (!FirebaseLoginHelper.isUserNull() && !FirebaseLoginHelper.isUserAnonymous()
                                        && snapshot.child(FirebaseLoginHelper.getUserId()).getValue() != null) {
                                    item.setInFav(true);
                                }
                                list.add(item);
                            }
                        }
                        viewModel.setRecyclerViewList(list);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
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
                        .orderByChild("authorId").equalTo(user.getUid());
        }

        if (category.equals(CATEGORY_FAVOURITES)) {
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

    public void deleteTipWithId(String id) {
        FirebaseDatabase.getInstance().getReference().child("tipList/" + id).removeValue();
        FirebaseDatabase.getInstance().getReference().child("tips/" + id).removeValue();
        FirebaseStorage.getInstance().getReference(id).delete();

        //refresh data in recycler view
        setItemListToViewModel();
    }

    //this is done so the data is fetched again after image for a new Tip is added
    public void waitForAddingImage(final String tipId) {
        FirebaseDatabase.getInstance().getReference("tipList/" + tipId).addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.child("image").getValue() != null) {
                            viewModel.notifyRecyclerDataHasChanged();
                            FirebaseDatabase.getInstance().getReference("tipList/" + tipId)
                                    .removeEventListener(this);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Timber.d(databaseError.getMessage());
                    }
                });
    }
}
