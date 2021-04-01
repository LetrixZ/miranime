package com.letrix.miranime.data


import com.google.gson.annotations.SerializedName
import com.letrix.miranime.data.models.Anime

data class Data(
    @SerializedName("anime")
    val anime: Anime
)