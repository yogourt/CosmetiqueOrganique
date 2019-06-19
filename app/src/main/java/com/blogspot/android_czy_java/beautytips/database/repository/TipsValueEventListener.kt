package com.blogspot.android_czy_java.beautytips.database.repository

import com.blogspot.android_czy_java.beautytips.database.models.CommentModel
import com.blogspot.android_czy_java.beautytips.database.models.RecipeDetailModel
import com.blogspot.android_czy_java.beautytips.newTip.model.TipListItem
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class TipsValueEventListener(private val tipListDataSnapshot: DataSnapshot) : ValueEventListener {

    override fun onDataChange(tipsDataSnapshot: DataSnapshot) {

        Runnable {

            val tipDetailsMap: HashMap<Int, RecipeDetailModel> = HashMap()
            for (item in tipsDataSnapshot.children) {
                val comments = ArrayList<CommentModel>()

                for(commentItem in item.child("comments").children) {
                    val id = commentItem.key?.toLong()
                    val author = commentItem.child("a").value.toString()
                    val authorId = commentItem.child("b").value.toString()
                    val message = commentItem.child("c").value.toString()
                    if(id != null)
                    comments.add(CommentModel(id, authorId, author, message))
                }


            }

            for (item in tipListDataSnapshot.children) {
            }

        }.run()

    }

    override fun onCancelled(p0: DatabaseError) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}