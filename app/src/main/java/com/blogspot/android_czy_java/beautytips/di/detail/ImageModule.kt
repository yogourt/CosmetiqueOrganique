package com.blogspot.android_czy_java.beautytips.di.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.blogspot.android_czy_java.beautytips.di.core.ViewModelKey
import com.blogspot.android_czy_java.beautytips.listView.view.ImageFragment
import com.blogspot.android_czy_java.beautytips.viewmodel.common.ImageFragmentViewModel
import com.blogspot.android_czy_java.beautytips.usecase.common.LoadImageFragmentDataUseCase
import com.blogspot.android_czy_java.beautytips.viewmodel.recipe.RecipeViewModel
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class ImageModule {

    @ContributesAndroidInjector(modules = [
        InjectViewModel::class
    ])

    abstract fun bind(): ImageFragment

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
        fun provideListNotesViewModel(
                factory: ViewModelProvider.Factory,
                target: ImageFragment
        ): ImageFragmentViewModel =
                ViewModelProviders.of(target, factory).get(ImageFragmentViewModel::class.java)
    }
}