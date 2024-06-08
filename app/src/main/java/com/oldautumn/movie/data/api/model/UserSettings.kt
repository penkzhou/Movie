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
 * "user": {
"username": "justin",
"private": false,
"name": "Justin Nemeth",
"vip": true,
"vip_ep": false,
"ids": {
"slug": "justin",
"uuid": "b6589fc6ab0dc82cf12099d1c2d40ab994e8410c"
},
"joined_at": "2010-09-25T17:49:25.000Z",
"location": "San Diego, CA",
"about": "Co-founder of trakt.",
"gender": "male",
"age": 32,
"images": {
"avatar": {
"full": "https://secure.gravatar.com/avatar/30c2f0dfbc39e48656f40498aa871e33?r=pg&s=256"
}
},
"vip_og": true,
"vip_years": 5
},
"account": {
"timezone": "America/Los_Angeles",
"date_format": "mdy",
"time_24hr": false,
"cover_image": "https://walter.trakt.tv/images/movies/000/001/545/fanarts/original/0abb604492.jpg"
},
"connections": {
"facebook": false,
"twitter": true,
"google": true,
"tumblr": false,
"medium": false,
"slack": false,
"apple": false
},
"sharing_text": {
"watching": "I'm watching [item]",
"watched": "I just watched [item]",
"rated": "[item] [stars]"
},
"limits": {
"list": {
"count": 5,
"item_count": 250
},
"watchlist": {
"item_count": 250
},
"recommendations": {
"item_count": 50
}
}
 */

data class UserSettings(
    val user: User,
    val account: Account,
    val connections: Connections,
    val sharing_text: SharingText,
    val limits: Limits
)

data class User(
    val username: String,
    val private: Boolean,
    val name: String,
    val vip: Boolean,
    val vip_ep: Boolean,
    val ids: Ids,
    val joined_at: String,
    val location: String,
    val about: String,
    val gender: String,
    val age: Int?,
    val images: Images,
    val vip_og: Boolean,
    val vip_years: Int
)

data class Ids(val slug: String, val uuid: String)

data class Images(val avatar: Avatar)

data class Avatar(val full: String)

data class Account(
    val timezone: String,
    val date_format: String,
    val time_24hr: Boolean,
    val cover_image: String
)

data class Connections(
    val facebook: Boolean,
    val twitter: Boolean,
    val google: Boolean,
    val tumblr: Boolean,
    val medium: Boolean,
    val slack: Boolean,
    val apple: Boolean
)

data class SharingText(val watching: String, val watched: String, val rated: String)

data class Limits(
    val list: CommonList,
    val watchlist: Watchlist,
    val recommendations: Recommendations
)

data class CommonList(val count: Int, val item_count: Int)

data class Watchlist(val item_count: Int)

data class Recommendations(val item_count: Int)
