package com.blogspot.android_czy_java.beautytips.view.listView.firebase;

import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.blogspot.android_czy_java.beautytips.viewmodel.recipe.ListViewViewModel;
import com.blogspot.android_czy_java.beautytips.view.listView.model.ListItem;
import com.blogspot.android_czy_java.beautytips.view.listView.model.TipListItem;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import timber.log.Timber;

import static com.blogspot.android_czy_java.beautytips.viewmodel.recipe.ListViewViewModel.ORDER_POPULAR;
import static com.blogspot.android_czy_java.beautytips.viewmodel.recipe.ListViewViewModel.SUBCATEGORY_ALL;
import static com.blogspot.android_czy_java.beautytips.view.listView.view.MyDrawerLayoutListener.CATEGORY_ALL;
import static com.blogspot.android_czy_java.beautytips.view.listView.view.MyDrawerLayoutListener.CATEGORY_FAVOURITES;
import static com.blogspot.android_czy_java.beautytips.view.listView.view.MyDrawerLayoutListener.CATEGORY_INGREDIENTS;
import static com.blogspot.android_czy_java.beautytips.view.listView.view.MyDrawerLayoutListener.CATEGORY_YOUR_TIPS;


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
                        if (viewModel.getCategory().equals(CATEGORY_INGREDIENTS)) {
                            prepareIngredientList(dataSnapshot);
                        } else {
                            final List<ListItem> list = new ArrayList<>();
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                TipListItem item = snapshot.getValue(TipListItem.class);

                                if (item != null) {

                                    /*
                                    //check subcategory and if it's not the chosen, continue loop
                                    if (!activityViewModel.getSubcategory().equals(SUBCATEGORY_ALL)
                                            && !item.getSubcategory().equals(activityViewModel.getSubcategory())) {
                                        continue;
                                    }
                                    */

                                    //if image is not already added to this tip, do not show it
                                    if (TextUtils.isEmpty(item.getImage())) {
                                        continue;
                                    }

                                    String author = String.valueOf(snapshot.child("author").getValue());
                                    String id = snapshot.getKey();
                                    item.setId(id);
                                    if (!author.equals("null")) item.setAuthorId(author);

                                    if (!isUserNull() && !isUserAnonymous()
                                            && snapshot.child(FirebaseLoginHelper.getUserId()).getValue() != null) {
                                        item.setInFav(true);
                                    }
                                    list.add(item);
                                    //activityViewModel.setRecyclerViewList(list);
                                }
                            }

                            //sort the list if the order should be popular
                            //if (activityViewModel.getOrder().equals(ORDER_POPULAR)) {
                                Collections.sort(list, new Comparator<ListItem>() {
                                    @Override
                                    public int compare(ListItem o1, ListItem o2) {
                                        return (int) (((TipListItem) o1).getFavNum() -
                                                ((TipListItem) o2).getFavNum());
                                    }
                                });
                            }
                            //activityViewModel.setRecyclerViewList(list);
                       // }
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

                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                            ListItem item = snapshot.getValue(ListItem.class);

                            //check if this item should appear in search
                            String[] tagArray = String.valueOf(snapshot
                                    .child("tags").getValue()).split(" ");

                            int matches = 0;
                            for (String tag : tagArray) {
                                for (String query : queryArray) {
                                    if (tag.equals(query)) {
                                        matches++;
                                    }
                                }
                            }
                            //if no tag appeared in search, do not add this item, else continue adding
                            if (matches < queryArray.length || item == null) continue;

                            if(viewModel.getCategory().equals(CATEGORY_INGREDIENTS)) {
                                item.setMatches(matches);
                                String id = snapshot.getKey();
                                item.setId(id);
                                list.add(item);

                            } else {
                                TipListItem tipItem = snapshot.getValue(TipListItem.class);
                                if (tipItem == null) continue;
                                tipItem.setMatches(matches);
                                String author = String.valueOf(snapshot.child("author").getValue());
                                String id = snapshot.getKey();
                                tipItem.setId(id);
                                if (!author.equals("null")) tipItem.setAuthorId(author);

                                if (!isUserNull() && !isUserAnonymous()
                                        && snapshot.child(FirebaseLoginHelper.getUserId()).getValue() != null) {
                                    tipItem.setInFav(true);
                                }
                                list.add(tipItem);
                            }
                        }

                        //sort the list, so the items with higher number of matches will be first
                        Collections.sort(list, new Comparator<ListItem>() {
                            @Override
                            public int compare(ListItem o1, ListItem o2) {
                                return o2.getMatches() -
                                        o1.getMatches();
                            }
                        });
                        //activityViewModel.setRecyclerViewList(list);
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
            ListItem item = snapshot.getValue(ListItem.class);

            /*
            if (!activityViewModel.getSubcategory().equals(SUBCATEGORY_ALL)
                    && !item.getSubcategory().equals(activityViewModel.getSubcategory())) {
                //item discarded
                continue;
            }
            */

            //item accepted
            String id = snapshot.getKey();
            if (item != null) {
                item.setId(id);
            }
            list.add(item);
        }
        //activityViewModel.setRecyclerViewList(list);
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

        if (category.equals(CATEGORY_INGREDIENTS)) {
            return FirebaseDatabase.getInstance()
                    .getReference()
                    .child("ingredientList");
        }

        return FirebaseDatabase.getInstance().
                getReference().
                child("tipList").
                orderByChild("category").equalTo(category);
    }


    public void deleteTipWithId(Long id) {
        FirebaseDatabase.getInstance().getReference().child("tipList/" + id).removeValue();
        FirebaseDatabase.getInstance().getReference().child("tips/" + id).removeValue();
        FirebaseStorage.getInstance().getReference(id.toString()).delete();

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

    public static boolean isUserNull() {
        return FirebaseAuth.getInstance().getCurrentUser() == null;
    }


    public static boolean isUserAnonymous() {
        return FirebaseAuth.getInstance().getCurrentUser().isAnonymous();
    }
}
