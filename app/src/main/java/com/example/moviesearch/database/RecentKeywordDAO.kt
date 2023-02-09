package com.example.moviesearch.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.moviesearch.model.RecentKeyword

@Dao
interface RecentKeywordDAO{
    @Insert
    fun insertKeyword(recentKeyword: RecentKeyword)

    @Query("SELECT * FROM recent_keyword")
    fun getAllKeyword(): List<RecentKeyword>

    @Query("DELETE FROM recent_keyword WHERE id = :id")
    fun deleteById(id: Int)
}