package com.blogspot.android_czy_java.beautytips.appUtils.categories

import com.blogspot.android_czy_java.beautytips.appUtils.categories.labels.CategoryLabel
import com.blogspot.android_czy_java.beautytips.appUtils.categories.labels.SubcategoryLabel


enum class CategoryAll(override val subcategory: SubcategoryLabel) : CategoryInterface {

    SUBCATEGORY_ALL(SubcategoryLabel.SUBCATEGORY_ALL);

    override fun getCategoryLabel() = CategoryLabel.CATEGORY_ALL.label


    companion object {

        val subcategories = values().map { it.getSubcategoryLabel() }

    }

}

