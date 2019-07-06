package com.blogspot.android_czy_java.beautytips.database.repository.forViewModels.recipe

import com.blogspot.android_czy_java.beautytips.appUtils.categories.CategoryAll
import com.blogspot.android_czy_java.beautytips.appUtils.categories.CategoryInterface
import com.blogspot.android_czy_java.beautytips.database.AppDatabase
import com.blogspot.android_czy_java.beautytips.database.recipe.RecipeDao
import com.blogspot.android_czy_java.beautytips.database.recipe.RecipeMappedModel
import com.blogspot.android_czy_java.beautytips.database.repository.FirebaseToRoom
import io.reactivex.Single
import io.reactivex.SingleEmitter


class RecipeRepository(private val recipeDao: RecipeDao, private val database: AppDatabase) :
        RecipeRepositoryInterface {

    override fun getByDate(category: CategoryInterface): Single<List<RecipeMappedModel>> {

        return Single.create { emitter ->

            if (recipeDao.getAllRecipesIds().isEmpty()) {

                FirebaseToRoom(database).observeFirebaseAndSaveToRoom().subscribe({
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

    private fun emitResultByDate(category: CategoryInterface, emitter: SingleEmitter<List<RecipeMappedModel>>) {
        when {
            category == CategoryAll.SUBCATEGORY_ALL -> emitter.onSuccess(recipeDao.getAllRecipes())

            category.isSubcategoryAll() -> emitter.onSuccess(
                    recipeDao.getRecipesByCategory(category.getCategoryLabel()))

            else -> emitter.onSuccess(recipeDao.getRecipesByCategoryAndSubcategory(
                    category.getCategoryLabel(), category.getSubcategoryLabel()))
        }
    }

    override fun getByPopularity(category: CategoryInterface): Single<List<RecipeMappedModel>> {

        return Single.create { emitter ->

            if (recipeDao.getAllRecipesIds().isEmpty()) {

                FirebaseToRoom(database).observeFirebaseAndSaveToRoom().subscribe({
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

    private fun emitResultByPopularity(category: CategoryInterface, emitter: SingleEmitter<List<RecipeMappedModel>>) {
        when {
            category == CategoryAll.SUBCATEGORY_ALL -> emitter.onSuccess(recipeDao.getAllRecipesOrderByPopularity())

            category.isSubcategoryAll() -> emitter.onSuccess(
                    recipeDao.getRecipesByCategoryOrderByPopularity(category.getCategoryLabel()))

            else -> emitter.onSuccess(recipeDao.getRecipesByCategoryAndSubcategoryOrderByPopularity(
                    category.getCategoryLabel(), category.getSubcategoryLabel()))
        }
    }


}