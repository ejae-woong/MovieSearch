package com.example.moviesearch.ui

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.example.moviesearch.databinding.ActivityMainBinding
import com.example.moviesearch.viewmodel.MovieSearchViewModel

class MainActivity : AppCompatActivity() {
    private val TAG = this::class.simpleName
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MovieSearchViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also {
            it.lifecycleOwner = this
        }
        viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(Application()))[MovieSearchViewModel::class.java]
        setContentView(binding.root)
        subscribeUI()
        initView()
        initData()
    }

    private fun subscribeUI() {
        viewModel.getSearchedMovieList().observe(this) {

        }
    }

    private fun initView() {
        viewModel.searchMovie("starwars")
    }

    private fun initData() {

    }
}