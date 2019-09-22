package com.blogspot.android_czy_java.beautytips.appUtils.categories.labels

import com.blogspot.android_czy_java.beautytips.appUtils.categories.*

enum class CategoryLabel(val label: String) {

    CATEGORY_ALL("All"),
    CATEGORY_HAIR("For hair"),
    CATEGORY_FACE("For face"),
    CATEGORY_BODY("For body");

    fun getCategory(): CategoryInterface {
        return when(this) {
            CATEGORY_ALL -> CategoryAll.SUBCATEGORY_ALL
            CATEGORY_HAIR -> CategoryHair.SUBCATEGORY_ALL
            CATEGORY_FACE -> CategoryFace.SUBCATEGORY_ALL
            CATEGORY_BODY -> CategoryBody.SUBCATEGORY_ALL
        }
    }


}