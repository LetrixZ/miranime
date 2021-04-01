package com.letrix.miranime.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.letrix.miranime.BaseApplication
import com.letrix.miranime.data.network.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.wax911.library.annotation.processor.GraphProcessor
import io.github.wax911.library.annotation.processor.plugin.AssetManagerDiscoveryPlugin
import io.github.wax911.library.converter.GraphConverter
import io.github.wax911.library.logger.DefaultGraphLogger
import io.github.wax911.library.logger.contract.ILogger
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(5, TimeUnit.SECONDS)
            .readTimeout(5, TimeUnit.SECONDS)
            .build()
    }

    @Singleton
    @Provides
    fun provideApiService(client: OkHttpClient, context: BaseApplication): ApiService {
        return Retrofit.Builder()
            .baseUrl("http://192.168.1.40:4000")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .addConverterFactory(
                GraphConverter(
                    GraphProcessor(
                        AssetManagerDiscoveryPlugin(assetManager = context.assets),
                        logger = DefaultGraphLogger(ILogger.Level.VERBOSE)
                    ), Gson()
                ).also {
                    it.setMinimumLogLevel(ILogger.Level.VERBOSE)
                }
            )
            .build()
            .create(ApiService::class.java)
    }

}