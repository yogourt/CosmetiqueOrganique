package com.blogspot.android_czy_java.beautytips.di.repository.search

import com.blogspot.android_czy_java.beautytips.database.recipe.RecipeDao
import com.blogspot.android_czy_java.beautytips.repository.forViewModels.recipe.RecipeRepositoryInterface
import com.blogspot.android_czy_java.beautytips.repository.forViewModels.recipe.SearchResultRecipeRepository
import com.blogspot.android_czy_java.beautytips.usecase.search.SearchResultRequest
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class SearchResultRepositoryModule {

    @Provides
    @Singleton
    fun provideRecipeRepository(recipeDao: RecipeDao):
            RecipeRepositoryInterface<SearchResultRequest> = SearchResultRecipeRepository(recipeDao)
}