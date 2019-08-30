package com.blogspot.android_czy_java.beautytips.di.repository.account

import com.blogspot.android_czy_java.beautytips.database.recipe.RecipeDao
import com.blogspot.android_czy_java.beautytips.database.user.UserDao
import com.blogspot.android_czy_java.beautytips.di.database.DatabaseModule
import com.blogspot.android_czy_java.beautytips.repository.forViewModels.recipe.UserListRecipeRepository
import com.blogspot.android_czy_java.beautytips.repository.forViewModels.user.UserRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [
    DatabaseModule::class
])
class AccountRepositoryModule {

    @Provides
    @Singleton
    fun provideUserListRecipeRepository(recipeDao: RecipeDao) = UserListRecipeRepository(recipeDao)

    @Provides
    @Singleton
    fun provideUserRepository(userDao: UserDao) = UserRepository(userDao)
}