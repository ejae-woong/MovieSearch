package com.example.moviesearch.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.moviesearch.model.Movie
import com.example.moviesearch.repository.MovieRepository
import kotlinx.coroutines.launch

class MovieSearchViewModel(application: Application): AndroidViewModel(application) {

    private val _searchedMovieList: MutableLiveData<ArrayList<Movie>> = MutableLiveData()
    val searchedMovieList: LiveData<ArrayList<Movie>> get() = _searchedMovieList

    fun getSearchedMovieList() = searchedMovieList

    fun searchMovie(keyword: String) {
        viewModelScope.launch {
            MovieRepository.getInstance().requestSearchMovie(keyword).collect { response ->
                _searchedMovieList.postValue(response.body()?.movieList)
            }
        }
    }
}