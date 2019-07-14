package com.blogspot.android_czy_java.beautytips.listView.viewmodel

import androidx.lifecycle.Observer
import com.blogspot.android_czy_java.beautytips.appUtils.categories.CategoryHair
import com.blogspot.android_czy_java.beautytips.appUtils.orders.Order
import com.blogspot.android_czy_java.beautytips.database.recipe.RecipeModel
import com.blogspot.android_czy_java.beautytips.usecase.recipe.LoadRecipesUseCase
import com.blogspot.android_czy_java.beautytips.usecase.recipe.RecipeRequest
import com.blogspot.android_czy_java.beautytips.viewmodel.recipe.RecipeViewModel
import org.junit.Before

import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.any
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class RecipeViewModelTest {

    @Mock
    private lateinit var loadRecipesUseCase: LoadRecipesUseCase

    @Mock
    private lateinit var recipeObserver: Observer<List<RecipeModel>>


    private lateinit var SUT: RecipeViewModel

    @Before
    fun setUp() {
        SUT = RecipeViewModel(loadRecipesUseCase)
    }

    @Test
    fun orderChangeToPopularity_callsExecuteOnLoadRecipesUseCase() {

        //given

        //when
        SUT.order = Order.POPULARITY

        //then
        verify(loadRecipesUseCase).execute(RecipeRequest(any(), Order.POPULARITY))
    }

    @Test
    fun categoryChangeToHair_callsExecuteOnLoadRecipesUseCase() {

        //given

        //when
        SUT.category = CategoryHair.SUBCATEGORY_ALL

        //then
        verify(loadRecipesUseCase).execute(RecipeRequest(CategoryHair.SUBCATEGORY_ALL, any()))
    }

}