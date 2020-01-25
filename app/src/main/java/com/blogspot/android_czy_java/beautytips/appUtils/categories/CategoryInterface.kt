package com.blogspot.android_czy_java.beautytips.appUtils.categories

import com.blogspot.android_czy_java.beautytips.appUtils.categories.labels.SubcategoryLabel

interface CategoryInterface {

    val subcategory: SubcategoryLabel

    fun subcategories(): List<String>

    fun getCategoryLabel(): String
    fun getSubcategoryLabel() = subcategory.label
    fun isSubcategoryAll() = subcategory == SubcategoryLabel.SUBCATEGORY_ALL
    fun newSubcategory(id: Int): CategoryInterface

    fun getListTitle(): String {
        return if (isSubcategoryAll()) {
            getCategoryLabel()
        } else {
            "${getCategoryLabel()} > ${getSubcategoryLabel()}"
        }
    }

}