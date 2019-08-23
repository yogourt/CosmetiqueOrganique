package com.blogspot.android_czy_java.beautytips.view.account

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.blogspot.android_czy_java.beautytips.R
import com.blogspot.android_czy_java.beautytips.database.recipe.RecipeModel
import com.blogspot.android_czy_java.beautytips.view.listView.view.callback.NestedListCallback
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.card_expanded.view.*

class VerticalRecipeListAdapter(private val data: List<RecipeModel>,
                                private val activityCallback: NestedListCallback):
        RecyclerView.Adapter<VerticalRecipeListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.card_expanded,
                parent, false)

        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val recipe = data[position]

        holder.apply {
            card.setOnClickListener{
                 activityCallback.onRecipeClick(recipe.recipeId)
            }
            title.text = recipe.title
            Glide.with(card).load(recipe.imageUrl).into(image)
        }
    }

    override fun getItemCount() = data.size


    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val card: View = itemView.card_expanded
        val title: TextView = itemView.title
        val image: ImageView = itemView.image
    }
}