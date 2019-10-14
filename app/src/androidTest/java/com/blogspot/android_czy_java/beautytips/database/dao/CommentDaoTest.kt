package com.blogspot.android_czy_java.beautytips.database.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import com.blogspot.android_czy_java.beautytips.database.AppDatabase
import com.blogspot.android_czy_java.beautytips.database.comment.CommentDao
import com.blogspot.android_czy_java.beautytips.database.comment.CommentModel
import org.hamcrest.Matchers.equalTo
import org.junit.After
import org.junit.Before
import org.junit.Test

class CommentDaoTest {

    private val testedRecipeId = -1L
    private val otherRecipeId = -1L
    private val testedCommentId = "123"
    private val otherCommentId = "234"
    private val testedCommentDesc = "example message"
    private val testedCommentAuthor = "example author"
    private val testedCommentAuthorId = "example author firebaseId"

    lateinit var db: AppDatabase
    lateinit var SUT: CommentDao

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).allowMainThreadQueries().build()
        SUT = db.commentDao()
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun insertedComment_getCommentsForRecipe_returnsComment() {

        //given
        val insertedComment = CommentModel(testedCommentId, testedRecipeId, testedCommentAuthorId, testedCommentDesc, testedCommentAuthor)
        SUT.insertComments(listOf(insertedComment))

        //when
        val result = SUT.getComments(testedRecipeId)

        //then
        assertThat(result[0], equalTo(insertedComment))

    }

    @Test
    fun insertedTwoComments_getCommentsForRecipe_returnsRightComment() {

        //given
        val insertedComment = CommentModel(testedCommentId, testedRecipeId, testedCommentAuthorId, testedCommentDesc, testedCommentAuthor)
        val otherComment = CommentModel(otherCommentId, otherRecipeId, testedCommentAuthorId, testedCommentDesc, testedCommentAuthor)
        SUT.insertComments(listOf(insertedComment, otherComment))

        //when
        val result = SUT.getComments(testedRecipeId)

        //then
        for(resultElement in result)
        assertThat(resultElement.recipeId, equalTo(testedRecipeId))

    }
}