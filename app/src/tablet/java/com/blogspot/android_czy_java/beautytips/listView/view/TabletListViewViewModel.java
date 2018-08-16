package com.blogspot.android_czy_java.beautytips.listView.view;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.blogspot.android_czy_java.beautytips.listView.ListViewViewModel;
import com.blogspot.android_czy_java.beautytips.listView.model.ListItem;
import com.blogspot.android_czy_java.beautytips.listView.model.TipListItem;


public class TabletListViewViewModel extends ListViewViewModel {

    private MutableLiveData<TipListItem> chosenTipLiveData;
    private MutableLiveData<ListItem> chosenIngredientLiveData;

    @Override
    public void init() {
        super.init();
        chosenTipLiveData = new MutableLiveData<>();
        chosenIngredientLiveData = new MutableLiveData<>();
    }

    public void setChosenTip(TipListItem item) {
        chosenTipLiveData.setValue(item);
    }

    public LiveData<TipListItem> getChosenTipLiveData() {
        return chosenTipLiveData;
    }

    public void setChosenIngredient(ListItem item) {
        chosenIngredientLiveData.setValue(item);
    }
    public LiveData<ListItem> getChosenIngredientLiveData() {
        return chosenIngredientLiveData;
    }
}
