package com.blogspot.android_czy_java.beautytips.listView.view;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.blogspot.android_czy_java.beautytips.listView.ListViewViewModel;
import com.blogspot.android_czy_java.beautytips.listView.model.ListItem;
import com.blogspot.android_czy_java.beautytips.listView.model.TipListItem;

import static com.blogspot.android_czy_java.beautytips.listView.view.MainActivity.TAG_FRAGMENT_DETAIL;
import static com.blogspot.android_czy_java.beautytips.listView.view.MainActivity.TAG_FRAGMENT_INGREDIENT;
import static com.blogspot.android_czy_java.beautytips.listView.view.MainActivity.TAG_FRAGMENT_OPENING;
import static com.blogspot.android_czy_java.beautytips.listView.view.MyDrawerLayoutListener.CATEGORY_INGREDIENTS;


public class TabletListViewViewModel extends ListViewViewModel {

    private MutableLiveData<TipListItem> chosenTipLiveData;
    private MutableLiveData<ListItem> chosenIngredientLiveData;

    private boolean areListItemsClickable;

    private MutableLiveData<String> currentDetailFragmentLiveData;

    @Override
    public void init() {
        super.init();
        chosenTipLiveData = new MutableLiveData<>();
        chosenIngredientLiveData = new MutableLiveData<>();

        currentDetailFragmentLiveData = new MutableLiveData<>();
        currentDetailFragmentLiveData.setValue(TAG_FRAGMENT_OPENING);
    }

    public void setChosenTip(TipListItem item) {

        if(!areListItemsClickable) {
            currentDetailFragmentLiveData.setValue(TAG_FRAGMENT_DETAIL);
        }
        chosenTipLiveData.setValue(item);
    }

    public LiveData<TipListItem> getChosenTipLiveData() {
        return chosenTipLiveData;
    }

    public void setChosenIngredient(ListItem item) {

        if(!areListItemsClickable) {
            currentDetailFragmentLiveData.setValue(TAG_FRAGMENT_INGREDIENT);
        }
        chosenIngredientLiveData.setValue(item);
    }

    public LiveData<ListItem> getChosenIngredientLiveData() {
        return chosenIngredientLiveData;
    }

    public void setAreListItemsClickable() {
        areListItemsClickable = true;
    }

    public boolean getAreListItemsClickable() {
        return areListItemsClickable;
    }

    @Override
    public void setCategory(String category) {

        //if the category has changed and the detail fragment has changed (there is fragment transaction
        //proceeding) make list items unclickable until the transaction's end. It will prevent multiple
        //errors due to lack of context attached

        if (!getCategory().equals(category) && (getCategory().equals(CATEGORY_INGREDIENTS) ||
                category.equals(CATEGORY_INGREDIENTS))) {
            areListItemsClickable = false;
        }
        super.setCategory(category);
    }

    public LiveData<String> getCurrentDetailFragmentLiveData() {
        return currentDetailFragmentLiveData;
    }
}
