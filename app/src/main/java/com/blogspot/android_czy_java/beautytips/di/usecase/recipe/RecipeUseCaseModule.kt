package com.blogspot.android_czy_java.beautytips.di.usecase.recipe

import com.blogspot.android_czy_java.beautytips.database.repository.forViewModels.recipe.RecipeRepository
import com.blogspot.android_czy_java.beautytips.database.repository.forViewModels.recipe.RecipeRepositoryInterface
import com.blogspot.android_czy_java.beautytips.di.repository.recipe.RecipeRepositoryModule
import com.blogspot.android_czy_java.beautytips.usecase.recipe.LoadRecipesUseCase
import com.blogspot.android_czy_java.beautytips.usecase.recipe.RecipeRequest
import dagger.Module
import dagger.Provides

@Module(includes = [
    RecipeRepositoryModule::class
])
class RecipeUseCaseModule {

    @Provides
    fun provideLoadRecipesUseCase(recipeRepository: RecipeRepositoryInterface<RecipeRequest>)
            = LoadRecipesUseCase(recipeRepository)

}