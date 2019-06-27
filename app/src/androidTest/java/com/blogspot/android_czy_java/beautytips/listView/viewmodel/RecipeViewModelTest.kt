package com.blogspot.android_czy_java.beautytips.listView.viewmodel

import androidx.lifecycle.Observer
import com.blogspot.android_czy_java.beautytips.database.models.mapped.RecipeMappedModel
import com.blogspot.android_czy_java.beautytips.database.repository.forViewModels.RecipeRepositoryInterface
import org.junit.Before

import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class RecipeViewModelTest {

    @Mock
    private lateinit var recipeRepository: RecipeRepositoryInterface

    @Mock
    private lateinit var recipeObserver: Observer<List<RecipeMappedModel>>


    private lateinit var SUT: RecipeViewModel

    @Before
    fun setUp() {
        SUT = RecipeViewModel(recipeRepository)
    }

    @Test
    fun getRecipesByDate_callsGetByDateFromRepository() {

        //given

        //when
        SUT.getRecipesByDate()

        //then
        verify(recipeRepository).getByDate()
    }

    @Test
    fun getRecipesByPopularity_callsGetByPopularityFromRepository() {

        //given

        //when
        SUT.getRecipesByPopularity()

        //then
        verify(recipeRepository).getByPopularity()
    }

}