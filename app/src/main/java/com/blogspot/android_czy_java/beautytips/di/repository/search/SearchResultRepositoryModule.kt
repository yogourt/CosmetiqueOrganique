package com.blogspot.android_czy_java.beautytips.di.repository.search

import com.blogspot.android_czy_java.beautytips.di.repository.recipe.RecipeRepositoryModule
import com.blogspot.android_czy_java.beautytips.repository.forViewModels.recipe.RecipeRepository
import com.blogspot.android_czy_java.beautytips.repository.forViewModels.recipe.RecipeRepositoryInterface
import com.blogspot.android_czy_java.beautytips.repository.forViewModels.recipe.SearchResultRecipeRepository
import com.blogspot.android_czy_java.beautytips.usecase.recipe.RecipeRequest
import com.blogspot.android_czy_java.beautytips.usecase.search.SearchResultInnerRequest
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [
    RecipeRepositoryModule::class
])
class SearchResultRepositoryModule {

    @Provides
    @Singleton
    fun provideRecipeRepository(recipeRepository:
                                RecipeRepositoryInterface<RecipeRequest>):
            RecipeRepositoryInterface<SearchResultInnerRequest> = SearchResultRecipeRepository(recipeRepository)
}