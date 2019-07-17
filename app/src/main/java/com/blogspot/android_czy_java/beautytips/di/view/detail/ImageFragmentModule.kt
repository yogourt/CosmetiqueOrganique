package com.blogspot.android_czy_java.beautytips.di.view.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.blogspot.android_czy_java.beautytips.di.core.ViewModelKey
import com.blogspot.android_czy_java.beautytips.view.detail.DetailImageFragment
import com.blogspot.android_czy_java.beautytips.viewmodel.detail.ImageFragmentViewModel
import com.blogspot.android_czy_java.beautytips.usecase.detail.LoadImageFragmentDataUseCase
import com.blogspot.android_czy_java.beautytips.viewmodel.detail.DetailActivityViewModel
import com.blogspot.android_czy_java.beautytips.viewmodel.recipe.RecipeViewModel
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class ImageFragmentModule {

    @ContributesAndroidInjector(modules = [
        InjectViewModel::class,
        DetailActivityModule::class
    ])
    abstract fun bind(): DetailImageFragment

    @Module
    class ProvideViewModel {

        @Provides
        @IntoMap
        @ViewModelKey(RecipeViewModel::class)
        fun provideImageFragmentViewModel(loadImageFragmentDataUseCase: LoadImageFragmentDataUseCase): ViewModel =
                ImageFragmentViewModel(loadImageFragmentDataUseCase)
    }

    @Module
    class InjectViewModel {

        @Provides
        fun provideImageFragmentViewModel(
                factory: ViewModelProvider.Factory,
                target: DetailImageFragment
        ): ImageFragmentViewModel =
                ViewModelProviders.of(target, factory).get(ImageFragmentViewModel::class.java)

        @Provides
        fun provideTabletDetailViewModel(
                factory: ViewModelProvider.Factory,
                target: DetailImageFragment
        ): DetailActivityViewModel =
                ViewModelProviders.of(target.requireActivity(), factory).get(DetailActivityViewModel::class.java)
    }


}

