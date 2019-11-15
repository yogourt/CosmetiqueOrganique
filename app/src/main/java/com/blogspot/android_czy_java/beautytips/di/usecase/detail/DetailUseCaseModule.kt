package com.blogspot.android_czy_java.beautytips.di.usecase.detail

import com.blogspot.android_czy_java.beautytips.di.repository.detail.DetailRepositoryModule
import com.blogspot.android_czy_java.beautytips.di.usecase.account.AccountUseCaseModule
import com.blogspot.android_czy_java.beautytips.repository.forViewModels.detail.RecipeDetailRepository
import com.blogspot.android_czy_java.beautytips.repository.forViewModels.recipe.UserListRecipeRepository
import com.blogspot.android_czy_java.beautytips.usecase.account.GetCurrentUserUseCase
import com.blogspot.android_czy_java.beautytips.usecase.account.UpdateUserDataInFirebaseUseCase
import com.blogspot.android_czy_java.beautytips.usecase.comment.GetCommentNumberUseCase
import com.blogspot.android_czy_java.beautytips.usecase.detail.*
import dagger.Module
import dagger.Provides

@Module(includes = [
    DetailRepositoryModule::class,
    AccountUseCaseModule::class
])
class DetailUseCaseModule {

    @Provides
    fun provideLoadBaseDetailUseCase(recipeDetailRepository: RecipeDetailRepository) =
            LoadDescriptionFragmentDataUseCase(recipeDetailRepository)

    @Provides
    fun provideLoadHeaderFragmentDataUseCase(recipeDetailRepository: RecipeDetailRepository) =
            LoadHeaderFragmentDataUseCase(recipeDetailRepository)

    @Provides
    fun provideLoadHeartDataForHeaderFragmentUseCase(recipeDetailRepository: RecipeDetailRepository,
                                                     userListRecipeRepository: UserListRecipeRepository,
                                                     getCurrentUserUseCase: GetCurrentUserUseCase,
                                                     updateFavNumInFirebaseUseCase: UpdateFavNumInFirebaseUseCase) =
            LoadHeartDataForHeaderFragmentUseCase(
                    recipeDetailRepository,
                    userListRecipeRepository,
                    getCurrentUserUseCase,
                    updateFavNumInFirebaseUseCase
            )

    @Provides
    fun provideLoadIngredientsUseCase(recipeDetailRepository: RecipeDetailRepository) =
            LoadIngredientsUseCase(recipeDetailRepository)

    @Provides
    fun provideUpdateFavNumInFirebaseUseCase() = UpdateFavNumInFirebaseUseCase()

    @Provides
    fun provideAddToUserListUseCase(userListRecipeRepository: UserListRecipeRepository,
                                    currentUserUseCase: GetCurrentUserUseCase) =
            AddToUserListUseCase(userListRecipeRepository, currentUserUseCase)

    @Provides
    fun provideCreateUserListUseCase(getCurrentUserUseCase: GetCurrentUserUseCase,
                                     userListRecipeRepository: UserListRecipeRepository) =
            CreateUserListUseCase(userListRecipeRepository, getCurrentUserUseCase)

}