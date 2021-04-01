package com.letrix.miranime.data.mapper

import GetAnimeQuery
import GetHomeQuery
import com.letrix.miranime.data.models.Anime
import com.letrix.miranime.data.models.Site

class AnimeMapper : DomainMapper<GetAnimeQuery.Anime, Anime> {
    override fun mapFromEntity(model: GetAnimeQuery.Anime) = Anime(
        id = model.id,
        idMal = model.idMal,
        idAl = model.idAl,
        title = model.title,
        synonyms = model.synonyms,
        synopsis = model.synopsis,
        poster = model.poster,
        banner = model.banner,
        season = model.season,
        year = model.year,
        state = model.state,
        sites = model.sites?.map { Site(name = it.name, id = it.id) },
        genres = model.genres,
        episodes = model.episodes,
        type = model.type
    )

    fun mapFromEntity(model: GetHomeQuery.List) = Anime(
        id = model.id,
        idMal = model.idMal,
        idAl = model.idAl,
        title = model.title,
        synonyms = model.synonyms,
        synopsis = model.synopsis,
        poster = model.poster,
        banner = model.banner,
        season = model.season,
        year = model.year,
        state = model.state,
        sites = model.sites?.map { Site(name = it.name, id = it.id) },
        genres = model.genres,
        episodes = model.episodes,
        type = model.type
    ).also {
        it.latestEpisode = model.latestEpisode
        it.thumbnail = model.thumbnail
    }

    override fun mapToEntity(domainModel: Anime): GetAnimeQuery.Anime {
        TODO("Not yet implemented")
    }

}