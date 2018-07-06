package com.blogspot.android_czy_java.beautytips.listView;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.blogspot.android_czy_java.beautytips.listView.firebase.FirebaseHelper;
import com.blogspot.android_czy_java.beautytips.listView.firebase.FirebaseLoginHelper;
import com.blogspot.android_czy_java.beautytips.listView.model.ListItem;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

import static com.blogspot.android_czy_java.beautytips.listView.view.MyDrawerLayoutListener.CATEGORY_ALL;
import static com.blogspot.android_czy_java.beautytips.listView.view.MyDrawerLayoutListener.NAV_POSITION_ALL;

public class ListViewViewModel extends ViewModel {

    public static final String USER_STATE_LOGGED_IN = "logged_in";
    public static final String USER_STATE_ANONYMOUS = "anonymous";
    public static final String USER_STATE_NULL = "null";

    /*
     Category and navigationPosition are used in Navigation Drawer: navigationPosition is used
     to check selected item (it's rose), categoryLiveData is used when creating firebase query
   */
    private int navigationPosition;

    private MutableLiveData<String> categoryLiveData;

    private MutableLiveData<String> userStateLiveData;

    private int[] into;

    private MutableLiveData<Boolean> searchLiveData;

    private MutableLiveData<List<ListItem>> recyclerViewLiveData;

    private FirebaseHelper mFirebaseHelper;

    public void init() {
        if(categoryLiveData == null) {
            mFirebaseHelper = new FirebaseHelper(this);
            categoryLiveData = new MutableLiveData<>();
            categoryLiveData.setValue(CATEGORY_ALL);
            Timber.d("init() - categoryLiveData initiation");
            //set navigation position to "All"
            navigationPosition = NAV_POSITION_ALL;

            //set value of userStateLiveData. This will be observed in Main Activity to prepare
            // navigation drawer header.
            userStateLiveData = new MutableLiveData<>();
            if(FirebaseLoginHelper.isUserNull()) userStateLiveData.setValue(USER_STATE_NULL);
            else if(FirebaseLoginHelper.isUserAnonymous()) userStateLiveData.setValue(USER_STATE_ANONYMOUS);
            else userStateLiveData.setValue(USER_STATE_LOGGED_IN);

            //set search visibility value
            searchLiveData = new MutableLiveData<>();
            searchLiveData.setValue(false);

            recyclerViewLiveData = new MutableLiveData<>();
            notifyRecyclerDataHasChanged();
        }
    }

    public int getNavigationPosition() {
        return navigationPosition;
    }

    public void setNavigationPosition(int position) {
        navigationPosition = position;
    }

    public void setCategory(String category) {
        Timber.d("setCategory: " + category);
        boolean categoryIsTheSame = categoryLiveData.getValue().equals(category);

        this.categoryLiveData.setValue(category);
        if(!categoryIsTheSame) {
            into = null;
            notifyRecyclerDataHasChanged();
        }
    }

    public LiveData<String> getCategoryLiveData() {
        return categoryLiveData;
    }

    public String getCategory() {
        return categoryLiveData.getValue();
    }

    public LiveData<String> getUserStateLiveData() {
        return userStateLiveData;
    }

    public void changeUserState(String newState) {
        userStateLiveData.setValue(newState);
    }

    public int[] getInto() {
        return into;
    }

    public void setInto(int[] into) {
        this.into = into;
    }

    public void setIsSearchVisible(boolean isSearchVisible) {
        searchLiveData.setValue(isSearchVisible);
    }

    public LiveData<Boolean> getSearchLiveData() {
        return searchLiveData;
    }

    public LiveData<List<ListItem>> getRecyclerViewLiveData() {
        return recyclerViewLiveData;
    }

    public void setRecyclerViewList(List<ListItem> list) {
        recyclerViewLiveData.setValue(list);
    }

    public void deleteTipWithId(String id) {
        mFirebaseHelper.deleteTipWithId(id);
        //requery database, because data has changed
        notifyRecyclerDataHasChanged();
    }

    public void notifyRecyclerDataHasChanged() {
        mFirebaseHelper.setItemListToViewModel();
    }

    public void waitForAddingImage(String tipNum) {
        mFirebaseHelper.waitForAddingImage(tipNum);
    }
}
