package com.dd.rbook.api

import android.util.Log
import com.dd.rbook.api.jsoup.ReadJsoup
import com.dd.rbook.bean.MultiValueMap
import com.dd.rbook.bean.SearchBookBean
import com.dd.rbook.http.OkHttpUtils
import com.dd.rbook.room.book.Book
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import rxhttp.toStr
import rxhttp.wrapper.param.RxHttp

class SearchBookApi  {

    companion object{

        fun getBookList():MutableList<ReadJsoup>{
            val bookList : MutableList<ReadJsoup> = mutableListOf()
            bookList.add(BiQuGeJsoup())
            return bookList
        }

        suspend fun searchBook(searchKey:String) :MutableList<Book>{
            val searchBooks = getBookList()
            val books: MutableList<Book> = mutableListOf()
            for (searchBook in searchBooks){
                searchBook.getSearchBook(searchKey,books)
            }
            return books
        }



    }
}