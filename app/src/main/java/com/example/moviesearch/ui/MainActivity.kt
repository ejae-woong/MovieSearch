package com.example.moviesearch.ui

import android.app.Application
import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.size
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesearch.R
import com.example.moviesearch.adapter.SearchedMovieAdapter
import com.example.moviesearch.database.RoomDataBase
import com.example.moviesearch.databinding.ActivityMainBinding
import com.example.moviesearch.ui.MainActivity.ViewCallBack
import com.example.moviesearch.viewmodel.MovieSearchViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private val TAG = this::class.simpleName
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MovieSearchViewModel
    private lateinit var movieAdapter: SearchedMovieAdapter

    companion object {
        const val RECENT_KEYWORD = "recent_keyword"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also {
            it.lifecycleOwner = this
            it.viewCallBack = viewCallBack
        }
        RoomDataBase.getInstance(this)
        viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(Application()))[MovieSearchViewModel::class.java]
        setContentView(binding.root)
        initView()
    }

    private fun initView() {
        Log.d(TAG, "initView()")
        initAdapter()
        initIntent()
    }

    private fun initAdapter() {
        movieAdapter = SearchedMovieAdapter()
        binding.rvMovie.run {
            layoutManager = LinearLayoutManager(context)
            adapter = movieAdapter
            val dp8 = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8F, resources.displayMetrics).toInt()
            addItemDecoration(MovieItemDecoration(dp8))
        }
    }

    private fun initIntent() {
        Log.e(TAG, "initIntent()")
        intent.extras?.getString(RECENT_KEYWORD)?.let {
            Log.d(TAG, "intent.string: $it")
            searchMovie(it)
            binding.etKeyword.setText(it)
        }
    }

    private fun hideKeyboard() {
        binding.etKeyword.clearFocus()
        val imm = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(this.window.decorView.applicationWindowToken, 0)
    }

    private fun searchMovie(keyword: String) {
        Log.d(TAG, "searchMovie($keyword)")
        hideKeyboard()
        if (keyword.isBlank()) {
            Toast.makeText(this, "검색어를 입력해주세요", Toast.LENGTH_SHORT).show()
            return
        }

        lifecycleScope.launch {
            viewModel.searchMovie(keyword).collectLatest {
                movieAdapter.submitData(it)
            }
        }
    }

    private fun showRecentSearchActivity() {
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
}

class MovieItemDecoration (
    val bottomMargin : Int
    ) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        if (parent.getChildAdapterPosition(view) != parent.size - 1) {
            outRect.bottom = bottomMargin
        }
    }
}