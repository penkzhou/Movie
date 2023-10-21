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
"name": "Bryan Cranston",
"ids": {
"trakt": 1,
"slug": "bryan-cranston",
"imdb": "nm0186505",
"tmdb": 17419
},
"social_ids": {
"twitter": "BryanCranston",
"facebook": "thebryancranston",
"instagram": "bryancranston",
"wikipedia": null
},
"biography": "Bryan Lee Cranston (born March 7, 1956) is an American actor, voice actor, writer and director.He is perhaps best known for his roles as Hal, the father in the Fox situation comedy \"Malcolm in the Middle\", and as Walter White in the AMC drama series Breaking Bad, for which he has won three consecutive Outstanding Lead Actor in a Drama Series Emmy Awards. Other notable roles include Dr. Tim Whatley on Seinfeld, Doug Heffernan's neighbor in The King of Queens, Astronaut Buzz Aldrin in From the Earth to the Moon, and Ted Mosby's boss on How I Met Your Mother.  Description above from the Wikipedia article Bryan Cranston, licensed under CC-BY-SA, full list of contributors on Wikipedia.",
"birthday": "1956-03-07",
"death": null,
"birthplace": "San Fernando Valley, California, USA",
"homepage": "http://www.bryancranston.com/",
"gender": "male",
"known_for_department": "acting"
}
 * */
data class TraktPeople(
    val name: String,
    val ids: MovieIds,
    val social_ids: SocialIds,
    val biography: String,
    val birthday: String,
    val death: String?,
    val birthplace: String,
    val homepage: String?,
    val gender: String,
    val known_for_department: String,
)

data class SocialIds(
    val twitter: String?,
    val facebook: String?,
    val instagram: String?,
    val wikipedia: String?,
)
