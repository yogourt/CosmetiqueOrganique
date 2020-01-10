package com.blogspot.android_czy_java.beautytips.view.notification

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.blogspot.android_czy_java.beautytips.R
import com.blogspot.android_czy_java.beautytips.database.notification.NotificationModel
import com.blogspot.android_czy_java.beautytips.view.notification.callback.NotificationListCallback
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_notification.view.*

class NotificationAdapter(private val data: List<NotificationModel>,
                          private val callback: NotificationListCallback) :
        RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder>() {

    companion object {
        const val ITEM_TYPE_SEEN = 1
        const val ITEM_TYPE_UNSEEN = 2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        val view = inflater.inflate(
                if (viewType == ITEM_TYPE_SEEN) R.layout.item_notification
                else R.layout.item_notification_unseen,
                parent,
                false)

        return NotificationViewHolder(view)
    }

    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) {

        val notification = data[position]

        holder.apply {
            Glide.with(itemView).load(notification.image).into(image)
            message.text = notification.message
            date.text = notification.time
            itemView.setOnClickListener {
                handleClick(notification)
            }
        }

    }

    override fun getItemViewType(position: Int): Int {
        return if (data[position].seen) {
            ITEM_TYPE_SEEN
        } else {
            ITEM_TYPE_UNSEEN
        }
    }

    private fun handleClick(notification: NotificationModel) {
        if (notification.recipeId != null) {
            callback.onRecipeClick(notification.recipeId)
        }
        if (!notification.seen) {
            callback.makeNotificationSeen(notification.id)
        }
    }

    override fun getItemCount() = data.size


    class NotificationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val image = itemView.image
        val message = itemView.message
        val date = itemView.date

    }

}