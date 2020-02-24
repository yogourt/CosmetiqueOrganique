package com.blogspot.android_czy_java.beautytips.di.view.newrecipe

import androidx.lifecycle.ViewModel
import com.blogspot.android_czy_java.beautytips.di.core.ViewModelKey
import com.blogspot.android_czy_java.beautytips.viewmodel.newrecipe.NewRecipeActivityViewModel
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
class NewRecipeActivityModule {

    @Module
    class ProvideViewModel {

        @Provides
        @IntoMap
        @ViewModelKey(NewRecipeActivityViewModel::class)
        fun provideNewRecipeActivityViewModel(): ViewModel =
                NewRecipeActivityViewModel()
    }


}