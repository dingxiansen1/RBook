package com.dd.rbook.room.book

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "book")
class Book {

    @PrimaryKey
    lateinit var id: String

    var name: String? = null //书名
    var chapterUrl: String? = null //书目Url(本地书籍为：本地书籍地址)
    var infoUrl: String? = null //书目详情Url(本地书籍为：文件编码)
    var imgUrl: String? = null //封面图片url
    var desc: String? = null //简介
    var author: String? = null //作者
    var type: String? = null //类型(本地书籍为：本地书籍)
    var updateDate: String? = null //更新时间
    var wordCount: String? = null
    var status: String? = null
    var newestChapterId: String? = null //最新章节id
    var newestChapterTitle: String? = null//最新章节标题
    var historyChapterId: String? = null//上次关闭时的章节ID
    var histtoryChapterNum = 0//上次关闭时的章节数
}