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
    "like_count": 109,
    "comment_count": 20,
    "list": {
      "name": "Top Chihuahua Movies",
      "description": "So cute.",
      "privacy": "public",
      "display_numbers": true,
      "allow_comments": true,
      "sort_by": "rank",
      "sort_how": "asc",
      "created_at": "2015-10-11T17:00:54.000Z",
      "updated_at": "2015-10-11T17:00:54.000Z",
      "item_count": 50,
      "comment_count": 20,
      "likes": 109,
      "ids": {
        "trakt": 1338,
        "slug": "top-chihuahua-movies"
      },
      "user": {
        "username": "justin",
        "private": false,
        "name": "Justin Nemeth",
        "vip": true,
        "vip_ep": false,
        "ids": {
          "slug": "justin"
        }
      }
    }
  }
* */
data class TraktCollection(
    val like_count: Int,
    val comment_count: Int,
    val list: TraktCollectionItem,
)

data class TraktCollectionItem(
    val name: String,
    val description: String,
    val privacy: String,
    val display_numbers: Boolean,
    val allow_comments: Boolean,
    val sort_by: String,
    val sort_how: String,
    val created_at: String,
    val updated_at: String,
    val item_count: Int,
    val comment_count: Int,
    val likes: Int,
    val ids: MovieIds,
    val user: TraktUser,
)
