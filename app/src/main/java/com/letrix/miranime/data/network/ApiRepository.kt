package com.letrix.miranime.data.network

import GetAnimeQuery
import GetHomeQuery
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.coroutines.await
import com.letrix.miranime.data.mapper.AnimeMapper
import com.letrix.miranime.data.models.Anime
import com.letrix.miranime.data.models.AnimeList
import javax.inject.Inject

class ApiRepository @Inject constructor(
    private val apollo: ApolloClient,
    private val animeMapper: AnimeMapper
) {

    suspend fun get(idMal: Int): Anime? {
        return try {
            val entity = apollo.query(GetAnimeQuery(idMal = idMal)).await()
            if (entity.data != null) animeMapper.mapFromEntity(entity.data!!.anime!!)
            else null
        } catch (e: Exception) {
            null
        }
    }

    suspend fun getHome(): List<AnimeList>? {
        val entity = apollo.query(GetHomeQuery()).await()
        return if (entity.data != null) entity.data!!.home!!.map { item ->
            AnimeList(
                title = item.title,
                list = item.list.map { animeMapper.mapFromEntity(it) }
            )
        } else null
    }

}