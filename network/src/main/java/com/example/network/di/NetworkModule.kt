package com.example.network.di

import android.content.Context
import com.example.network.ApiInterface
import com.example.network.repository.NetworkRepository
import com.example.network.datasource.RemoteDataSource
import com.example.network.NetworkStatusHelper
import com.example.network.datasource.AppDataSource
import com.example.network.interceptor.MockInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder()
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .addInterceptor(MockInterceptor())
            .build()
    }

    @Provides
    fun provideNetworkHelper(@ApplicationContext appContext: Context) =
        NetworkStatusHelper(appContext)

    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient
    ): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())

            .baseUrl("https://example.com") //base url for api calling
            .client(okHttpClient)
            .build()

    @Provides
    fun provideApiService(retrofit: Retrofit): ApiInterface =
        retrofit.create(ApiInterface::class.java)

    @Provides
    fun provideApiHelper(apiService: ApiInterface): AppDataSource = RemoteDataSource(apiService)


    @Provides
    fun provideMainRepo(appDataSource: AppDataSource): NetworkRepository = NetworkRepository(appDataSource)
}