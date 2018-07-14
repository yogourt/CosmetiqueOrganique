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
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import timber.log.Timber;

import static com.blogspot.android_czy_java.beautytips.listView.ListViewViewModel.ORDER_POPULAR;
import static com.blogspot.android_czy_java.beautytips.listView.view.MyDrawerLayoutListener.CATEGORY_ALL;
import static com.blogspot.android_czy_java.beautytips.listView.view.MyDrawerLayoutListener.CATEGORY_FAVOURITES;
import static com.blogspot.android_czy_java.beautytips.listView.view.MyDrawerLayoutListener.CATEGORY_INGREDIENTS;
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

                        /*
                          if user wants to see ingredients, the helper function is called, because
                          the logic to get data from db is different
                         */
                        if(viewModel.getCategory().equals(CATEGORY_INGREDIENTS)) {
                            prepareIngredientList(dataSnapshot);
                        }
                        else {
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

                            //sort the list if the order should be popular
                            if(viewModel.getOrder().equals(ORDER_POPULAR)) {
                                Collections.sort(list, new Comparator<ListItem>() {
                                    @Override
                                    public int compare(ListItem o1, ListItem o2) {
                                        return (int) (o1.getFavNum() - o2.getFavNum());
                                    }
                                });
                            }
                            viewModel.setRecyclerViewList(list);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }


    public void searchAndSetListToViewModel(final String query) {
        createQuery(viewModel.getCategory())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        final List<ListItem> list = new ArrayList<>();
                        String[] queryArray = query.split(" ");
                        Timber.d(queryArray[0]);
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            ListItem item = snapshot.getValue(ListItem.class);

                            //check if this item should appear in search
                            String[] tagArray = String.valueOf(snapshot
                                    .child("tags").getValue()).split(" ");

                            int matches = 0;
                            for(String tag: tagArray) {
                                for(String query: queryArray) {
                                    if(tag.equals(query)) {
                                        matches++;
                                    }
                                }
                            }
                            //if no tag appeared in search, do not add this item, else continue adding
                            if(matches == 0) continue;

                            String author = String.valueOf(snapshot.child("author").getValue());
                            String id = snapshot.getKey();
                            if (item != null) {
                                item.setId(id);
                                if (!author.equals("null")) item.setAuthorId(author);

                                if (!FirebaseLoginHelper.isUserNull() && !FirebaseLoginHelper.isUserAnonymous()
                                        && snapshot.child(FirebaseLoginHelper.getUserId()).getValue() != null) {
                                    item.setInFav(true);
                                }
                                item.setMatches(matches);
                                list.add(item);
                            }
                        }

                        //sort the list, so the items with higher number of matches will be first
                        Collections.sort(list, new Comparator<ListItem>() {
                            @Override
                            public int compare(ListItem o1, ListItem o2) {
                                return o2.getMatches() - o1.getMatches();
                            }
                        });
                        viewModel.setRecyclerViewList(list);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Timber.d(databaseError.getMessage());
                    }
                });
    }

    private void prepareIngredientList(DataSnapshot dataSnapshot) {
        final List<ListItem> list = new ArrayList<>();
        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

        }
        viewModel.setRecyclerViewList(list);
    }

    /*
        Helper method to create query that is used in two methods above
     */
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

        if(category.equals(CATEGORY_INGREDIENTS)) {
            return FirebaseDatabase.getInstance()
                    .getReference()
                    .child("ingredients");
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
