package com.blogspot.android_czy_java.beautytips.view.recipe

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

import com.blogspot.android_czy_java.beautytips.R
import com.blogspot.android_czy_java.beautytips.database.recipe.RecipeModel
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

import butterknife.BindView
import butterknife.ButterKnife
import com.blogspot.android_czy_java.beautytips.view.recipe.callback.ListCallback


class RecipeListAdapter(private val activityCallback: ListCallback.InnerListCallback,
                        private val items: List<RecipeModel>,
                        isInnerList: Boolean = true) :
        RecyclerView.Adapter<RecipeListAdapter.ItemViewHolder>() {

    private var lastAnimatedPosition = -1

    private val itemLayout = if (isInnerList) {
        R.layout.item_recipe_inner_list
    } else {
        R.layout.item_recipe_expanded
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {

        val inflater = LayoutInflater.from(parent.context)

        val itemView = inflater.inflate(itemLayout,
                parent, false)

        return ItemViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {

        val (recipeId, title, imageUrl) = items[position]

        Glide.with(holder.itemView).setDefaultRequestOptions(RequestOptions().dontTransform()).load(imageUrl).into(holder.image)

        holder.title.text = title
        holder.image.contentDescription = title
        holder.card.setOnClickListener { activityCallback.onRecipeClick(recipeId) }

        setAnimation(holder.itemView, position)

    }

    private fun setAnimation(viewToAnimate: View, position: Int) {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastAnimatedPosition) {
            val animation = AnimationUtils.loadAnimation(viewToAnimate.context,
                    R.anim.item_animation_fall_down)
            viewToAnimate.startAnimation(animation)
            lastAnimatedPosition = position
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }


    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        @BindView(R.id.image)
        lateinit var image: ImageView

        @BindView(R.id.title)
        lateinit var title: TextView

        @BindView(R.id.card)
        lateinit var card: View

        init {
            ButterKnife.bind(this, itemView)
        }

    }
}
