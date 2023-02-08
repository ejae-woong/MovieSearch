package com.example.moviesearch.network

import com.example.moviesearch.model.MovieResponse
//import com.example.moviesearch.model.MovieList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface NaverAPI {
    @GET("/v1/search/movie.json")
    suspend fun searchMovie(
        @Header("X-Naver-Client-Id") clientId: String,
        @Header("X-Naver-Client-Secret") clientSecret: String,
        @Query("query") query: String,
        @Query("display") display: Int? = null,
        @Query("start") start: Int? = null
    ): Response<MovieResponse>
}