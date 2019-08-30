package com.blogspot.android_czy_java.beautytips.view.listView.view

import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blogspot.android_czy_java.beautytips.R
import com.blogspot.android_czy_java.beautytips.database.recipe.RecipeModel
import com.blogspot.android_czy_java.beautytips.view.listView.utils.recyclerViewUtils.SpacesItemDecoration
import com.blogspot.android_czy_java.beautytips.view.listView.view.callback.NestedListCallback
import com.blogspot.android_czy_java.beautytips.viewmodel.recipe.MainListData
import com.bumptech.glide.Glide
import com.github.rubensousa.gravitysnaphelper.GravitySnapHelper
import kotlinx.android.synthetic.main.card_expanded.view.*
import kotlinx.android.synthetic.main.main_list_item.view.*

class MainListAdapter(private val callback: NestedListCallback,
                      public var parents: MainListData)
    : RecyclerView.Adapter<MainListAdapter.ViewHolder>() {

    private val viewPool = RecyclerView.RecycledViewPool()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
                .inflate(R.layout.main_list_item, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount() = parents.data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val parent = parents.data[position]

        holder.listTitle.text = parent.listTitle

        prepareRecipeList(holder, parent.data)

        if (parent.data.isNotEmpty()) {
            prepareExpandedRecipe(holder, parent.data.random())
        }

    }

    private fun prepareRecipeList(holder: ViewHolder, data: List<RecipeModel>) {
        val childLayoutManager = LinearLayoutManager(
                holder.recyclerView.context, LinearLayout.HORIZONTAL, false)
        childLayoutManager.initialPrefetchItemCount = 4

        holder.recyclerView.apply {
            layoutManager = childLayoutManager
            adapter = RecipeListAdapter(callback, data)
            addItemDecoration(SpacesItemDecoration(
                    resources.getDimension(R.dimen.list_padding).toInt()))
            GravitySnapHelper(Gravity.START).attachToRecyclerView(this)
            setRecycledViewPool(viewPool)
        }
    }

    private fun prepareExpandedRecipe(holder: ViewHolder, recipe: RecipeModel) {
        holder.expandedTitle.text = recipe.title
        Glide.with(holder.itemView).load(recipe.imageUrl).into(holder.expandedImage)
        holder.expandedLayout.setOnClickListener {
            callback.onRecipeClick(recipe.recipeId)
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val recyclerView: RecyclerView = itemView.categorised_list
        val listTitle: TextView = itemView.list_title

        val expandedImage: ImageView = itemView.image
        val expandedTitle: TextView = itemView.title
        val expandedLayout: View = itemView.card_first

    }
}