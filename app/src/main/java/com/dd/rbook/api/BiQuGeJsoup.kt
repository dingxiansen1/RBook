package com.dd.rbook.api

import com.dd.rbook.api.jsoup.ReadJsoup
import com.dd.rbook.bean.MultiValueMap
import com.dd.rbook.bean.SearchBookBean
import com.dd.rbook.bean.StrResponse
import com.dd.rbook.common.AppConst
import com.dd.rbook.http.OkHttpUtils
import com.dd.rbook.room.book.Book
import com.dd.rbook.room.book.Chapter
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import rxhttp.toStr
import rxhttp.wrapper.param.RxHttp
import java.util.HashMap

class BiQuGeJsoup : ReadJsoup {

    override fun getBookUrlName(): String {
        return "笔趣阁"
    }

    override fun getBookUrl(): String {
        return "https://www.xbiquge.la"
    }

    override suspend fun getSearchBook(searchKey: String,books:MutableList<Book>): MutableList<Book> {
        val str  =  RxHttp.get(getBookUrl()+ "/modules/article/waps.php?searchkey=${searchKey}").toStr().await()
        val doc = Jsoup.parse(str)
        val divs =doc.getElementsByTag("table")
        val div =divs[0]
        val elementsByTags = div.getElementsByTag("tr")
        for (i in 1 until elementsByTags.size) {
            val book = Book()
            val element: Element = elementsByTags.get(i)
            val info = element.getElementsByTag("td")
            book.name = info[0].text()
            book.chapterUrl=(info[0].getElementsByTag("a").attr("href"))
            book.newestChapterTitle = info[1].text()
            book.author = info[2].text()
            book.updateDate = info[3].text()
            books.add(book)
        }
        return books
    }

    override fun getBooksFromStrResponse(searchKey: String): MultiValueMap<SearchBookBean?, Book?>? {
            return null
    }

    override fun getChaptersFromStrResponse(response: StrResponse?): List<Chapter?>? {
        return null
    }

    override fun getContentFormStrResponse(response: StrResponse?): String? {
        return null
    }


}