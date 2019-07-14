package com.blogspot.android_czy_java.beautytips.database.detail

import com.blogspot.android_czy_java.beautytips.database.FirebaseKeys
import com.google.firebase.database.DataSnapshot
import org.hamcrest.CoreMatchers.equalTo
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DetailConverterTest {

    private val testedRecipeDescription = "example description"
    private val testedRecipeSource = "example source"
    private val testedRecipeIngredients = "ingredient1, ingredient2"

    @Mock
    private lateinit var detailDataSnapshot: DataSnapshot

    @Mock
    private lateinit var descriptionDataSnapshot: DataSnapshot

    @Mock
    private lateinit var sourceDataSnapshot: DataSnapshot

    @Mock
    private lateinit var ingredientsDataSnapshot: DataSnapshot


    private lateinit var SUT: DetailConverter

    @Before
    fun setUp() {

        `when`(detailDataSnapshot.child(FirebaseKeys.KEY_RECIPE_DESCRIPTION)).thenReturn(descriptionDataSnapshot)
        `when`(detailDataSnapshot.child(FirebaseKeys.KEY_RECIPE_INGREDIENTS)).thenReturn(ingredientsDataSnapshot)
        `when`(detailDataSnapshot.child(FirebaseKeys.KEY_RECIPE_SOURCE)).thenReturn(sourceDataSnapshot)

        `when`(descriptionDataSnapshot.value).thenReturn(testedRecipeDescription)
        `when`(ingredientsDataSnapshot.value).thenReturn(testedRecipeIngredients)

        SUT = DetailConverter(detailDataSnapshot)

    }

    @Test
    fun dataSnapshotNotContainingSource_getDetailsCalled_returnsRecipeDetailModelObjectWithNullSource() {
        // Given
        `when`(sourceDataSnapshot.value).thenReturn(null)

        // When
        val result = SUT.getDetails()

        // Then
        assertThat(result, equalTo(RecipeDetailModel(testedRecipeDescription, null, testedRecipeIngredients)))
    }

    @Test
    fun dataSnapshotContainingSource_getDetailsCalled_returnsRecipeDetailModelObjectWithSource() {
        // Given
        `when`(sourceDataSnapshot.value).thenReturn(testedRecipeSource)

        // When
        val result = SUT.getDetails()

        // Then
        assertThat(result, equalTo(RecipeDetailModel(testedRecipeDescription, testedRecipeSource, testedRecipeIngredients)))
    }
}