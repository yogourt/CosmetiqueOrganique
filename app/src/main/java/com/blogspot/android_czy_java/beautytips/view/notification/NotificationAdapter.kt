package com.blogspot.android_czy_java.beautytips.view.notification

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.blogspot.android_czy_java.beautytips.R
import com.blogspot.android_czy_java.beautytips.database.notification.NotificationModel
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_notification.view.*

class NotificationAdapter(private val data: List<NotificationModel>) :
        RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        val view = inflater.inflate(R.layout.item_notification, parent, false)

        return NotificationViewHolder(view)
    }

    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) {

        val notification = data[position]

        holder.apply {
            Glide.with(itemView).load(notification.image).into(image)
            message.text = notification.message
            date.text = notification.time
        }

    }

    override fun getItemCount() = data.size


    class NotificationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val image = itemView.image
        val message = itemView.message
        val date = itemView.date

    }

}