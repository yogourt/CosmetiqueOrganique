package com.blogspot.android_czy_java.beautytips.view.comment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.blogspot.android_czy_java.beautytips.R
import com.blogspot.android_czy_java.beautytips.view.comment.callback.CommentListCallback
import com.blogspot.android_czy_java.beautytips.viewmodel.comment.CommentWithAuthorModel

class CommentListAdapter(private val callback: CommentListCallback,
                         private val comments: List<CommentWithAuthorModel>) : RecyclerView.Adapter<CommentListAdapter.CommentViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {

        val inflater = LayoutInflater.from(parent.context)

        return if(viewType == TYPE_COMMENT) {
            TopicViewHolder(inflater.inflate(R.layout.item_discussion_topic, parent, false))
        } else {
            CommentViewHolder(inflater.inflate(R.layout.item_discussion_comment, parent, false))
        }
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        val commentItem = comments[position]

        holder.apply {
            comment.text = commentItem.comment.message
            author.text = commentItem.author?.nickname
        }

        if(holder is TopicViewHolder) {
            holder.apply {

                if(commentItem.comment.firebaseId == null) {
                    reply.visibility = View.GONE
                }

                sendSubcommentButton.setOnClickListener {
                    callback.addComment(
                            newComment.text.toString(),
                            commentItem.comment.firebaseId
                    )
                }
            }
        }

    }

    override fun getItemViewType(position: Int): Int {
        return if (comments[position].comment.responseTo == null) {
            TYPE_COMMENT
        } else {
            TYPE_SUBCOMMENT
        }
    }

    override fun getItemCount() = comments.size

    open inner class CommentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val photo = itemView.findViewById<ImageView>(R.id.photo)
        val comment = itemView.findViewById<TextView>(R.id.comment)
        val author = itemView.findViewById<TextView>(R.id.nickname)
    }

    inner class TopicViewHolder(itemView: View) : CommentViewHolder(itemView) {

        val reply = itemView.findViewById<TextView>(R.id.reply)
        val newCommentLayout = itemView.findViewById<LinearLayout>(R.id.new_comment_layout)
        val newComment = itemView.findViewById<EditText>(R.id.new_comment)
        val sendSubcommentButton = itemView.findViewById<ImageView>(R.id.button_add_new)

        init {
            reply.setOnClickListener {
                reply.visibility = View.GONE
                newCommentLayout.visibility = View.VISIBLE
            }
        }
    }

    companion object {
        const val TYPE_COMMENT = 0
        const val TYPE_SUBCOMMENT = 1
    }
}