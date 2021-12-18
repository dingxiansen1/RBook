package com.dd.rbook.room.searchHistory

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [SearchHistory::class],version = 1)
abstract class SearchHistoryDataBase :RoomDatabase(){

    abstract fun SearchHistoryDao() : SearchHistoryDao

}
