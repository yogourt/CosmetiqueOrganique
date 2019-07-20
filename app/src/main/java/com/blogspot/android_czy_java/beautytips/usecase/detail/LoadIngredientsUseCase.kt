package com.blogspot.android_czy_java.beautytips.usecase.detail

import com.blogspot.android_czy_java.beautytips.repository.forViewModels.detail.RecipeDetailRepositoryInterface
import com.blogspot.android_czy_java.beautytips.usecase.UseCaseInterface
import com.blogspot.android_czy_java.beautytips.view.listView.exception.RecipeIdNotFoundException
import com.blogspot.android_czy_java.beautytips.viewmodel.detail.DetailIngredient
import com.blogspot.android_czy_java.beautytips.viewmodel.detail.IngredientsFragmentData
import io.reactivex.Single

class LoadIngredientsUseCase(private val recipeDetailRepositoryInterface: RecipeDetailRepositoryInterface) :
        UseCaseInterface<Long, IngredientsFragmentData> {

    override fun execute(request: Long): Single<IngredientsFragmentData> {
        return Single.create {
            try {
                val ingredients = mutableListOf<DetailIngredient>()
                ingredients.addAll(getNecessaryIngredients(request))
                getOptionalIngredients(request)?.let {
                    ingredients.addAll(it)
                }
                it.onSuccess(IngredientsFragmentData(ingredients))

            } catch (exception: RecipeIdNotFoundException) {
                it.onError(exception)
            }
        }
    }

    private fun getNecessaryIngredients(request: Long): List<DetailIngredient> {
        val necessaryIngredients = recipeDetailRepositoryInterface
                .getIngredientsWithQuantity(request)
        return DetailIngredientsConverter(necessaryIngredients, false)
                .convert()
    }

    private fun getOptionalIngredients(request: Long): List<DetailIngredient>? {
        val optionalIngredients = recipeDetailRepositoryInterface
                .getOptionalIngredientsWithQuantity(request)
        return if(optionalIngredients.isEmpty()) {
            null
        } else {
            DetailIngredientsConverter(optionalIngredients, true)
                    .convert()
        }
    }
}
