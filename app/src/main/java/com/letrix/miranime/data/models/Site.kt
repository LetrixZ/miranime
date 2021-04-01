package com.letrix.miranime.data.models


import com.google.gson.annotations.SerializedName

data class Site(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String
)