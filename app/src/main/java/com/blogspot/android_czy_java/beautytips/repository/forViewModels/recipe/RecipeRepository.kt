package com.blogspot.android_czy_java.beautytips.repository.forViewModels.recipe

import com.blogspot.android_czy_java.beautytips.appUtils.categories.CategoryAll
import com.blogspot.android_czy_java.beautytips.appUtils.categories.CategoryInterface
import com.blogspot.android_czy_java.beautytips.appUtils.orders.Order
import com.blogspot.android_czy_java.beautytips.database.recipe.RecipeDao
import com.blogspot.android_czy_java.beautytips.exception.common.DatabaseIsEmptyException
import com.blogspot.android_czy_java.beautytips.usecase.common.RecipeRequest
import com.blogspot.android_czy_java.beautytips.viewmodel.recipe.OneListData
import io.reactivex.Single
import io.reactivex.SingleEmitter


class RecipeRepository(override val recipeDao: RecipeDao) :
        RecipeRepositoryInterface<RecipeRequest>(recipeDao) {

    override fun getRecipes(request: RecipeRequest): Single<OneListData> {
        return when (request.order) {
            Order.NEW -> getByDate(request.category)
            Order.POPULARITY -> getByPopularity(request.category)
        }
    }

    private fun getByDate(category: CategoryInterface): Single<OneListData> {

        return Single.create { emitter ->
            if (recipeDao.getAllRecipesIds().isEmpty()) {
                emitter.onError(DatabaseIsEmptyException())
            } else {
                emitResultByDate(category, emitter)
            }
        }
    }

    private fun emitResultByDate(category: CategoryInterface, emitter: SingleEmitter<OneListData>) {
        val list = when {
            category == CategoryAll.SUBCATEGORY_ALL -> recipeDao.getAllRecipes()

            category.isSubcategoryAll() ->
                recipeDao.getRecipesByCategory(category.getCategoryLabel())

            else -> recipeDao.getRecipesByCategoryAndSubcategory(
                    category.getCategoryLabel(), category.getSubcategoryLabel())
        }
        emitter.onSuccess(
                OneListData(list,
                        createListTitle(category, Order.NEW),
                        category
                )
        )
    }

    private fun getByPopularity(category: CategoryInterface): Single<OneListData> {

        return Single.create { emitter ->

            if (recipeDao.getAllRecipesIds().isEmpty()) {
                emitter.onError(DatabaseIsEmptyException())
            } else {
                emitResultByPopularity(category, emitter)
            }
        }
    }

    private fun emitResultByPopularity(category: CategoryInterface, emitter: SingleEmitter<OneListData>) {
        val list = when {
            category == CategoryAll.SUBCATEGORY_ALL -> recipeDao.getAllRecipesOrderByPopularity()

            category.isSubcategoryAll() ->
                recipeDao.getRecipesByCategoryOrderByPopularity(category.getCategoryLabel())

            else -> recipeDao.getRecipesByCategoryAndSubcategoryOrderByPopularity(
                    category.getCategoryLabel(), category.getSubcategoryLabel())
        }

        emitter.onSuccess(
                OneListData(list,
                        createListTitle(category, Order.POPULARITY),
                        category
                )
        )
    }

    private fun createListTitle(category: CategoryInterface, order: Order): String {
        return when {
            category is CategoryAll -> order.label
            category.isSubcategoryAll() -> "${order.label} ${category.getListTitle()}"
            else -> category.getListTitle()
        }
    }


}