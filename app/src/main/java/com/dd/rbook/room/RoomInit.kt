package com.dd.rbook.room

import android.content.Context
import androidx.room.Room
import com.dd.rbook.room.searchHistory.SearchHistoryDataBase

object RoomInit {

    lateinit var RmSearchHistory: SearchHistoryDataBase

    fun initDatabase(context: Context) {
        RmSearchHistory = Room.databaseBuilder(
            context, SearchHistoryDataBase::class.java,"search_history.db")
            .allowMainThreadQueries().build()

    }


}