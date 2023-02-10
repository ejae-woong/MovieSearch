package com.example.moviesearch.adapter

import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.moviesearch.R
import com.example.moviesearch.databinding.ItemMovieBinding
import com.example.moviesearch.model.Movie
import com.example.moviesearch.model.RecentKeyword

class SearchedMovieAdapter : PagingDataAdapter<Movie, PagingViewHolder>(diffCallback) {
    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onBindViewHolder(holder: PagingViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagingViewHolder {
        return PagingViewHolder(
            ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }
}

class PagingViewHolder(private val binding: ItemMovieBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: Movie) {
        binding.movie = item
        binding.clRoot.setOnClickListener { showWebBrowser(item.link) }
        Glide.with(binding.root.context)
            .load(item.image)
            .error(R.drawable.error)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(binding.ivMovie)
    }

    private fun showWebBrowser(uri: String) {
        val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
        startActivity(binding.root.context, webIntent, null)
    }
}