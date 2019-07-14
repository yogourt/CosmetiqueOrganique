package com.blogspot.android_czy_java.beautytips.di

import android.content.Context
import com.blogspot.android_czy_java.beautytips.MyApplication
import com.blogspot.android_czy_java.beautytips.di.database.DatabaseModule
import com.blogspot.android_czy_java.beautytips.di.recipe.RecipeModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton


@Component(modules = [
    AndroidSupportInjectionModule::class,
    ViewModelModule::class,
    DatabaseModule::class
])

@Singleton
interface AppComponent {

    fun inject(app: MyApplication)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(app: MyApplication): Builder

        @BindsInstance
        fun context(context: Context): Builder

        fun build(): AppComponent
    }

}