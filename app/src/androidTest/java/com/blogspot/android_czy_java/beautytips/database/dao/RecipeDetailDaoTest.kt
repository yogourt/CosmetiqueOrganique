package com.blogspot.android_czy_java.beautytips.database.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.blogspot.android_czy_java.beautytips.database.AppDatabase
import com.blogspot.android_czy_java.beautytips.database.models.RecipeDetailModel
import com.blogspot.android_czy_java.beautytips.database.models.RecipeModel
import org.hamcrest.CoreMatchers.*
import org.junit.After
import org.junit.Before

import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class RecipeDetailDaoTest {

    private val testedRecipeId = -1L
    private val exampleDescription = "example description"
    private val exampleTwoIngredients = "ingredientOne, ingredient Two"

    lateinit var db: AppDatabase
    lateinit var SUT: RecipeDetailDao

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).allowMainThreadQueries().build()
        SUT = db.recipeDetailDao()
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun getDescriptionCalled_recipeNotFound_returnsNull() {

        //given

        //when
        val result = SUT.getDescription(testedRecipeId)

        //then
        assertThat(result, `is`(nullValue()))
    }

    @Test
    fun getDescriptionCalled_recipeFound_returnsValidDescription() {

        //given
        db.recipeDao().insertRecipe(RecipeModel(testedRecipeId,
                "",
                "",
                "",
                "",
                "",
                0,
                RecipeDetailModel(description = exampleDescription, ingredients = "")))

        //when
        val result = SUT.getDescription(testedRecipeId)

        //then
        assertThat(result, equalTo(exampleDescription))
    }

    @Test
    fun getIngredients_recipeNotFound_returnsNull() {

        //given

        //when
        val result = SUT.getIngredients(testedRecipeId)

        //then
        assertThat(result, `is`(nullValue()))
    }

    @Test
    fun getIngredients_forRecipeWithTwoIngredients_returnsIngredientString() {
        //given
        db.recipeDao().insertRecipe(RecipeModel(testedRecipeId,
                "",
                "",
                "",
                "",
                "",
                0,
                RecipeDetailModel(description = exampleDescription, ingredients = exampleTwoIngredients)))

        //when
        val result = SUT.getIngredients(testedRecipeId)

        //then
        assertThat(result, equalTo(exampleTwoIngredients))
    }




}