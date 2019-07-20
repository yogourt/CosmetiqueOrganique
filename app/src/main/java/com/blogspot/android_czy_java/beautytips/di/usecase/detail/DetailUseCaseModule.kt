package com.blogspot.android_czy_java.beautytips.di.usecase.detail

import com.blogspot.android_czy_java.beautytips.di.repository.detail.DetailRepositoryModule
import com.blogspot.android_czy_java.beautytips.repository.forViewModels.detail.RecipeDetailRepository
import com.blogspot.android_czy_java.beautytips.usecase.detail.LoadBaseDetailUseCase
import com.blogspot.android_czy_java.beautytips.usecase.detail.LoadHeaderFragmentDataUseCase
import com.blogspot.android_czy_java.beautytips.usecase.detail.LoadIngredientsUseCase
import dagger.Module
import dagger.Provides

@Module(includes = [
    DetailRepositoryModule::class
])
class DetailUseCaseModule {

    @Provides
    fun provideLoadBaseDetailUseCase(recipeDetailRepository: RecipeDetailRepository) =
            LoadBaseDetailUseCase(recipeDetailRepository)

    @Provides
    fun provideLoadHeaderFragmentDataUseCase(recipeDetailRepository: RecipeDetailRepository) =
            LoadHeaderFragmentDataUseCase(recipeDetailRepository)

    @Provides
    fun provideLoadIngredientsUseCase(recipeDetailRepository: RecipeDetailRepository) =
        LoadIngredientsUseCase(recipeDetailRepository)

}