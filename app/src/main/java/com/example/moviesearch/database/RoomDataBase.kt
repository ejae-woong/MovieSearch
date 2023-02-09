package com.example.moviesearch.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.moviesearch.model.RecentKeyword
import java.util.concurrent.Executors

@Database(entities = [RecentKeyword::class], version = 1)
abstract class RoomDataBase: RoomDatabase() {
    abstract fun recentKeywordDAO(): RecentKeywordDAO

    companion object {
        private const val DATABASE_NAME = "recent_keyword.db"
        @Volatile
        private var INSTANCE: RoomDataBase? = null

        @JvmStatic
        fun getInstance(context: Context): RoomDataBase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE = Room.databaseBuilder(context.applicationContext,
                    RoomDataBase::class.java,
                    DATABASE_NAME)
                    .setTransactionExecutor(Executors.newSingleThreadExecutor())
                    .allowMainThreadQueries()
                    .build()
                INSTANCE
            }!!
        }
    }
}