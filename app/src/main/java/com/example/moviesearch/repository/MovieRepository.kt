package com.example.moviesearch.repository

import android.util.Log
import com.example.moviesearch.network.ApiHelper
import com.example.moviesearch.network.NaverAPI
import kotlinx.coroutines.flow.flow

class MovieRepository {
    companion object {

        @Volatile
        private var INSTANCE: MovieRepository? = null

        fun getInstance(): MovieRepository {
            return INSTANCE ?: synchronized(this) {
                INSTANCE = MovieRepository()
                INSTANCE
            }!!
        }
    }

    fun requestSearchMovie(keyword: String) =
        flow {
            Log.e("asdasdasd", "asdasdasd")
            emit(ApiHelper().getInstance().create(NaverAPI::class.java)
                .searchMovie("j1kQ7NmRVBDg3Xet69r2", "MkIdKphTDk", keyword))
        }

}