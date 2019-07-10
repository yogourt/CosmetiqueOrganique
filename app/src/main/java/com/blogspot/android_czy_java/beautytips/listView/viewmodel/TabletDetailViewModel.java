package com.blogspot.android_czy_java.beautytips.listView.viewmodel;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import static com.blogspot.android_czy_java.beautytips.listView.view.MainActivity.TAG_FRAGMENT_DETAIL;
import static com.blogspot.android_czy_java.beautytips.listView.view.MainActivity.TAG_FRAGMENT_INGREDIENT;
import static com.blogspot.android_czy_java.beautytips.listView.view.MainActivity.TAG_FRAGMENT_OPENING;

public class TabletDetailViewModel extends ViewModel {

    public long chosenItemId;

    private boolean isShowingIngredientFromRecipe;

    private MutableLiveData<String> currentDetailFragmentLiveData;


    public void init() {
        if (currentDetailFragmentLiveData == null) {
            currentDetailFragmentLiveData = new MutableLiveData<>();
            currentDetailFragmentLiveData.setValue(TAG_FRAGMENT_OPENING);
        }
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

    public void onGoingBackFromIngredientToDetail() {
        setCurrentDetailFragmentLiveData(TAG_FRAGMENT_DETAIL);
        isShowingIngredientFromRecipe = false;
    }

    public void onRecipeClick(Long recipeId) {
        if (!(chosenItemId == recipeId)) {
            chosenItemId = recipeId;
            setCurrentDetailFragmentLiveData(TAG_FRAGMENT_DETAIL);
        }
    }

    public void onIngredientClick(Long ingredientId) {
        if (getIsShowingIngredientFromRecipe()) {
            setIsShowingIngredientFromRecipe(false);
        }

        if (!(chosenItemId == ingredientId)) {
            chosenItemId = ingredientId;
            setCurrentDetailFragmentLiveData(TAG_FRAGMENT_INGREDIENT);
        }
    }

}
