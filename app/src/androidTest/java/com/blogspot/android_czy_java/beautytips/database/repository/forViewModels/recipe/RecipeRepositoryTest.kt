package com.blogspot.android_czy_java.beautytips.database.repository.forViewModels.recipe

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.blogspot.android_czy_java.beautytips.appUtils.categories.CategoryHair
import com.blogspot.android_czy_java.beautytips.database.recipe.RecipeDao
import com.blogspot.android_czy_java.beautytips.database.recipe.RecipeMappedModel
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.`when`

class RecipeRepositoryTest {

    @Rule
    @JvmField
    var rule = InstantTaskExecutorRule()

    val recipeDao: RecipeDao = mock()

    val recipeFromRepoLiveData: LiveData<List<RecipeMappedModel>> = mock()

    val recipeObserver: Observer<List<RecipeMappedModel>> = mock()

    val recipes = listOf<RecipeMappedModel>()

    lateinit var SUT: RecipeRepository

    @Before
    fun setUp() {

        `when`(recipeDao.getRecipesByCategory(any())).thenReturn(recipes)
        `when`(recipeDao.getAllRecipes()).thenReturn(recipeFromRepoLiveData)
        `when`(recipeFromRepoLiveData.value).thenReturn(recipes)

        SUT = RecipeRepository(recipeDao)

    }

    @Test
    fun categoryHairAndSubcategoryAll_getByDateCalled_callsGetRecipesFromHairCategoryOnDatabase() {

        //given
        SUT.category = CategoryHair.SUBCATEGORY_ALL

        //when
        SUT.getByDate()

        //then
        verify(recipeDao).getRecipesByCategory(CategoryHair.SUBCATEGORY_ALL.getCategoryLabel())

    }

    @Test
    fun categoryHairAndSubcategoryAll_getByDateCalled_updatesRecipeLiveData() {

        //given
        SUT.category = CategoryHair.SUBCATEGORY_ALL
        SUT.recipeLiveData.observeForever(recipeObserver)

        //when
        SUT.getByDate()

        //then
        verify(recipeObserver).onChanged(any())

    }
}