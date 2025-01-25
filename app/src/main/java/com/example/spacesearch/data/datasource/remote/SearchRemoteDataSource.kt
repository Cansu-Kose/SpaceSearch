package com.example.spacesearch.data.datasource.remote

import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.spacesearch.data.model.entity.PostData
import com.example.spacesearch.data.service.remote.SearchAPIService
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

@ExperimentalPagingApi
class SearchRemoteDataSource @Inject constructor(
    private val apiService: SearchAPIService,
    private val keyword: String,
    private val time: String,
    private val limit: Int,
    private val after: String
) : PagingSource<Int, PostData>() {

    override fun getRefreshKey(state: PagingState<Int, PostData>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PostData> {
        return try {
            val nextPage = params.key ?: 1
            val response = apiService.getTopPosts(keyword, nextPage, time, limit,after)

            val posts = response.data.children.map { it.postData } // Extract list of PostData

            LoadResult.Page(
                data = posts,
                prevKey = if (nextPage == 1) null else nextPage - 1,
                nextKey = if (posts.isNotEmpty()) nextPage + 1 else null
            )
        } catch (exception: IOException) {
            Timber.e("IOException: ${exception.message}")
            LoadResult.Error(exception)
        } catch (httpException: HttpException) {
            Timber.e("HttpException: ${httpException.message}")
            LoadResult.Error(httpException)
        }
    }
}
