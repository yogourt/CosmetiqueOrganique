package com.blogspot.android_czy_java.beautytips.di.view.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.blogspot.android_czy_java.beautytips.view.detail.DetailActivity
import com.blogspot.android_czy_java.beautytips.di.core.ViewModelKey
import com.blogspot.android_czy_java.beautytips.di.usecase.detail.DetailUseCaseModule
import com.blogspot.android_czy_java.beautytips.di.view.recipe.MainActivityFragmentModule
import com.blogspot.android_czy_java.beautytips.usecase.detail.LoadHeaderFragmentDataUseCase
import com.blogspot.android_czy_java.beautytips.usecase.detail.LoadHeartDataForHeaderFragmentUseCase
import com.blogspot.android_czy_java.beautytips.view.comment.CommentFragment
import com.blogspot.android_czy_java.beautytips.viewmodel.account.AccountViewModel
import com.blogspot.android_czy_java.beautytips.viewmodel.detail.DetailActivityViewModel
import com.blogspot.android_czy_java.beautytips.viewmodel.detail.HeaderViewModel
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module(includes = [
    DetailActivityModule.ProvideViewModel::class
])
abstract class DetailActivityModule {

    @ContributesAndroidInjector(modules = [
        DetailActivityModule.InjectViewModel::class
    ])
    abstract fun bind(): DetailActivity

    @Module
    class ProvideViewModel {
        @Provides
        @IntoMap
        @ViewModelKey(DetailActivityViewModel::class)
        fun provideDetailActivityViewModel(): ViewModel = DetailActivityViewModel()

        @Provides
        @IntoMap
        @ViewModelKey(HeaderViewModel::class)
        fun provideHeaderViewModel(loadHeaderFragmentDataUseCase: LoadHeaderFragmentDataUseCase,
                                   loadHeartDataForHeaderFragmentUseCase: LoadHeartDataForHeaderFragmentUseCase):
                ViewModel = HeaderViewModel(loadHeaderFragmentDataUseCase, loadHeartDataForHeaderFragmentUseCase)

    }

    @Module
    class InjectViewModel {

        @Provides
        fun provideDetailActivityViewModel(
                factory: ViewModelProvider.Factory,
                target: DetailActivity
        ): DetailActivityViewModel =
                ViewModelProviders.of(target, factory).get(DetailActivityViewModel::class.java)

        @Provides
        fun provideHeaderViewModel(
                factory: ViewModelProvider.Factory,
                target: DetailActivity
        ): HeaderViewModel =
                ViewModelProviders.of(target, factory).get(HeaderViewModel::class.java)

        @Provides
        fun provideAccountViewModel(
                factory: ViewModelProvider.Factory,
                target: DetailActivity
        ): AccountViewModel =
                ViewModelProviders.of(target, factory).get(AccountViewModel::class.java)

    }
}



