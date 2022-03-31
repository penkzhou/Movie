package com.oldautumn.movie.data.media

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.oldautumn.movie.data.api.TraktApiService
import com.oldautumn.movie.data.api.model.TraktReview
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Named

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
            val response = traktApiService.fetchTraktMovieReviewList(
                query,
                sortType,
                nextPageNumber,
                params.loadSize
            )
            return LoadResult.Page(
                data = response,
                prevKey = null, // Only paging forward.
                nextKey = nextPageNumber+1
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