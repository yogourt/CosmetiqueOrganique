package com.blogspot.android_czy_java.beautytips.appUtils.categories.labels

import com.blogspot.android_czy_java.beautytips.appUtils.categories.*

enum class CategoryLabel(val label: String) {

    CATEGORY_ALL("All"),
    CATEGORY_HAIR("For hair"),
    CATEGORY_FACE("For face"),
    CATEGORY_BODY("For body");

    fun getCategory(): CategoryInterface {
        return when (this) {
            CATEGORY_ALL -> CategoryAll.SUBCATEGORY_ALL
            CATEGORY_HAIR -> CategoryHair.SUBCATEGORY_ALL
            CATEGORY_FACE -> CategoryFace.SUBCATEGORY_ALL
            CATEGORY_BODY -> CategoryBody.SUBCATEGORY_ALL
        }
    }


    companion object {
        fun get(category: CategoryLabel, subcategory: String = SubcategoryLabel.SUBCATEGORY_ALL.label):
                CategoryInterface {
            var categoryInterface: CategoryInterface = when (category) {
                CATEGORY_ALL -> CategoryAll.SUBCATEGORY_ALL
                CATEGORY_HAIR -> CategoryHair.SUBCATEGORY_ALL
                CATEGORY_FACE -> CategoryFace.SUBCATEGORY_ALL
                CATEGORY_BODY -> CategoryBody.SUBCATEGORY_ALL
            }
            categoryInterface = categoryInterface.let { category: CategoryInterface ->
                val subcategoryIndex = category.subcategories().indexOf(subcategory)
                if (subcategoryIndex != -1)
                    category.newSubcategory(subcategoryIndex)
                else category
            }
            return categoryInterface
        }

        fun get(category: String): CategoryLabel? {
            return when (category) {
                CATEGORY_ALL.label -> CATEGORY_ALL
                CATEGORY_BODY.label -> CATEGORY_BODY
                CATEGORY_HAIR.label -> CATEGORY_HAIR
                CATEGORY_FACE.label -> CATEGORY_FACE
                else -> null
            }
        }
    }


}