package com.blogspot.android_czy_java.beautytips.appUtils.categories

import com.blogspot.android_czy_java.beautytips.appUtils.categories.labels.CategoryLabel
import com.blogspot.android_czy_java.beautytips.appUtils.categories.labels.SubcategoryLabel

enum class CategoryFace(override val subcategory: SubcategoryLabel) : CategoryInterface {

    SUBCATEGORY_ALL(SubcategoryLabel.SUBCATEGORY_ALL),
    SUBCATEGORY_CREAMS(SubcategoryLabel.SUBCATEGORY_CREAMS),
    SUBCATEGORY_MASKS(SubcategoryLabel.SUBCATEGORY_MASKS),
    SUBCATEGORY_SCRUBS(SubcategoryLabel.SUBCATEGORY_SCRUBS),
    SUBCATEGORY_TONICS(SubcategoryLabel.SUBCATEGORY_TONICS),
    SUBCATEGORY_FOR_LIPS(SubcategoryLabel.SUBCATEGORY_FOR_LIPS),
    SUBCATEGORY_FOR_EYES(SubcategoryLabel.SUBCATEGORY_FOR_EYES),
    SUBCATEGORY_OTHERS(SubcategoryLabel.SUBCATEGORY_OTHERS);

    override fun getCategoryLabel() = CategoryLabel.CATEGORY_FACE.label


    companion object {

        val subcategories = values().map { it.getSubcategoryLabel() }

    }

}

