package com.dd.rbook.room.searchHistory

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "search_history")
data class SearchHistory(

    @PrimaryKey
    @ColumnInfo(name = "content")
    var content : String,//内容

    @ColumnInfo(name = "create_date")
    var createDate : String//创建时间

)
