package com.example.moviesearch.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesearch.databinding.ItemRecentSearchBinding
import com.example.moviesearch.model.RecentKeyword
import com.example.moviesearch.ui.RecentSearchActivity

class RecentKeywordAdapter: RecyclerView.Adapter<RecentKeywordAdapter.ViewHolder>() {
    private lateinit var list: ArrayList<RecentKeyword>
    private lateinit var itemClickListener: RecentSearchActivity.ItemClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemRecentSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setList(list: ArrayList<RecentKeyword>) {
        this.list = list
    }

    fun setItemClickListener(itemClickListener: RecentSearchActivity.ItemClickListener) {
        this.itemClickListener = itemClickListener
    }

    inner class ViewHolder(val binding: ItemRecentSearchBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: RecentKeyword) {
            binding.tvKeyword.text = item.keyword
            binding.root.setOnClickListener { itemClickListener.onClick(item) }
        }
    }
}

