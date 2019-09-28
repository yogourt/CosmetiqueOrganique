package com.blogspot.android_czy_java.beautytips.view.comment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.blogspot.android_czy_java.beautytips.R
import com.blogspot.android_czy_java.beautytips.database.comment.CommentModel

class CommentListAdapter(private val comments: List<CommentModel>) : RecyclerView.Adapter<CommentListAdapter.CommentViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {

        val inflater = LayoutInflater.from(parent.context)

        return CommentViewHolder(inflater.inflate(if (viewType == TYPE_COMMENT) {
            R.layout.item_discussion_topic
        } else {
            R.layout.item_discussion_comment
        }, parent, false))

    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        val commentItem = comments[position]

        holder.apply {
            comment.text = commentItem.message
            author.text = commentItem.authorNickname
        }

    }

    override fun getItemViewType(position: Int): Int {
        return if (comments[position].responseTo == null) {
            TYPE_COMMENT
        } else {
            TYPE_SUBCOMMENT
        }
    }

    override fun getItemCount() = comments.size

    inner class CommentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val photo = itemView.findViewById<ImageView>(R.id.photo)
        val comment = itemView.findViewById<TextView>(R.id.comment)
        val author = itemView.findViewById<TextView>(R.id.nickname)
    }

    companion object {
        const val TYPE_COMMENT = 0
        const val TYPE_SUBCOMMENT = 1
    }
}