package com.letrix.miranime.data.models


import com.google.gson.annotations.SerializedName

data class Anime(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("idAl")
    val idAl: Int?,
    @SerializedName("idMal")
    val idMal: Int?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("episodes")
    val episodes: Int?,
    @SerializedName("genres")
    val genres: List<String>?,
    @SerializedName("poster")
    val poster: String?,
    @SerializedName("banner")
    val banner: String?,
    @SerializedName("season")
    val season: String?,
    @SerializedName("sites")
    val sites: List<Site>?,
    @SerializedName("state")
    val state: Int?,
    @SerializedName("synonyms")
    val synonyms: List<String>?,
    @SerializedName("synopsis")
    val synopsis: String?,
    @SerializedName("type")
    val type: String?,
    @SerializedName("year")
    val year: Int?
) {
    var latestEpisode: Int? = null
    var thumbnail: String? = null
}

data class AnimeList(
    val title: String,
    val list: List<Anime>
)