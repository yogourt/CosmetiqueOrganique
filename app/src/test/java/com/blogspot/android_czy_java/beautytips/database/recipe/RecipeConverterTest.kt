package com.blogspot.android_czy_java.beautytips.database.recipe

import com.blogspot.android_czy_java.beautytips.database.FirebaseKeys
import com.google.firebase.database.DataSnapshot
import org.hamcrest.CoreMatchers.equalTo
import org.junit.Before

import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class RecipeConverterTest {

    private val testedRecipeId = -1L
    private val testedRecipeTitle = "example title"
    private val testedRecipeImage = "example image"
    private val testedRecipeAuthorId = "example author id"
    private val testedRecipeCategory = "example category"
    private val testedRecipeSubcategory = "example subcategory"
    private val testedRecipeFavNum = 10L
    private val testedRecipeTags = "tag1, tag2"

    @Mock
    private lateinit var recipeDataSnapshot: DataSnapshot

    @Mock
    private lateinit var titleDataSnapshot: DataSnapshot

    @Mock
    private lateinit var imageDataSnapshot: DataSnapshot

    @Mock
    private lateinit var authorIdDataSnapshot: DataSnapshot

    @Mock
    private lateinit var categoryDataSnapshot: DataSnapshot

    @Mock
    private lateinit var subcategoryDataSnapshot: DataSnapshot

    @Mock
    private lateinit var favNumDataSnapshot: DataSnapshot

    @Mock
    private lateinit var tagsDataSnapshot: DataSnapshot

    private lateinit var SUT: RecipeConverter

    @Before
    fun setUp() {

        `when`(recipeDataSnapshot.key).thenReturn(testedRecipeId.toString())

        `when`(recipeDataSnapshot.child(FirebaseKeys.KEY_RECIPE_TITLE)).thenReturn(titleDataSnapshot)
        `when`(recipeDataSnapshot.child(FirebaseKeys.KEY_RECIPE_IMAGE)).thenReturn(imageDataSnapshot)
        `when`(recipeDataSnapshot.child(FirebaseKeys.KEY_RECIPE_AUTHOR_ID)).thenReturn(authorIdDataSnapshot)
        `when`(recipeDataSnapshot.child(FirebaseKeys.KEY_RECIPE_CATEGORY)).thenReturn(categoryDataSnapshot)
        `when`(recipeDataSnapshot.child(FirebaseKeys.KEY_RECIPE_SUBCATEGORY)).thenReturn(subcategoryDataSnapshot)
        `when`(recipeDataSnapshot.child(FirebaseKeys.KEY_RECIPE_FAV_NUM)).thenReturn(favNumDataSnapshot)
        `when`(recipeDataSnapshot.child(FirebaseKeys.KEY_RECIPE_TAGS)).thenReturn(tagsDataSnapshot)

        `when`(titleDataSnapshot.value).thenReturn(testedRecipeTitle)
        `when`(imageDataSnapshot.value).thenReturn(testedRecipeImage)
        `when`(categoryDataSnapshot.value).thenReturn(testedRecipeCategory)
        `when`(subcategoryDataSnapshot.value).thenReturn(testedRecipeSubcategory)
        `when`(tagsDataSnapshot.value).thenReturn(testedRecipeTags)

        SUT = RecipeConverter(recipeDataSnapshot)
    }

    @Test
    fun whenNoAuthorId_getRecipeCalled_returnsRecipeModelObjectWithNullAuthorId() {

        //given
        `when`(favNumDataSnapshot.value).thenReturn(testedRecipeFavNum)

        val expectedRecipe = RecipeModel(
                testedRecipeId,
                testedRecipeTitle,
                testedRecipeImage,
                null,
                testedRecipeCategory,
                testedRecipeSubcategory,
                testedRecipeFavNum,
                testedRecipeTags

        )

        //when
        val result = SUT.getRecipe()

        //then
        assertThat(result, equalTo(expectedRecipe))

    }

    @Test
    fun whenAuthorIdAndFavNumInSnapshot_getRecipeCalled_returnsRecipeModelObjectWithAuthorIdAndFavNum() {

        //given
        `when`(favNumDataSnapshot.value).thenReturn(testedRecipeFavNum)
        `when`(authorIdDataSnapshot.value).thenReturn(testedRecipeAuthorId)

        val expectedRecipe = RecipeModel(
                testedRecipeId,
                testedRecipeTitle,
                testedRecipeImage,
                testedRecipeAuthorId,
                testedRecipeCategory,
                testedRecipeSubcategory,
                testedRecipeFavNum,
                testedRecipeTags

        )

        //when
        val result = SUT.getRecipe()

        //then
        assertThat(result, equalTo(expectedRecipe))


    }

    @Test
    fun whenNoFavNum_getRecipeCalled_returnsRecipeModelObjectWith0FavNum() {

        //given
        `when`(authorIdDataSnapshot.value).thenReturn(testedRecipeAuthorId)

        val expectedRecipe = RecipeModel(
                testedRecipeId,
                testedRecipeTitle,
                testedRecipeImage,
                testedRecipeAuthorId,
                testedRecipeCategory,
                testedRecipeSubcategory,
                0,
                testedRecipeTags

        )

        //when
        val result = SUT.getRecipe()

        //then
        assertThat(result, equalTo(expectedRecipe))

    }

}