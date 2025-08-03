package com.example.baseproject.di

import android.content.Context
import com.example.baseproject.utils.SharedHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LocalModule {
    @Provides
    @Singleton
    fun provideSharedHelper(@ApplicationContext context: Context) = SharedHelper(context)


}