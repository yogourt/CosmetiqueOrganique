package com.blogspot.android_czy_java.beautytips.di.usecase.recipe

import com.blogspot.android_czy_java.beautytips.repository.forViewModels.recipe.RecipeRepositoryInterface
import com.blogspot.android_czy_java.beautytips.di.repository.recipe.RecipeRepositoryModule
import com.blogspot.android_czy_java.beautytips.usecase.recipe.CreateRecipeRequestsUseCase
import com.blogspot.android_czy_java.beautytips.usecase.recipe.LoadListDataUseCase
import com.blogspot.android_czy_java.beautytips.usecase.recipe.LoadRecipesUseCase
import com.blogspot.android_czy_java.beautytips.usecase.recipe.RecipeRequest
import dagger.Module
import dagger.Provides

@Module(includes = [
    RecipeRepositoryModule::class
])
class RecipeUseCaseModule {

    @Provides
    fun provideLoadListDataUseCase(loadRecipesUseCase: LoadRecipesUseCase) = LoadListDataUseCase(loadRecipesUseCase)

    @Provides
    fun provideLoadRecipesUseCase(recipeRepository: RecipeRepositoryInterface<RecipeRequest>) = LoadRecipesUseCase(recipeRepository)

    @Provides
    fun provideCreateRecipeRequestsUseCase() = CreateRecipeRequestsUseCase()


}