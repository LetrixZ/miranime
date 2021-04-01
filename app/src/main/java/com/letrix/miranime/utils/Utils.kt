package com.letrix.miranime.utils

object Utils {

    fun parseTitle(title: String) = when (title) {
        "latest_episodes" -> "Latest Releases"
        "latest_additions" -> "Recently Added"
        "top_anime" -> "Most Liked"
        else -> title
    }

    fun parseState(state: Int) = when (state) {
        1 -> "Ongoing"
        2 -> "Finished"
        3 -> "Soon"
        else -> throw Exception("Invalid state: $state")
    }

}