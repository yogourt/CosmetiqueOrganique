package com.blogspot.android_czy_java.beautytips.di.usecase.account

import com.blogspot.android_czy_java.beautytips.di.repository.account.UserListRepositoryModule
import com.blogspot.android_czy_java.beautytips.repository.forViewModels.recipe.UserListRecipeRepository
import com.blogspot.android_czy_java.beautytips.usecase.account.CreateUserListRequestsUseCase
import com.blogspot.android_czy_java.beautytips.usecase.account.LoadRecipesFromUserListUseCase
import com.blogspot.android_czy_java.beautytips.usecase.account.UserListRecipeRequest
import com.blogspot.android_czy_java.beautytips.usecase.recipe.LoadRecipesUseCase
import dagger.Module
import dagger.Provides

@Module(includes = [
    UserListRepositoryModule::class
])
class AccountUseCaseModule {

    @Provides
    fun provideCreateUserListRequestsUseCase() = CreateUserListRequestsUseCase()

    @Provides
    fun provideLoadRecipesFromUserListUseCase(
            loadRecipesUseCase: LoadRecipesUseCase<UserListRecipeRequest>
    ) = LoadRecipesFromUserListUseCase(loadRecipesUseCase)

    @Provides
    fun provideLoadRecipesUseCase(recipeRepository: UserListRecipeRepository)
            = LoadRecipesUseCase(recipeRepository)

}