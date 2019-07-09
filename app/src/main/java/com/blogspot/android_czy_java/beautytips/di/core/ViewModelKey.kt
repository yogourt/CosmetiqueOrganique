package com.blogspot.android_czy_java.beautytips.di.core

import androidx.lifecycle.ViewModel
import dagger.MapKey
import kotlin.reflect.KClass

@MapKey
@Target(AnnotationTarget.FUNCTION)
annotation class ViewModelKey(
        val value: KClass<out ViewModel>
)