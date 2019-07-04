package com.blogspot.android_czy_java.beautytips.database.repository.forViewModels.recipe

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.blogspot.android_czy_java.beautytips.appUtils.categories.CategoryBody
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

    val recipeObserver: Observer<List<RecipeMappedModel>> = mock()

    val recipes = listOf<RecipeMappedModel>()

    lateinit var SUT: RecipeRepository

    @Before
    fun setUp() {

        `when`(recipeDao.getAllRecipes()).thenReturn(recipes)
        `when`(recipeDao.getRecipesByCategory(any())).thenReturn(recipes)
        `when`(recipeDao.getRecipesByCategoryAndSubcategory(any(), any())).thenReturn(recipes)

        SUT = RecipeRepository(recipeDao)

    }

    @Test
    fun categoryHairAndSubcategoryAll_getByDateCalled_callsGetRecipesByCategoryOnDatabase() {

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

    @Test
    fun categoryBodyAndSubcategoryLotions_getByDateCalled_callsGetRecipesByCategoryAndSubcategoryOnDatabase() {

        //given
        SUT.category = CategoryBody.SUBCATEGORY_LOTIONS

        //when
        SUT.getByDate()

        //then
        verify(recipeDao).getRecipesByCategoryAndSubcategory(
                CategoryBody.SUBCATEGORY_LOTIONS.getCategoryLabel(),
                CategoryBody.SUBCATEGORY_LOTIONS.getSubcategoryLabel()
        )

    }

    @Test
    fun categoryBodyAndSubcategoryLotions_getByDateCalled_updatesRecipeLiveData() {

        //given
        SUT.category = CategoryBody.SUBCATEGORY_LOTIONS
        SUT.recipeLiveData.observeForever(recipeObserver)

        //when
        SUT.getByDate()

        //then
        verify(recipeObserver).onChanged(any())

    }

    @Test
    fun categoryHairAndSubcategoryAll_getByPopularityCalled_callsGetRecipesByCategorySortedByPopularityOnDatabase() {

        //given
        SUT.category = CategoryHair.SUBCATEGORY_ALL

        //when
        SUT.getByPopularity()

        //then
        verify(recipeDao).getRecipesByCategoryOrderByPopularity(CategoryHair.SUBCATEGORY_ALL.getCategoryLabel())

    }

    @Test
    fun categoryHairAndSubcategoryAll_getByPopularityCalled_updatesRecipeLiveData() {

        //given
        SUT.category = CategoryHair.SUBCATEGORY_ALL
        SUT.recipeLiveData.observeForever(recipeObserver)

        //when
        SUT.getByPopularity()

        //then
        verify(recipeObserver).onChanged(any())

    }

    @Test
    fun categoryBodyAndSubcategoryLotions_getByPopularityCalled_callsGetRecipesByCategoryAndSubcategoryOrderedByPopularityOnDatabase() {

        //given
        SUT.category = CategoryBody.SUBCATEGORY_LOTIONS

        //when
        SUT.getByPopularity()

        //then
        verify(recipeDao).getRecipesByCategoryAndSubcategoryOrderByPopularity(
                CategoryBody.SUBCATEGORY_LOTIONS.getCategoryLabel(),
                CategoryBody.SUBCATEGORY_LOTIONS.getSubcategoryLabel()
        )

    }

    @Test
    fun categoryBodyAndSubcategoryLotions_getByPopularityCalled_updatesRecipeLiveData() {

        //given
        SUT.category = CategoryBody.SUBCATEGORY_LOTIONS
        SUT.recipeLiveData.observeForever(recipeObserver)

        //when
        SUT.getByPopularity()

        //then
        verify(recipeObserver).onChanged(any())

    }
}