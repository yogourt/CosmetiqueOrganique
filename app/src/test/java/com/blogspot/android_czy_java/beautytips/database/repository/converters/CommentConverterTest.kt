package com.blogspot.android_czy_java.beautytips.database.repository.converters

import com.blogspot.android_czy_java.beautytips.database.FirebaseKeys
import com.blogspot.android_czy_java.beautytips.database.comment.CommentConverter
import com.blogspot.android_czy_java.beautytips.database.comment.CommentModel
import com.google.firebase.database.DataSnapshot
import org.hamcrest.CoreMatchers.equalTo
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class CommentConverterTest {

    private val testedRecipeId = -1L
    private val testedCommentId = "123"
    private val testedCommentMessage = "example message"
    private val testedCommentAuthor = "example author"
    private val testedCommentAuthorId = "example author id"

    private val comment = CommentModel(testedCommentId, testedRecipeId, testedCommentAuthorId, testedCommentAuthor, testedCommentMessage)

    @Mock
    private lateinit var dataSnapshot: DataSnapshot

    @Mock
    private lateinit var messageDataSnapshot: DataSnapshot

    @Mock
    private lateinit var authorDataSnapshot: DataSnapshot

    @Mock
    private lateinit var authorIdDataSnapshot: DataSnapshot

    private lateinit var SUT: CommentConverter

    @Before
    fun setUp() {

        `when`(dataSnapshot.key).thenReturn(testedCommentId)
        `when`(dataSnapshot.child(FirebaseKeys.KEY_COMMENT_MESSAGE)).thenReturn(messageDataSnapshot)
        `when`(messageDataSnapshot.value).thenReturn(testedCommentMessage)
        `when`(dataSnapshot.child(FirebaseKeys.KEY_COMMENT_AUTHOR)).thenReturn(authorDataSnapshot)
        `when`(authorDataSnapshot.value).thenReturn(testedCommentAuthor)
        `when`(dataSnapshot.child(FirebaseKeys.KEY_COMMENT_AUTHOR_ID)).thenReturn(authorIdDataSnapshot)
        `when`(authorIdDataSnapshot.value).thenReturn(testedCommentAuthorId)

        SUT = CommentConverter(dataSnapshot, testedRecipeId)

    }

    @Test
    fun getComment_convertsDataSnapshot_returnsCommentModelObject() {

        //given

        //when
        val convertedComment = SUT.getComment()

        //then
        assertThat(convertedComment, equalTo(comment))
    }


}