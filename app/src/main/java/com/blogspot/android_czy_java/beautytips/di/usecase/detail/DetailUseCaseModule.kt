package com.blogspot.android_czy_java.beautytips.di.usecase.detail

import com.blogspot.android_czy_java.beautytips.di.repository.detail.DetailRepositoryModule
import com.blogspot.android_czy_java.beautytips.repository.forViewModels.detail.RecipeDetailRepository
import com.blogspot.android_czy_java.beautytips.usecase.detail.LoadBaseDetailUseCase
import dagger.Module
import dagger.Provides

@Module(includes = [
    DetailRepositoryModule::class
])
class DetailUseCaseModule {

    @Provides
    fun provideLoadBaseDetailUseCase(recipeDetailRepository: RecipeDetailRepository) =
            LoadBaseDetailUseCase(recipeDetailRepository)

}