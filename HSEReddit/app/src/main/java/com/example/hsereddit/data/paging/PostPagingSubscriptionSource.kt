package com.example.hsereddit.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.hsereddit.data.model.Post
import com.example.hsereddit.network.RestApiService
import java.lang.Exception

class PostPagingSubscriptionSource(private val api: RestApiService): PagingSource<Int, Post>() {
    override fun getRefreshKey(state: PagingState<Int, Post>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Post> {
        try {
            val nextPage: Int = params.key ?: FIRST_PAGE_INDEX
            var res: LoadResult.Page<Int, Post>? = null

            api.getSubscriptionPosts(nextPage) {
                res = LoadResult.Page(
                    data = it,
                    prevKey = null,
                    nextKey = nextPage + 1
                )
            }

            return res!!

        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

    companion object {
        private const val FIRST_PAGE_INDEX = 0
    }
}