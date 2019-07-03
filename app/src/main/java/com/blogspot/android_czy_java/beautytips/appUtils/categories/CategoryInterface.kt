package com.blogspot.android_czy_java.beautytips.appUtils.categories

import com.blogspot.android_czy_java.beautytips.appUtils.categories.labels.SubcategoryLabel

interface CategoryInterface {

    val subcategory: SubcategoryLabel

    fun getCategoryLabel(): String
    fun getSubcategoryLabel() = subcategory.label
    fun isSubcategoryAll() = subcategory == SubcategoryLabel.SUBCATEGORY_ALL
}