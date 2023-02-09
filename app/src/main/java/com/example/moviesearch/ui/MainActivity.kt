package com.example.moviesearch.ui

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviesearch.R
import com.example.moviesearch.adapter.SearchedMovieAdapter
import com.example.moviesearch.databinding.ActivityMainBinding
import com.example.moviesearch.viewmodel.MovieSearchViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private val TAG = this::class.simpleName
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MovieSearchViewModel
    private lateinit var movieAdapter: SearchedMovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also {
            it.lifecycleOwner = this
            it.viewCallBack = viewCallBack
        }
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

    fun showRecentSearchFragment() {
        Toast.makeText(this, "asdasd", LENGTH_SHORT).show()
        val ft = supportFragmentManager.beginTransaction()
        ft.run {
            setCustomAnimations(R.anim.slide_right_in, R.anim.slide_right_out)
            add(binding.root.id, RecentSearchedFragment()).addToBackStack(null)
            commit()
        }
    }

    fun interface ViewCallBack {
        fun onClick(view: View)
    }

    private val viewCallBack = ViewCallBack { view ->
        when (view.id) {
            R.id.btn_search -> searchMovie(binding.etKeyword.text.toString())
            R.id.btn_recent -> showRecentSearchFragment()
        }
    }
}