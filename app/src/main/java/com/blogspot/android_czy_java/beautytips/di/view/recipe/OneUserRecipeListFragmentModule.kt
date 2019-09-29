package com.blogspot.android_czy_java.beautytips.di.view.recipe

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.blogspot.android_czy_java.beautytips.di.core.ViewModelKey
import com.blogspot.android_czy_java.beautytips.di.usecase.account.AccountUseCaseModule
import com.blogspot.android_czy_java.beautytips.di.view.detail.DetailActivityModule
import com.blogspot.android_czy_java.beautytips.usecase.account.userlist.UserListRecipeRequest
import com.blogspot.android_czy_java.beautytips.usecase.common.LoadRecipesUseCase
import com.blogspot.android_czy_java.beautytips.view.recipe.OneListFragment
import com.blogspot.android_czy_java.beautytips.viewmodel.detail.DetailActivityViewModel
import com.blogspot.android_czy_java.beautytips.viewmodel.recipe.OneListViewModel
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module(includes = [
    OneUserRecipeListFragmentModule.ProvideViewModel::class,
    AccountUseCaseModule::class
])
abstract class OneUserRecipeListFragmentModule {

    @ContributesAndroidInjector(modules = [
        InjectViewModel::class,
        DetailActivityModule.ProvideViewModel::class
    ])
    abstract fun bind(): OneListFragment.OneUserRecipeListFragment

    @Module
    class ProvideViewModel {

        @Provides
        @IntoMap
        @ViewModelKey(OneListViewModel.OneUserRecipeListViewModel::class)
        fun provideOneListViewModel(loadListDataUseCase: LoadRecipesUseCase<UserListRecipeRequest>): ViewModel =
                OneListViewModel.OneUserRecipeListViewModel(loadListDataUseCase)
    }

    @Module
    class InjectViewModel {

        @Provides
        fun provideOneListViewModel(
                factory: ViewModelProvider.Factory,
                target: OneListFragment.OneUserRecipeListFragment
        ): OneListViewModel.OneUserRecipeListViewModel =
                ViewModelProviders.of(target, factory).get(OneListViewModel.OneUserRecipeListViewModel::class.java)

        @Provides
        fun provideDetailActivityViewModel(
                factory: ViewModelProvider.Factory,
                target: OneListFragment.OneUserRecipeListFragment
        ): DetailActivityViewModel =
                ViewModelProviders.of(target.requireActivity(), factory).get(DetailActivityViewModel::class.java)
    }


}