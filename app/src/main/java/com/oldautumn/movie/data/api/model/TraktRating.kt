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

{
  "rating": 9.38363,
  "votes": 51065,
  "distribution": {
    "1": 320,
    "2": 77,
    "3": 73,
    "4": 131,
    "5": 300,
    "6": 514,
    "7": 1560,
    "8": 4399,
    "9": 9648,
    "10": 34042
  }
}

* */
data class TraktRating(val rating: Double, val votes: Int, val distribution: Map<String, Int>)
