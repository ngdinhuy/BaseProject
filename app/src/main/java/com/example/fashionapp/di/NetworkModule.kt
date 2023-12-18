package com.example.fashionapp.di

import android.content.Context
import com.example.fashionapp.Define
import com.example.fashionapp.utils.Prefs
import com.example.shopapp.data.remote.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideBaseUrl() = Define.BASE_URL

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Provides
    @Singleton
    fun provideHeaderInterceptor(@ApplicationContext context: Context):Interceptor{
        return Interceptor{
            val request = it.request()
                .newBuilder()
                .addHeader("Authorization", "Bearer "+ Prefs.newInstance(context).getToken())
                .build()
            it.proceed(request)
        }
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(logging: HttpLoggingInterceptor, interceptor: Interceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .addInterceptor(interceptor)
            .connectTimeout(60, TimeUnit.SECONDS) // connect timeout
            .readTimeout(60, TimeUnit.SECONDS)
            .build()

        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
    }



    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient,baseUrl: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    @Provides
    @Singleton
    fun provideMovieAppService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

}