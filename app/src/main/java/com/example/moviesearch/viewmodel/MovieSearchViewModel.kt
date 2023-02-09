package com.example.moviesearch.viewmodel

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.moviesearch.model.Movie
import com.example.moviesearch.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class MovieSearchViewModel(application: Application): AndroidViewModel(application) {

    private val _searchedMovieList: MutableLiveData<ArrayList<Movie>> = MutableLiveData()
    val searchedMovieList: LiveData<ArrayList<Movie>> get() = _searchedMovieList

    fun searchMovie(keyword: String): Flow<PagingData<Movie>> {
        return MovieRepository.getInstance().requestSearchMovie(keyword).cachedIn(viewModelScope)
    }
}