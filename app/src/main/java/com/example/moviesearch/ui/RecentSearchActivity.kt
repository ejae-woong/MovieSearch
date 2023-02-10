package com.example.moviesearch.ui

import android.app.Application
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.moviesearch.adapter.RecentKeywordAdapter
import com.example.moviesearch.databinding.ActivityRecentSearchBinding
import com.example.moviesearch.model.RecentKeyword
import com.example.moviesearch.viewmodel.MovieSearchViewModel

class RecentSearchActivity : AppCompatActivity() {
    private val TAG = this::class.simpleName
    private lateinit var binding: ActivityRecentSearchBinding
    private lateinit var viewModel: MovieSearchViewModel
    private lateinit var searchedMovieAdapter: RecentKeywordAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecentSearchBinding.inflate(layoutInflater).also {
            it.lifecycleOwner = this
        }
        viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(Application()))[MovieSearchViewModel::class.java]
        setContentView(binding.root)
        initView()
    }

    private fun initView() {
        searchedMovieAdapter = RecentKeywordAdapter().apply {
            setList(viewModel.getRecentKeyword())
            setItemClickListener(itemClickListener)
        }
        binding.rvRecent.run {
            layoutManager = GridLayoutManager(context, 2)
            adapter = searchedMovieAdapter
        }
    }

    fun interface ItemClickListener {
        fun onClick(item: RecentKeyword)
    }

    private val itemClickListener = ItemClickListener {
        Log.d(TAG, "onClick($it)")
        finish()
        Intent(this, MainActivity::class.java).run {
            flags = FLAG_ACTIVITY_CLEAR_TOP
            putExtra(MainActivity.RECENT_KEYWORD, it.keyword)
            startActivity(this)
        }
    }
}