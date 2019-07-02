package com.blogspot.android_czy_java.beautytips.appUtils.categories

import com.blogspot.android_czy_java.beautytips.appUtils.categories.labels.CategoryLabel
import com.blogspot.android_czy_java.beautytips.appUtils.categories.labels.SubcategoryLabel

interface CategoryFavourites {

    companion object {

        val subcategories = CategoryFace.Subcategory.values().map { it.label }

    }

    enum class Subcategory(private val label: SubcategoryLabel): CategoryInterface {

        SUBCATEGORY_ALL(SubcategoryLabel.SUBCATEGORY_ALL);

        override fun getCategory() = CategoryLabel.CATEGORY_FAVOURITES

        override fun getSubcategory() = label
    }
}