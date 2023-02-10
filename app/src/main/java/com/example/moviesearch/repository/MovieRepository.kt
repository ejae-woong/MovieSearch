package com.example.moviesearch.repository

import android.util.Log
import androidx.paging.*
import com.example.moviesearch.database.RecentKeywordDAO
import com.example.moviesearch.database.RoomDataBase
import com.example.moviesearch.model.Movie
import com.example.moviesearch.model.MovieResponse
import com.example.moviesearch.network.ApiHelper
import com.example.moviesearch.network.NaverAPI
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response

class MovieRepository {
    companion object {
        const val PAGING_SIZE = 5

        @Volatile
        private var INSTANCE: MovieRepository? = null

        fun getInstance(): MovieRepository {
            return INSTANCE ?: synchronized(this) {
                INSTANCE = MovieRepository()
                INSTANCE
            }!!
        }
    }

    fun requestSearchMovie(keyword: String): Flow<PagingData<Movie>> {

        return Pager(config = PagingConfig(
            pageSize = PAGING_SIZE,
//            enablePlaceholders = false,
            initialLoadSize = PAGING_SIZE,
            prefetchDistance = PAGING_SIZE
        ),
            pagingSourceFactory = { MoviePagingSource(keyword)}
        ).flow
    }

    inner class MoviePagingSource(val keyword: String) : PagingSource<Int, Movie>() {
        var count = 1
        override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
            return try {
                delay(500)
                val next = params.key ?: 0
                val service = ApiHelper().getInstance().create(NaverAPI::class.java)
                val response = service.searchMovie(keyword = keyword, display = PAGING_SIZE, start = count).movieList
                count += PAGING_SIZE
                val isAllLoaded = response.size < PAGING_SIZE
                LoadResult.Page(
                    data = response,
                    prevKey = if (next == 0) null else next - 1,
                    nextKey = if (isAllLoaded) null else next + 1
                )
            } catch (e: Exception) {
                LoadResult.Error(e)
            }
        }

        override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
            return state.anchorPosition?.let { anchorPosition ->
                state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                    ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
            }
        }


    }
}