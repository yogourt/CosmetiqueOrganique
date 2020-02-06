package com.blogspot.android_czy_java.beautytips.view.recipe.callback

import com.adroitandroid.chipcloud.ChipListener
import com.blogspot.android_czy_java.beautytips.usecase.common.OneListRequest

class SubcategoryListener(
        private var request: OneListRequest,
        private val onSubcategoryClicked: (request: OneListRequest) -> Unit) : ChipListener {

    override fun chipDeselected(index: Int) {
    }

    override fun chipSelected(index: Int) {
        onSubcategoryClicked(createNewRequest(index))
    }

    private fun createNewRequest(index: Int) =
            request.newCategory(request.category.newSubcategory(index))

}