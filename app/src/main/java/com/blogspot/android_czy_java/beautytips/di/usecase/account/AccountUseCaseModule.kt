package com.blogspot.android_czy_java.beautytips.di.usecase.account

import com.blogspot.android_czy_java.beautytips.di.repository.account.AccountRepositoryModule
import com.blogspot.android_czy_java.beautytips.repository.forViewModels.recipe.UserListRecipeRepository
import com.blogspot.android_czy_java.beautytips.repository.forViewModels.user.UserRepository
import com.blogspot.android_czy_java.beautytips.usecase.account.GetCurrentUserUseCase
import com.blogspot.android_czy_java.beautytips.usecase.account.login.LoginUseCase
import com.blogspot.android_czy_java.beautytips.usecase.account.userlist.CreateUserListRequestsUseCase
import com.blogspot.android_czy_java.beautytips.usecase.account.userlist.LoadRecipesFromUserListUseCase
import com.blogspot.android_czy_java.beautytips.usecase.account.userlist.UserListRecipeRequest
import com.blogspot.android_czy_java.beautytips.usecase.recipe.LoadRecipesUseCase
import dagger.Module
import dagger.Provides

@Module(includes = [
    AccountRepositoryModule::class
])
class AccountUseCaseModule {

    @Provides
    fun provideCreateUserListRequestsUseCase() = CreateUserListRequestsUseCase()

    @Provides
    fun provideLoadRecipesFromUserListUseCase(
            loadRecipesUseCase: LoadRecipesUseCase<UserListRecipeRequest>,
            recipeRepository: UserListRecipeRepository
    ) = LoadRecipesFromUserListUseCase(loadRecipesUseCase, recipeRepository)

    @Provides
    fun provideLoadRecipesUseCase(recipeRepository: UserListRecipeRepository)
            = LoadRecipesUseCase(recipeRepository)

    @Provides
    fun provideLoginUseCase() = LoginUseCase()

    @Provides
    fun provideGetCurrentUserUseCase(repository: UserRepository) = GetCurrentUserUseCase(repository)

}