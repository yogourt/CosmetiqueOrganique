package com.blogspot.android_czy_java.beautytips.di.view.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.blogspot.android_czy_java.beautytips.di.core.ViewModelKey
import com.blogspot.android_czy_java.beautytips.di.usecase.comment.CommentUseCaseModule
import com.blogspot.android_czy_java.beautytips.di.usecase.detail.DetailUseCaseModule
import com.blogspot.android_czy_java.beautytips.usecase.comment.GetCommentNumberUseCase
import com.blogspot.android_czy_java.beautytips.view.detail.DetailBottomActionFragment
import com.blogspot.android_czy_java.beautytips.viewmodel.detail.DetailActivityViewModel
import com.blogspot.android_czy_java.beautytips.viewmodel.detail.DetailBottomActionViewModel
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module(includes = [
    DetailBottomActionModule.ProvideViewModel::class,
    CommentUseCaseModule::class
])
abstract class DetailBottomActionModule {

    @ContributesAndroidInjector(modules = [
        InjectViewModel::class,
        DetailActivityModule::class
    ])
    abstract fun bind(): DetailBottomActionFragment

    @Module
    class ProvideViewModel {
        @Provides
        @IntoMap
        @ViewModelKey(DetailBottomActionViewModel::class)
        fun provideDetailBottomActionViewModel(getCommentNumberUseCase: GetCommentNumberUseCase):
                ViewModel = DetailBottomActionViewModel(getCommentNumberUseCase)
    }

    @Module
    class InjectViewModel {

        @Provides
        fun provideDetailActivityViewModel(
                factory: ViewModelProvider.Factory,
                target: DetailBottomActionFragment
        ): DetailActivityViewModel =
                ViewModelProviders.of(target.requireActivity(), factory).get(DetailActivityViewModel::class.java)

        @Provides
        fun provideDetailBottomActionViewModel(
                factory: ViewModelProvider.Factory,
                target: DetailBottomActionFragment
        ): DetailBottomActionViewModel =
                ViewModelProviders.of(target, factory).get(DetailBottomActionViewModel::class.java)
    }

}