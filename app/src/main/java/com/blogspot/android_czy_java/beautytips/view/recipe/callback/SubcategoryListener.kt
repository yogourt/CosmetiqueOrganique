package com.blogspot.android_czy_java.beautytips.view.recipe.callback

import com.adroitandroid.chipcloud.ChipListener
import com.blogspot.android_czy_java.beautytips.appUtils.categories.CategoryInterface
import com.blogspot.android_czy_java.beautytips.appUtils.categories.labels.CategoryLabel

class SubcategoryListener(
        private val category: CategoryInterface,
        private val onSubcategoryClicked: (category: CategoryInterface) -> Unit): ChipListener {

    override fun chipDeselected(index: Int) {
    }

    override fun chipSelected(index: Int) {
        val categoryLabel = CategoryLabel.get(category.getCategoryLabel()) ?: return
        val category = CategoryLabel.get(categoryLabel,category.subcategories()[index])
        onSubcategoryClicked(category)
    }

}