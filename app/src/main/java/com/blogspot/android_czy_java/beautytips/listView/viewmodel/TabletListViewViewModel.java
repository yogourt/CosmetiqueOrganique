package com.blogspot.android_czy_java.beautytips.listView.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.blogspot.android_czy_java.beautytips.listView.model.ListItem;
import com.blogspot.android_czy_java.beautytips.listView.model.TipListItem;

import static com.blogspot.android_czy_java.beautytips.listView.view.MainActivity.TAG_FRAGMENT_OPENING;

public class TabletListViewViewModel extends ListViewViewModel {

    private MutableLiveData<TipListItem> chosenTip;
    private ListItem chosenIngredient;

    private boolean isShowingIngredientFromRecipe;

    private MutableLiveData<String> currentDetailFragmentLiveData;

    private MutableLiveData<Boolean> tipChangeIndicator;

    @Override
    public void init() {
        super.init();

        if(currentDetailFragmentLiveData == null) {
            currentDetailFragmentLiveData = new MutableLiveData<>();
            currentDetailFragmentLiveData.setValue(TAG_FRAGMENT_OPENING);
            chosenTip = new MutableLiveData<>();
            tipChangeIndicator = new MutableLiveData<>();
        }
    }

    public void setChosenTip(TipListItem item) {
        chosenTip.setValue(item);
    }

    public TipListItem getChosenTip() {
        return chosenTip.getValue();
    }

    public LiveData<TipListItem> getChosenTipLiveData() {
        return  chosenTip;
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

    public void notifyTipChange() {
        tipChangeIndicator.setValue(true);
    }

    public LiveData<Boolean> getTipChangeIndicator() {
        return tipChangeIndicator;
    }

}
