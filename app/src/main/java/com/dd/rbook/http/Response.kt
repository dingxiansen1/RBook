package com.dd.rbook.http

class Response<T> {
    var code = 0

    var msg: String? = null

    var data: T? = null

}