package com.blogspot.android_czy_java.beautytips.di.usecase.search

import com.blogspot.android_czy_java.beautytips.di.repository.search.SearchResultRepositoryModule
import com.blogspot.android_czy_java.beautytips.repository.forViewModels.recipe.RecipeRepositoryInterface
import com.blogspot.android_czy_java.beautytips.usecase.common.LoadRecipesUseCase
import com.blogspot.android_czy_java.beautytips.usecase.common.SearchResultRequest
import dagger.Module
import dagger.Provides

@Module(includes = [
    SearchResultRepositoryModule::class
])
class SearchUseCaseModule {

    @Provides
    fun provideLoadRecipesUseCase(recipeRepository: RecipeRepositoryInterface<SearchResultRequest>)
            = LoadRecipesUseCase(recipeRepository)
}