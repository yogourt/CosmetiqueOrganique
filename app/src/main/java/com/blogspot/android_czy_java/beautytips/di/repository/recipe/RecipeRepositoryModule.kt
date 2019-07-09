package com.blogspot.android_czy_java.beautytips.di.repository.recipe

import android.content.Context
import com.blogspot.android_czy_java.beautytips.database.AppDatabase
import com.blogspot.android_czy_java.beautytips.database.recipe.RecipeDao
import com.blogspot.android_czy_java.beautytips.database.repository.FirebaseToRoom
import com.blogspot.android_czy_java.beautytips.database.repository.forViewModels.recipe.RecipeRepository
import com.blogspot.android_czy_java.beautytips.database.repository.forViewModels.recipe.RecipeRepositoryInterface
import com.blogspot.android_czy_java.beautytips.usecase.recipe.RecipeRequest
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RecipeRepositoryModule {

    @Provides
    @Singleton
    fun provideRecipeRepository(recipeDao: RecipeDao, firebaseToRoom: FirebaseToRoom):
            RecipeRepositoryInterface<RecipeRequest> = RecipeRepository(recipeDao, firebaseToRoom)
}