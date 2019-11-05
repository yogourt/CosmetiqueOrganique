package com.blogspot.android_czy_java.beautytips.view.detail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.blogspot.android_czy_java.beautytips.R
import com.blogspot.android_czy_java.beautytips.view.detail.callback.UserListChooserCallback
import com.blogspot.android_czy_java.beautytips.viewmodel.recipe.MainListData
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_user_list_chooser.view.*

class UserListAdapter(private val userLists: MainListData,
                      private val callback: UserListChooserCallback) : BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user_list_chooser, parent, false)

        val userList = userLists.data[position]

        view.title.text = userList.listTitle
        Glide.with(view).load(userList.data.last().imageUrl).into(view.image)

        view.setOnClickListener {
            callback.onClick(userList.listTitle)
        }

        return view;
    }

    override fun getItem(position: Int) = userLists.data[position]

    override fun getItemId(position: Int) = position.toLong()

    override fun getCount() = userLists.data.size

}