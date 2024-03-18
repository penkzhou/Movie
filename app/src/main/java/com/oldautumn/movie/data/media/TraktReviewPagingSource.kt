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
package com.oldautumn.movie.data.media

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.oldautumn.movie.data.api.TraktApiService
import com.oldautumn.movie.data.api.model.TraktReview
import java.io.IOException
import retrofit2.HttpException

class TraktReviewPagingSource(
    val traktApiService: TraktApiService,
    val query: String,
    val sortType: String
) : PagingSource<Int, TraktReview>() {
    override fun getRefreshKey(state: PagingState<Int, TraktReview>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TraktReview> {
        try {
            // Start refresh at page 1 if undefined.
            val nextPageNumber = params.key ?: 1
            val response =
                traktApiService.fetchTraktMovieReviewList(
                    query,
                    sortType,
                    nextPageNumber,
                    params.loadSize
                )
            return LoadResult.Page(
                data = response,
                prevKey = null,
                nextKey = nextPageNumber + 1
            )
        } catch (e: IOException) {
            // IOException for network failures.
            return LoadResult.Error(e)
        } catch (e: HttpException) {
            // HttpException for any non-2xx HTTP status codes.
            return LoadResult.Error(e)
        }
    }
}
