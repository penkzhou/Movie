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
 *
 * {
"id": 550,
"results": [
{
"iso_639_1": "en",
"iso_3166_1": "US",
"name": "Fight Club - Theatrical Trailer Remastered in HD",
"key": "6JnN1DmbqoU",
"site": "YouTube",
"size": 1080,
"type": "Trailer",
"official": false,
"published_at": "2015-02-26T03:19:25.000Z",
"id": "5e382d1b4ca676001453826d"
},
{
"iso_639_1": "en",
"iso_3166_1": "US",
"name": "Fight Club | #TBT Trailer | 20th Century FOX",
"key": "BdJKm16Co6M",
"site": "YouTube",
"size": 1080,
"type": "Trailer",
"official": true,
"published_at": "2014-10-02T19:20:22.000Z",
"id": "5c9294240e0a267cd516835f"
}
]
}
 */
data class MovieVideo(
    val id: Long,
    val results: List<MovieVideoItem>,
)

data class MovieVideoItem(
    val iso_639_1: String,
    val iso_3166_1: String,
    val name: String,
    val key: String,
    val site: String,
    val size: String,
    val type: String,
    val official: Boolean,
    val published_at: String,
    val id: String,
)
