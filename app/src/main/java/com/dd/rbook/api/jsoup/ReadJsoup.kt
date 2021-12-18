package com.dd.rbook.api.jsoup

import com.dd.rbook.bean.MultiValueMap
import com.dd.rbook.bean.SearchBookBean
import com.dd.rbook.bean.StrResponse
import com.dd.rbook.room.book.Book
import com.dd.rbook.room.book.Chapter
import kotlinx.coroutines.flow.Flow

interface ReadJsoup {

    fun getBookUrlName():String

    fun getBookUrl():String

    suspend fun getSearchBook(searchKey: String,books:MutableList<Book>): MutableList<Book> // 搜索书籍规则

    fun getBooksFromStrResponse(searchKey: String): MultiValueMap<SearchBookBean?, Book?>? // 搜索书籍规则

    fun getChaptersFromStrResponse(response: StrResponse?): List<Chapter?>? // 获取书籍章节列表规则

    fun getContentFormStrResponse(response: StrResponse?): String? // 获取书籍内容规则

}