package com.blogspot.android_czy_java.beautytips.view.detail

import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.blogspot.android_czy_java.beautytips.R
import com.blogspot.android_czy_java.beautytips.view.detail.callback.UserListChooserCallback
import com.blogspot.android_czy_java.beautytips.viewmodel.recipe.MainListData
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_user_list_chooser.view.*
import kotlinx.android.synthetic.main.item_user_list_chooser.view.title
import kotlinx.android.synthetic.main.item_user_list_new.view.*

class UserListAdapter(private var userLists: MainListData,
                      private val callback: UserListChooserCallback) : BaseAdapter() {

    init {
        userLists = MainListData(userLists.data.filter { it.listTitle != "My recipes" })
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val view: View

        if (position == count - 1) {
            view = LayoutInflater.from(parent.context).inflate(R.layout.item_user_list_new, parent, false)

            view.plus.setOnClickListener {
                val title = view.title.text.toString()
                if (!TextUtils.isEmpty(title))
                    callback.addList(title)
            }
        } else {

            view = LayoutInflater.from(parent.context).inflate(R.layout.item_user_list_chooser, parent, false)

            val userList = userLists.data[position]

            view.title.text = userList.listTitle
            userList.data.lastOrNull()?.let { recipe ->
                Glide.with(view).load(recipe.imageUrl).into(view.image)
            }

            view.setOnClickListener {
                callback.onClick(userList.listTitle)
            }
        }

        return view
    }

    override fun getItem(position: Int) = userLists.data[position]

    override fun getItemId(position: Int) = position.toLong()

    override fun getCount() = userLists.data.size + 1

}