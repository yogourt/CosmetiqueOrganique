package com.blogspot.android_czy_java.beautytips.listView.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.blogspot.android_czy_java.beautytips.listView.firebase.FirebaseHelper;
import com.blogspot.android_czy_java.beautytips.listView.model.ListItem;
import com.blogspot.android_czy_java.beautytips.listView.model.User;

import java.util.List;

import timber.log.Timber;

import static com.blogspot.android_czy_java.beautytips.listView.model.User.USER_STATE_ANONYMOUS;
import static com.blogspot.android_czy_java.beautytips.listView.model.User.USER_STATE_LOGGED_IN;
import static com.blogspot.android_czy_java.beautytips.listView.model.User.USER_STATE_NULL;
import static com.blogspot.android_czy_java.beautytips.listView.view.MyDrawerLayoutListener.CATEGORY_ALL;
import static com.blogspot.android_czy_java.beautytips.listView.view.MyDrawerLayoutListener.NAV_POSITION_ALL;

public class ListViewViewModel extends ViewModel {

    public static final String ORDER_POPULAR = "popular";
    public static final String ORDER_NEW = "new";

    public static final String SUBCATEGORY_ALL = "all";

    public int detailScreenOpenTimesAfterPromptDialog = 0;

    /*
     CategoryLabel and navigationPosition are used in Navigation Drawer: navigationPosition is used
     to check selected item (it's rose), categoryLiveData is used when creating firebase query
   */
    private int navigationPosition;

    private MutableLiveData<String> categoryLiveData;

    private MutableLiveData<String> userStateLiveData;
    private MutableLiveData<User> userLiveData;

    private MutableLiveData<List<ListItem>> recyclerViewLiveData;

    private FirebaseHelper mFirebaseHelper;

    private boolean searchWasConducted;

    private String listOrder;

    private String subcategory;

    private String query;

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
            if(FirebaseHelper.isUserNull()) userStateLiveData.setValue(USER_STATE_NULL);
            else if(FirebaseHelper.isUserAnonymous()) userStateLiveData.setValue(USER_STATE_ANONYMOUS);
            else userStateLiveData.setValue(USER_STATE_LOGGED_IN);

            recyclerViewLiveData = new MutableLiveData<>();
            notifyRecyclerDataHasChanged();

            searchWasConducted = false;

            listOrder = ORDER_NEW;
            subcategory = SUBCATEGORY_ALL;
            query = "";
        }
    }

    public int getNavigationPosition() {
        return navigationPosition;
    }

    public void setNavigationPosition(int position) {
        navigationPosition = position;
    }

    public void setCategory(String category) {
        boolean categoryIsTheSame = categoryLiveData.getValue().equals(category);

        this.categoryLiveData.setValue(category);
        if(!categoryIsTheSame) {
            setSubcategory(SUBCATEGORY_ALL);
            notifyRecyclerDataHasChanged();
        }

        //even though user chosen the same category, he wants all the data to be loaded
        if(searchWasConducted) {
            resetSearch();
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

    public LiveData<List<ListItem>> getRecyclerViewLiveData() {
        return recyclerViewLiveData;
    }

    public void setRecyclerViewList(List<ListItem> list) {
        recyclerViewLiveData.setValue(list);
    }

    public LiveData<User> getUserLiveData() {
        return userLiveData;
    }

    public void setUserLiveData(User user) {
        userLiveData.setValue(user);
    }

    public void deleteTipWithId(Long id) {
        mFirebaseHelper.deleteTipWithId(id);
        //requery database, because data has changed
        notifyRecyclerDataHasChanged();
    }

    /*
      Method called whenever the list data has to be loaded again. All the logic is in mFirebaseHelper
     */
    public void notifyRecyclerDataHasChanged() {
        mFirebaseHelper.setItemListToViewModel();
    }

    public void waitForAddingImage(String tipNum) {
        mFirebaseHelper.waitForAddingImage(tipNum);
    }

    //implementation of search tip list in searchView
    public void search(String query) {
        if(query == null) return;
        query = query.toLowerCase();
        this.query = query;
        subcategory = SUBCATEGORY_ALL;
        searchWasConducted = true;
        mFirebaseHelper.searchAndSetListToViewModel(query);
    }

    public boolean getSearchWasConducted() {
        return searchWasConducted;
    }

    public void resetSearch() {
        searchWasConducted = false;
        notifyRecyclerDataHasChanged();
    }
    public void setOrder(String order) {
        listOrder = order;
            notifyRecyclerDataHasChanged();
    }

    public String getOrder() {
        return listOrder;
    }

    public String getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(String subcategory) {
        if(subcategory == null) return;
        subcategory = subcategory.toLowerCase();
        if(!this.subcategory.equals(subcategory)) {
            this.subcategory = subcategory;
            notifyRecyclerDataHasChanged();
        }
    }

    public String getQuery() {
        return query;
    }

    public boolean shouldSupportDialogBeShown() {
        return detailScreenOpenTimesAfterPromptDialog > 2;
    }
 }
