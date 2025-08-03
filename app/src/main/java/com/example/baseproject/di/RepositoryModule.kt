package com.example.baseproject.di

import com.example.baseproject.data.ShopAppResponsitory
import com.example.baseproject.data.ShopAppResponsitoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindShopAppRepository(repository: ShopAppResponsitoryImpl): ShopAppResponsitory

}