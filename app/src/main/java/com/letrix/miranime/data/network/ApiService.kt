package com.letrix.miranime.data.network

import com.google.gson.JsonObject
import com.letrix.miranime.data.Data
import io.github.wax911.library.annotation.GraphQuery
import io.github.wax911.library.model.body.GraphContainer
import io.github.wax911.library.model.request.QueryContainerBuilder
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("/graphql")
    @GraphQuery("GetAnime")
    suspend fun get(@Body builder: QueryContainerBuilder): Response<GraphContainer<Data>>

    @POST("/graphql")
    @GraphQuery("GetHome")
    suspend fun getHome(@Body builder: QueryContainerBuilder): Response<GraphContainer<Data>>

    @POST("/graphql")
    suspend fun getRetro(@Body string: JsonObject): GraphContainer<Data>

}