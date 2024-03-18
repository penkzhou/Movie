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

/*
*
{
      "adult": false,
      "gender": 2,
      "id": 9828,
      "known_for_department": "Acting",
      "name": "Garrett Hedlund",
      "original_name": "Garrett Hedlund",
      "popularity": 8.712,
      "profile_path": "/qe7hflOB1B9xA8s9truiYQAz8xw.jpg",
      "cast_id": 44,
      "character": "Sam Flynn",
      "credit_id": "52fe43ebc3a368484e005d19",
      "order": 0
}

* */
data class TmdbCast(
    val adult: Boolean,
    val gender: Int?,
    val known_for_department: String,
    val id: Int,
    val name: String,
    val original_name: String,
    val profile_path: String?,
    val character: String,
    val credit_id: String,
    val order: Int,
    val popularity: Double,
    val cast_id: Int
)
