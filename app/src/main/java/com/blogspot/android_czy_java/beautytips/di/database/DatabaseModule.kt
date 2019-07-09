package com.blogspot.android_czy_java.beautytips.di.database

import android.content.Context
import com.blogspot.android_czy_java.beautytips.database.AppDatabase
import com.blogspot.android_czy_java.beautytips.repository.FirebaseToRoom
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {


    @Provides
    @Singleton
    fun provideRecipeDao(db: AppDatabase) = db.recipeDao()

    @Provides
    @Singleton
    fun provideAppDatabase(context: Context) = AppDatabase.getInstance(context)

    @Provides
    @Singleton
    fun provideFirebaseToRoom(db: AppDatabase) = FirebaseToRoom(db)

}