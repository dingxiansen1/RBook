package com.dd.rbook.room.book

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "chapter")
class Chapter {

    @PrimaryKey
    lateinit var id: String

    var bookId : String? = null//章节所属书的ID
    var number : Int? = null//章节序号
    var title : String? = null//章节标题
    var url : String? = null//章节链接(本地书籍为：字符编码)
    var content : String? = null//章节正文
    var start : Long? = null//章节内容在文章中的起始位置(本地)
    var end : Long? = null//章节内容在文章中的终止位置(本地)
}