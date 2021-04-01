package com.letrix.miranime.di

import com.apollographql.apollo.ApolloClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.*
import okio.Timeout
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApolloModule {

    @Singleton
    @Provides
    fun provideApolloClient(client: OkHttpClient): ApolloClient = ApolloClient.builder()
        .okHttpClient(client)
        .serverUrl("http://192.168.1.40:4000/graphql")
        .build()

}