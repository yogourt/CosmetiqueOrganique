package com.blogspot.android_czy_java.beautytips.view.detail

import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.blogspot.android_czy_java.beautytips.viewmodel.detail.DetailIngredient
import android.content.Context
import android.widget.TextView
import android.view.LayoutInflater
import com.blogspot.android_czy_java.beautytips.R


class DetailIngredientsAdapter(
        context: Context,
        private val ingredients: List<DetailIngredient>) : BaseAdapter() {

    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getView(position: Int, view: View?, viewGroup: ViewGroup?): View {

        val viewHolder: ViewHolder
        val convertView: View

        val ingredient = getItem(position)

        if (view == null) {
            convertView = inflater.inflate(if (ingredient.isOptional) {
                R.layout.item_detail_ingredient_optional
            } else {
                R.layout.item_detail_ingredient
            },
                    viewGroup,
                    false)

            viewHolder = ViewHolder(convertView)
            convertView.tag = viewHolder
        } else {
            convertView = view
            viewHolder = view.tag as ViewHolder
        }


        viewHolder.name.text = ingredient.name
        viewHolder.quantity.text = ingredient.quantity

        return convertView
    }

    override fun getItem(position: Int) = ingredients[position]

    override fun getItemId(position: Int) = position.toLong()

    override fun getCount() = ingredients.size

    override fun getItemViewType(position: Int): Int {
        return if (getItem(position).isOptional) {
            2
        } else {
            1
        }
    }


    private class ViewHolder(view: View) {
        val name: TextView = view.findViewById(R.id.name)
        val quantity: TextView = view.findViewById(R.id.quantity)
    }

}