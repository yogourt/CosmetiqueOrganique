package com.blogspot.android_czy_java.beautytips.di.repository.detail

import com.blogspot.android_czy_java.beautytips.database.detail.DetailDao
import com.blogspot.android_czy_java.beautytips.database.recipe.RecipeDao
import com.blogspot.android_czy_java.beautytips.repository.forViewModels.detail.RecipeDetailRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DetailRepositoryModule {

    @Provides
    @Singleton
    fun provideRecipeDetailRepository(detailDao: DetailDao, recipeDao: RecipeDao)
            = RecipeDetailRepository(detailDao, recipeDao)
}