package com.blogspot.android_czy_java.beautytips.di.usecase.search

import com.blogspot.android_czy_java.beautytips.di.repository.search.SearchResultRepositoryModule
import com.blogspot.android_czy_java.beautytips.repository.forViewModels.recipe.RecipeRepositoryInterface
import com.blogspot.android_czy_java.beautytips.usecase.common.LoadRecipesUseCase
import com.blogspot.android_czy_java.beautytips.usecase.search.CreateSearchResultRequestsUseCase
import com.blogspot.android_czy_java.beautytips.usecase.search.LoadSearchResultListDataUseCase
import com.blogspot.android_czy_java.beautytips.usecase.search.SearchResultRequest
import dagger.Module
import dagger.Provides

@Module(includes = [
    SearchResultRepositoryModule::class
])
class SearchUseCaseModule {

    @Provides
    fun provideLoadSearchResultListDataUseCase(loadRecipesUseCase: LoadRecipesUseCase<SearchResultRequest>,
                                   recipeRepository: RecipeRepositoryInterface<SearchResultRequest>)
            = LoadSearchResultListDataUseCase(loadRecipesUseCase, recipeRepository)

    @Provides
    fun provideLoadRecipesUseCase(recipeRepository: RecipeRepositoryInterface<SearchResultRequest>)
            = LoadRecipesUseCase(recipeRepository)

    @Provides
    fun provideCreateRecipeRequestsUseCase() = CreateSearchResultRequestsUseCase()
}