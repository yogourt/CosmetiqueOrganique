package com.blogspot.android_czy_java.beautytips.appUtils.categories

import com.blogspot.android_czy_java.beautytips.appUtils.categories.labels.CategoryLabel
import com.blogspot.android_czy_java.beautytips.appUtils.categories.labels.SubcategoryLabel

enum class CategoryHair(override val subcategory: SubcategoryLabel) : CategoryInterface {

    SUBCATEGORY_ALL(SubcategoryLabel.SUBCATEGORY_ALL),
    SUBCATEGORY_OTHERS(SubcategoryLabel.SUBCATEGORY_OTHERS),
    SUBCATEGORY_SHAMPOOS(SubcategoryLabel.SUBCATEGORY_SHAMPOOS),
    SUBCATEGORY_OILS(SubcategoryLabel.SUBCATEGORY_OILS),
    SUBCATEGORY_STYLING(SubcategoryLabel.SUBCATEGORY_STYLING),
    SUBCATEGORY_MASKS(SubcategoryLabel.SUBCATEGORY_MASKS);

    override fun getCategoryLabel() = CategoryLabel.CATEGORY_HAIR.label

    companion object {

        val subcategories = values().map { it.getSubcategoryLabel() }

    }

}

