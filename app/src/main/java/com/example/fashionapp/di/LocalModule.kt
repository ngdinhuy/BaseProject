package com.example.fashionapp.di

import android.content.Context
import androidx.room.Room
import com.example.fashionapp.Define
import com.example.fashionapp.data.local.MyDatabse
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LocalModule {
    @Singleton
    @Provides
    fun provideMyDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(context, MyDatabse::class.java,Define.DATABASE_NAME).build()

    @Singleton
    @Provides
    fun provideDAO(db:MyDatabse) = db.getMyDAO()
}