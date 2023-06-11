package com.oldautumn.movie.data.api.model

/**
 * {
"watcher_count": 155291,
"play_count": 23542030,
"collected_count": 6635583,
"collector_count": 54953,
"show": {
"title": "The Big Bang Theory",
"year": 2007,
"ids": {
"trakt": 1409,
"slug": "the-big-bang-theory",
"tvdb": 80379,
"imdb": "tt0898266",
"tmdb": 1418
}
}
},
 */
data class ShowPlayedItem(
    val watcher_count: Int,
    val play_count: Int,
    val collected_count: Int,
    val collector_count: Int,
    val show: TraktSimpleContentItem
)
