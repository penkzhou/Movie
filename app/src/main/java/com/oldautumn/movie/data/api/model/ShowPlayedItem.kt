/*
 * Copyright 2023 The Old Autumn Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
    val show: TraktSimpleContentItem,
)
