package com.blogspot.android_czy_java.beautytips.viewmodel.detail;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.blogspot.android_czy_java.beautytips.view.recipe.MainActivity;


public class DetailActivityViewModel extends ViewModel {

    public long chosenItemId;

    private boolean isShowingIngredientFromRecipe;

    private MutableLiveData<String> currentDetailFragmentLiveData;


    public void init() {
        if (currentDetailFragmentLiveData == null) {
            currentDetailFragmentLiveData = new MutableLiveData<>();
            currentDetailFragmentLiveData.setValue(MainActivity.TAG_FRAGMENT_OPENING);
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
        setCurrentDetailFragmentLiveData(MainActivity.TAG_FRAGMENT_DETAIL);
        isShowingIngredientFromRecipe = false;
    }

    public void onRecipeClick(Long recipeId) {
        if (!(chosenItemId == recipeId)) {
            chosenItemId = recipeId;
            setCurrentDetailFragmentLiveData(MainActivity.TAG_FRAGMENT_DETAIL);
        }
    }

    public void onIngredientClick(Long ingredientId) {
        if (getIsShowingIngredientFromRecipe()) {
            setIsShowingIngredientFromRecipe(false);
        }

        if (!(chosenItemId == ingredientId)) {
            chosenItemId = ingredientId;
            setCurrentDetailFragmentLiveData(MainActivity.TAG_FRAGMENT_INGREDIENT);
        }
    }

}
