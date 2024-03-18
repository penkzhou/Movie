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
"backdrop_path": "/AwB0BmQm1GxP0BH8ZL7WPNDTkb5.jpg",
"first_air_date": "2016-01-05",
"genre_ids": [
10759,
10765
],
"id": 64122,
"original_language": "en",
"original_name": "The Shannara Chronicles",
"overview": "A young Healer armed with an unpredictable magic guides a runaway Elf in her perilous quest to save the peoples of the Four Lands from an age-old Demon scourge.",
"origin_country": [
"US"
],
"poster_path": "/aurZJ8UsXqhGwwBnNuZsPNepY8y.jpg",
"popularity": 9.523348,
"name": "The Shannara Chronicles",
"vote_average": 5.5,
"vote_count": 61
}
 * */
data class TmdbSimpleTvItem(
    val id: Int,
    val name: String,
    val vote_average: Double,
    val vote_count: Int,
    val poster_path: String?,
    val backdrop_path: String,
    val first_air_date: String,
    val original_language: String,
    val original_name: String,
    val overview: String,
    val popularity: Double,
    val genre_ids: List<Int>,
    val origin_country: List<String>
)
