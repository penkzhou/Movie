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

用于接收tmdb的简单电影信息，比如电影推荐列表或者电影相似列表，一般用于电影详情页面
{
 "adult": false,
 "backdrop_path": null,
 "genre_ids": [
 28
 ],
 "id": 106912,
 "original_language": "en",
 "original_title": "Darna! Ang Pagbabalik",
 "overview": "Valentina, Darna's snake-haired arch enemy, is trying to take over the Phillipines through subliminal messages on religious TV shows. Darna has her own problems, however, as she has lost her magic pearl and with it the ability to transform into her scantily clad super self. Trapped as her alter-ego, the plucky reporter Narda, she must try to regain the pearl and foil Valentina's plans.",
 "release_date": "1994-05-09",
 "poster_path": null,
 "popularity": 1.012564,
 "title": "Darna: The Return",
 "video": false,
 "vote_average": 0,
 "vote_count": 0
 }
* */
data class TmdbSimpleMovieItem(
    val adult: Boolean,
    val backdrop_path: String?,
    val genre_ids: List<Int>,
    val id: Int,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val release_date: String,
    val poster_path: String?,
    val popularity: Double,
    val title: String,
    val video: Boolean,
    val vote_average: Double,
    val vote_count: Int
)
