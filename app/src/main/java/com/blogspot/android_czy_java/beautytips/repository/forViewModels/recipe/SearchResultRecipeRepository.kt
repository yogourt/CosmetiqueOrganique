package com.blogspot.android_czy_java.beautytips.repository.forViewModels.recipe

import com.blogspot.android_czy_java.beautytips.appUtils.categories.CategoryAll
import com.blogspot.android_czy_java.beautytips.appUtils.categories.labels.SubcategoryLabel
import com.blogspot.android_czy_java.beautytips.appUtils.orders.Order
import com.blogspot.android_czy_java.beautytips.database.recipe.RecipeDao
import com.blogspot.android_czy_java.beautytips.database.recipe.RecipeModel
import com.blogspot.android_czy_java.beautytips.usecase.recipe.RecipeRequest
import com.blogspot.android_czy_java.beautytips.usecase.search.SearchResultInnerRequest
import io.reactivex.Single

class SearchResultRecipeRepository(private val recipeRepository:
                                   RecipeRepositoryInterface<RecipeRequest>) :
        RecipeRepositoryInterface<SearchResultInnerRequest>(recipeRepository.recipeDao) {

    override fun getRecipes(request: SearchResultInnerRequest): Single<List<RecipeModel>> {

        return recipeRepository.getRecipes(
                RecipeRequest(request.category,
                        request.order))
                .map {
                    it.filter { recipe ->
                        recipe.title.contains(request.title.trim()) ||
                                recipe.tags.split(",")
                                        .map { tag -> tag.trim() }
                                        .any { tag ->
                                            request.keywords.split(" ")
                                                    .map { keyword -> keyword.trim() }
                                                    .contains(tag)
                                        }
                    }
                }


        /* val recipes =
                 if (request.category.isSubcategoryAll()) {
                     if (request.category is CategoryAll) {
                         if (request.order == Order.NEW) {
                             recipeDao.getAllRecipes()
                         } else {
                             recipeDao.getAllRecipesOrderByPopularity()
                         }
                     }
                     if (request.order == Order.NEW) {
                         recipeDao.getRecipesByCategory(
                                 request.category.getCategoryLabel()
                         )
                     } else {
                         recipeDao.getRecipesByCategoryOrderByPopularity(
                                 request.category.getCategoryLabel())
                     }
                 } else {
                     if (request.order == Order.NEW) {
                         recipeDao.getRecipesByCategoryAndSubcategory(
                                 request.category.getCategoryLabel(),
                                 request.category.getSubcategoryLabel()
                         )
                     } else {
                         recipeDao.getRecipesByCategoryAndSubcategoryOrderByPopularity(
                                 request.category.getCategoryLabel(),
                                 request.category.getSubcategoryLabel()
                         )
                     }
                 }*/
    }
}
