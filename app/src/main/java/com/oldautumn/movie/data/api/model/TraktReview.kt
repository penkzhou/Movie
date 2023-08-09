package com.oldautumn.movie.data.api.model

/*


{
  "id": 1,
  "parent_id": 0,
  "created_at": "2010-11-03T06:30:13.000Z",
  "comment": "Agreed, this show is awesome. AMC in general has awesome shows.",
  "spoiler": false,
  "review": false,
  "replies": 1,
  "likes": 0,
  "user_stats": {
    "rating": 8,
    "play_count": 1,
    "completed_count": 1
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
* */
data class TraktReview(
    val id: Int,
    val parent_id: Int,
    val created_at: String,
    val comment: String,
    val spoiler: Boolean,
    val review: Boolean,
    val replies: Int,
    val likes: Int,
    val user_stats: TraktUserStats,
    val user: TraktUser,
)

data class TraktUserStats(
    val rating: Int?,
    val play_count: Int,
    val completed_count: Int,
)

data class TraktUser(
    val username: String,
    val private: Boolean,
    val name: String,
    val vip: Boolean,
    val vip_ep: Boolean,
    val ids: TraktUserIds,
)

data class TraktUserIds(
    val slug: String,
)
