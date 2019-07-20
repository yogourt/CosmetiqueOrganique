package com.blogspot.android_czy_java.beautytips.repository.forViewModels.recipe

import com.blogspot.android_czy_java.beautytips.appUtils.categories.CategoryAll
import com.blogspot.android_czy_java.beautytips.appUtils.categories.CategoryInterface
import com.blogspot.android_czy_java.beautytips.appUtils.orders.Order
import com.blogspot.android_czy_java.beautytips.database.recipe.RecipeDao
import com.blogspot.android_czy_java.beautytips.database.recipe.RecipeModel
import com.blogspot.android_czy_java.beautytips.repository.FirebaseToRoom
import com.blogspot.android_czy_java.beautytips.usecase.recipe.RecipeRequest
import io.reactivex.Single
import io.reactivex.SingleEmitter


class RecipeRepository(private val recipeDao: RecipeDao, private val firebaseToRoom: FirebaseToRoom) :
        RecipeRepositoryInterface<RecipeRequest> {

    override fun getRecipes(request: RecipeRequest): Single<List<RecipeModel>> {
        return when(request.order) {
            Order.NEW -> getByDate(request.category)
            Order.POPULARITY -> getByPopularity(request.category)
        }
    }

    private fun getByDate(category: CategoryInterface): Single<List<RecipeModel>> {

        return Single.create { emitter ->


            //TODO: change
           // if (recipeDao.getAllRecipesIds().isEmpty()) {
            if (true) {

                firebaseToRoom.observeFirebaseAndSaveToRoom().subscribe({
                    run {
                        emitResultByDate(category, emitter)
                    }
                },
                        { error ->
                            emitter.onError(error)
                        }

                )
            } else {
                emitResultByDate(category, emitter)
            }
        }
    }

    private fun emitResultByDate(category: CategoryInterface, emitter: SingleEmitter<List<RecipeModel>>) {
        when {
            category == CategoryAll.SUBCATEGORY_ALL -> emitter.onSuccess(recipeDao.getAllRecipes())

            category.isSubcategoryAll() -> emitter.onSuccess(
                    recipeDao.getRecipesByCategory(category.getCategoryLabel()))

            else -> emitter.onSuccess(recipeDao.getRecipesByCategoryAndSubcategory(
                    category.getCategoryLabel(), category.getSubcategoryLabel()))
        }
    }

    private fun getByPopularity(category: CategoryInterface): Single<List<RecipeModel>> {

        return Single.create { emitter ->

            if (recipeDao.getAllRecipesIds().isEmpty()) {

                firebaseToRoom.observeFirebaseAndSaveToRoom().subscribe({
                    run {
                        emitResultByPopularity(category, emitter)
                    }
                },
                        { error ->
                            emitter.onError(error)
                        }

                )
            } else {
                emitResultByPopularity(category, emitter)
            }
        }
    }

    private fun emitResultByPopularity(category: CategoryInterface, emitter: SingleEmitter<List<RecipeModel>>) {
        when {
            category == CategoryAll.SUBCATEGORY_ALL -> emitter.onSuccess(recipeDao.getAllRecipesOrderByPopularity())

            category.isSubcategoryAll() -> emitter.onSuccess(
                    recipeDao.getRecipesByCategoryOrderByPopularity(category.getCategoryLabel()))

            else -> emitter.onSuccess(recipeDao.getRecipesByCategoryAndSubcategoryOrderByPopularity(
                    category.getCategoryLabel(), category.getSubcategoryLabel()))
        }
    }


}