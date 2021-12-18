package com.dd.rbook.room.searchHistory

import androidx.room.*

@Dao
interface SearchHistoryDao{

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSearchHistory(searchHistory: SearchHistory)

    @Query("select content from search_history order by create_date desc")
    suspend fun queryAll(): MutableList<String>?

    // 删除所有历史搜索记录
    @Query("delete from search_history")
    suspend fun deleteAll()
}
