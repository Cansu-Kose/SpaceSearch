package com.example.spacesearch.data.datasource.remote

import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.spacesearch.data.model.entity.Search
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
    private val limit: Int
) : PagingSource<Int, Search>() {

    override fun getRefreshKey(state: PagingState<Int, Search>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Search> {
        return try {
            val nextPage = params.key ?: 1
            val response = apiService.getTopPosts(keyword, 1, time, limit)

            LoadResult.Page(
                data = response,
                prevKey = if (nextPage == 1) null else nextPage - 1,
                nextKey = if (response.isNotEmpty()) nextPage + 1 else null
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
