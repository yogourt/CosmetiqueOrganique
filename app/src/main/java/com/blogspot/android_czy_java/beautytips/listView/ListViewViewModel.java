package com.blogspot.android_czy_java.beautytips.listView;

import android.arch.lifecycle.ViewModel;

import com.blogspot.android_czy_java.beautytips.model.ListItem;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import javax.inject.Inject;

public class ListViewViewModel extends ViewModel {

    private FirebaseRecyclerOptions<ListItem> options;

    public ListViewViewModel() {}

    void init() {
        if(options != null) {
            return;
        }
        options = ListViewRepository.createFirebaseRecyclerOptions();
    }
    FirebaseRecyclerOptions<ListItem> getOptions() {
        return options;
    }
}
