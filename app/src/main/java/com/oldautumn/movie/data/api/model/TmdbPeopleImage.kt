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
{
"id": 66633,
"profiles": [
{
"aspect_ratio": 0.666666666666667,
"file_path": "/rLSUjr725ez1cK7SKVxC9udO03Y.jpg",
"height": 819,
"iso_639_1": null,
"vote_average": 5.3125,
"vote_count": 1,
"width": 546
},
{
"aspect_ratio": 0.666666666666667,
"file_path": "/lYqC8Amj4owX05xQg5Yo7uUHgah.jpg",
"height": 3000,
"iso_639_1": null,
"vote_average": 0,
"vote_count": 0,
"width": 2000
}
]
}
 */
data class TmdbPeopleImage(
    val id: Int,
    val profiles: List<TmdbImageItem> = listOf()
)
