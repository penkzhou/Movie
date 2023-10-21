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

data class TmdbImageModel(
    val id: Int,
    val backdrops: List<TmdbImageItem>,
    val posters: List<TmdbImageItem>,
)

data class TmdbImageItem(
    val aspect_ratio: Double,
    val file_path: String,
    val height: Int,
    val widtb: Int,
    val vote_average: Double,
    val vote_count: Int,
    val iso_639_1: String?,
)
