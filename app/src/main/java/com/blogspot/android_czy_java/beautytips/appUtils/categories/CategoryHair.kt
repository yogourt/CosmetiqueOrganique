package com.blogspot.android_czy_java.beautytips.appUtils.categories

import com.blogspot.android_czy_java.beautytips.appUtils.categories.labels.CategoryLabel
import com.blogspot.android_czy_java.beautytips.appUtils.categories.labels.SubcategoryLabel

interface CategoryHair {

    companion object {

        val subcategories = CategoryFace.Subcategory.values().map { it.label }

    }

    enum class Subcategory(private val label: SubcategoryLabel): CategoryInterface {

        SUBCATEGORY_ALL(SubcategoryLabel.SUBCATEGORY_ALL),
        SUBCATEGORY_OTHERS(SubcategoryLabel.SUBCATEGORY_OTHERS),
        SUBCATEGORY_SHAMPOOS(SubcategoryLabel.SUBCATEGORY_SHAMPOOS),
        SUBCATEGORY_OILS(SubcategoryLabel.SUBCATEGORY_OILS),
        SUBCATEGORY_STYLING(SubcategoryLabel.SUBCATEGORY_STYLING),
        SUBCATEGORY_MASKS(SubcategoryLabel.SUBCATEGORY_MASKS);

        override fun getCategory() = CategoryLabel.CATEGORY_HAIR

        override fun getSubcategory() = label
    }

}