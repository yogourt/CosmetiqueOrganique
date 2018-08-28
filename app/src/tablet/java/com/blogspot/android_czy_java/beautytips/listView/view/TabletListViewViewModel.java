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

    private TipListItem chosenTip;
    private ListItem chosenIngredient;

    private boolean isShowingIngredientFromRecipe;

    private MutableLiveData<String> currentDetailFragmentLiveData;

    @Override
    public void init() {
        super.init();

        currentDetailFragmentLiveData = new MutableLiveData<>();
        currentDetailFragmentLiveData.setValue(TAG_FRAGMENT_OPENING);
    }

    public void setChosenTip(TipListItem item) {
        chosenTip = item;
    }

    public TipListItem getChosenTip() {
        return chosenTip;
    }

    public void setChosenIngredient(ListItem item) {
        chosenIngredient = item;
    }

    public ListItem getChosenIngredient() {
        return chosenIngredient;
    }

    public LiveData<String> getCurrentDetailFragmentLiveData() {
        return currentDetailFragmentLiveData;
    }

    public void setCurrentDetailFragmentLiveData(String fragmentTag) {
        currentDetailFragmentLiveData.setValue(fragmentTag);
    }

    public String getCurrentDetailFragment() {
        return currentDetailFragmentLiveData.getValue();
    }

    public void setIsShowingIngredientFromRecipe(boolean value) {
        isShowingIngredientFromRecipe = value;
    }

    public boolean getIsShowingIngredientFromRecipe() {
        return isShowingIngredientFromRecipe;
    }
}
