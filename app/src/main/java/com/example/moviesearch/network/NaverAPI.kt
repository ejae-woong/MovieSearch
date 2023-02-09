package com.example.moviesearch.network

import com.example.moviesearch.model.MovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface NaverAPI {
    @GET("/v1/search/movie.json")
    suspend fun searchMovie(
        @Header("X-Naver-Client-Id") clientId: String = "j1kQ7NmRVBDg3Xet69r2",
        @Header("X-Naver-Client-Secret") clientSecret: String = "MkIdKphTDk",
        @Query("query") keyword: String,
        @Query("display") display: Int? = null,
        @Query("start") start: Int? = null
    ): MovieResponse
}