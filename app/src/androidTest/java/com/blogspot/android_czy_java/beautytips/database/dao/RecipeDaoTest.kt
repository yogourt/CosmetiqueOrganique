package com.blogspot.android_czy_java.beautytips.database.dao

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.blogspot.android_czy_java.beautytips.database.AppDatabase
import com.blogspot.android_czy_java.beautytips.database.detail.RecipeDetailModel
import com.blogspot.android_czy_java.beautytips.database.recipe.RecipeDao
import com.blogspot.android_czy_java.beautytips.database.recipe.RecipeModel
import com.nhaarman.mockito_kotlin.mock
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.verify

class RecipeDaoTest {

    @Rule
    @JvmField
    var rule = InstantTaskExecutorRule()

    private val favNumObserver: Observer<Long> = mock()

    private val testedRecipeId = -1L

    lateinit var db: AppDatabase
    lateinit var SUT: RecipeDao

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).allowMainThreadQueries().build()
        SUT = db.recipeDao()
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun getFavNum_recipeNotFound_returnsNull() {

        //given
        SUT.getFavNum(testedRecipeId).observeForever(favNumObserver)

        //when

        //then
        verify(favNumObserver).onChanged(null)
    }

    @Test
    fun getFavNum_recipeFound_returnsLiveDataWithIntValue() {

        //given
        SUT.getFavNum(testedRecipeId).observeForever(favNumObserver)

        val recipe = RecipeModel(testedRecipeId,
                "",
                "",
                "",
                "",
                "",
                0,
                "")
        recipe.details = RecipeDetailModel(description = "", ingredients = "")

        //when
        db.recipeDao().insertRecipes(listOf(recipe))

        //then
        verify(favNumObserver).onChanged(0)
    }
}