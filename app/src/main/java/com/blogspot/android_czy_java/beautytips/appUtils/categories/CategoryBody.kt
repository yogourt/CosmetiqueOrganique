package com.blogspot.android_czy_java.beautytips.appUtils.categories

import com.blogspot.android_czy_java.beautytips.appUtils.categories.labels.CategoryLabel
import com.blogspot.android_czy_java.beautytips.appUtils.categories.labels.SubcategoryLabel

enum class CategoryBody(override val subcategory: SubcategoryLabel) : CategoryInterface {

    SUBCATEGORY_ALL(SubcategoryLabel.SUBCATEGORY_ALL),
    SUBCATEGORY_OTHERS(SubcategoryLabel.SUBCATEGORY_OTHERS),
    SUBCATEGORY_LOTIONS(SubcategoryLabel.SUBCATEGORY_LOTIONS),
    SUBCATEGORY_SCRUBS(SubcategoryLabel.SUBCATEGORY_SCRUBS),
    SUBCATEGORY_BATH_SALTS(SubcategoryLabel.SUBCATEGORY_BATH_SALTS),
    SUBCATEGORY_SHOWER_GELS(SubcategoryLabel.SUBCATEGORY_SHOWER_GELS),
    SUBCATEGORY_FOR_HANDS(SubcategoryLabel.SUBCATEGORY_FOR_HANDS),
    SUBCATEGORY_FOR_FEET(SubcategoryLabel.SUBCATEGORY_FOR_FEET);

    override fun getCategoryLabel() = CategoryLabel.CATEGORY_BODY.label


    companion object {

        val subcategories = values().map { it.getSubcategoryLabel() }

    }

}

