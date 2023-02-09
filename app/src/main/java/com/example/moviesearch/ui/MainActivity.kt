package com.example.moviesearch.ui

import android.app.Application
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviesearch.R
import com.example.moviesearch.adapter.SearchedMovieAdapter
import com.example.moviesearch.database.RoomDataBase
import com.example.moviesearch.databinding.ActivityMainBinding
import com.example.moviesearch.viewmodel.MovieSearchViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private val TAG = this::class.simpleName
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MovieSearchViewModel
    private lateinit var movieAdapter: SearchedMovieAdapter
    var str: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also {
            it.lifecycleOwner = this
            it.viewCallBack = viewCallBack
        }
        RoomDataBase.getInstance(this)
        viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(Application()))[MovieSearchViewModel::class.java]
        setContentView(binding.root)
        subscribeUI()
        initView()
    }

    private fun subscribeUI() {
        viewModel.searchedMovieList.observe(this) {
            Log.e(TAG, it.toString())
        }
    }

    private fun initView() {
        Log.e(TAG, "initView()")
        movieAdapter = SearchedMovieAdapter()
        binding.rvMovie.run {
            layoutManager = LinearLayoutManager(context)
            adapter = movieAdapter
        }
    }

    fun searchMovie(keyword: String) {
        lifecycleScope.launch {
            viewModel.searchMovie(keyword).collectLatest {
                movieAdapter.submitData(it)
            }
        }
    }

    fun setStrs(str: String) {
        this.str = str
    }

    override fun onResume() {
        super.onResume()
        Log.e(TAG, "onResume(), str: $str")
        str?.let {
            searchMovie(it)
        }
    }

    fun showRecentSearchActivity() {
        Intent(this, RecentSearchActivity::class.java).run {
            startActivity(this)
        }
    }

    fun interface ViewCallBack {
        fun onClick(view: View)
    }

    private val viewCallBack = ViewCallBack { view ->
        when (view.id) {
            R.id.btn_search -> searchMovie(binding.etKeyword.text.toString())
            R.id.btn_recent -> showRecentSearchActivity()
        }
    }

//    private val itemClickListener = RecentSearchActivity.ItemClickListener {
//        viewModel.searchMovie(it.keyword)
//    }
}