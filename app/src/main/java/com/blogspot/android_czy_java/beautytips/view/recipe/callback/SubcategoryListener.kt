package com.blogspot.android_czy_java.beautytips.view.recipe.callback

import com.adroitandroid.chipcloud.ChipListener
import com.blogspot.android_czy_java.beautytips.appUtils.categories.CategoryInterface

class SubcategoryListener(
        private val category: CategoryInterface,
        private val onSubcategoryClicked: (category: String, subcategory: String) -> Unit): ChipListener {

    override fun chipDeselected(index: Int) {
    }

    override fun chipSelected(index: Int) {
        onSubcategoryClicked(category.getCategoryLabel(), category.subcategories()[index])
    }

}