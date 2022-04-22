package com.example.hsereddit.data.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.hsereddit.data.model.Post
import com.example.hsereddit.network.RestApiService
import kotlinx.coroutines.delay
import java.lang.Exception

class PostPagingSectionKeywordsSource(
    private val api: RestApiService,
    private val keywords: String
): PagingSource<Int, Post>() {
    private var currentPage: Int = 0
    private var pagesBefore: Int = 0
    private var pagesLeft: Int = 1


    override fun getRefreshKey(state: PagingState<Int, Post>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Post> {
        try {
            currentPage = params.key ?: FIRST_PAGE_INDEX
            val liveData: ArrayList<Post> = arrayListOf()
            api.getPostsByKeywordInTitle(keywords, currentPage) {
                if (it == null) {
                    Log.i("NULL", "get posts by section keywords: $keywords")
                } else {
                    liveData.addAll(it)
                }
            }
            val res = LoadResult.Page(
                data = liveData,
                prevKey = null,
                nextKey = currentPage + 1,
                itemsBefore = pagesBefore,
                itemsAfter = pagesLeft
            )
            pagesBefore += 1
            pagesLeft -= 1
            currentPage += 1
            Log.i("RETURN", res.toString())
            delay(1000)
            return res
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

    companion object {
        private const val FIRST_PAGE_INDEX = 0
    }
}