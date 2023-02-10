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
import com.example.moviesearch.database.RoomDataBase
import com.example.moviesearch.model.Movie
import com.example.moviesearch.model.RecentKeyword
import com.example.moviesearch.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class MovieSearchViewModel(application: Application): AndroidViewModel(application) {
    val roomDao = RoomDataBase.getInstance(application).recentKeywordDAO()

    fun searchMovie(keyword: String): Flow<PagingData<Movie>> {
        roomDao.insertKeyword(RecentKeyword(keyword = keyword))
        return MovieRepository.getInstance().requestSearchMovie(keyword).cachedIn(viewModelScope)
    }

    fun getRecentKeyword(): ArrayList<RecentKeyword> {
        val all = roomDao.getAllKeyword().sortedBy { it.id }.reversed().distinctBy { it.keyword }
        val recentList = arrayListOf<RecentKeyword>()
        all.forEachIndexed { index, it ->
            if (index == 10) return recentList
            recentList.add(it)
        }
        return recentList
    }
}