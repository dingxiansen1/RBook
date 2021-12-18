package com.dd.rbook.http

import android.util.Log
import com.dd.rbook.bean.StrResponse
import com.dd.rbook.common.AppConst
import com.dd.rbook.util.SSLSocketClient.*
import okhttp3.*
import okhttp3.Response
import java.io.IOException
import java.nio.charset.Charset
import java.util.concurrent.TimeUnit
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLSession

class OkHttpUtils {
    companion object{
        var mClient: OkHttpClient? = null

        @Synchronized
        fun getOkHttpClient(): OkHttpClient? {
            if (mClient == null) {
                val builder = OkHttpClient.Builder()
                builder.connectTimeout(5, TimeUnit.SECONDS)
                    .readTimeout(10, TimeUnit.SECONDS)
                    .writeTimeout(10, TimeUnit.SECONDS)
                    .sslSocketFactory(createSSLSocketFactory(), createTrustAllManager())
                    .hostnameVerifier(HostnameVerifier { hostname: String?, session: SSLSession? -> true })
                    .protocols(listOf(Protocol.HTTP_1_1))
                    .addInterceptor(getHeaderInterceptor())
                mClient = builder
                    .build()
            }
            return mClient
        }

        /**
         * 同步获取html文件，默认编码utf-8
         */
        @Throws(IOException::class)
        fun getHtml(url: String?): String? {
            return getHtml(url, "utf-8")
        }

        @Throws(IOException::class)
        fun getHtml(url: String?, encodeType: String?): String? {
            return getHtml(url, null, encodeType)
        }

        @Throws(IOException::class)
        fun getHtml(url: String?, encodeType: String?, headers: Map<String, String>?): String? {
            return getHtml(url, null, encodeType, headers)
        }

        @Throws(IOException::class)
        fun getHtml(url: String?, requestBody: RequestBody?, encodeType: String?): String? {
            return getHtml(url, requestBody, encodeType, null)
        }

        @Throws(IOException::class)
        fun getHtml(
            url: String?,
            requestBody: RequestBody?,
            encodeType: String?,
            headers: Map<String, String>?
        ): String {
            val response: Response = getResponse(url, requestBody, headers)
            val body = response.body
            return if (body == null) {
                ""
            } else {
                val bodyStr = String(body.bytes(), Charset.forName(encodeType))
                Log.d("Http -> read finish", bodyStr)
                bodyStr
            }
        }

        /**
         * 同步获取StrResponse
         */
        @Throws(IOException::class)
        fun getStrResponse(
            url: String?,
            encodeType: String?,
            headers: Map<String, String>?
        ): StrResponse {
            return getStrResponse(url, null, encodeType, headers)
        }

        @Throws(IOException::class)
        fun getStrResponse(
            url: String?,
            requestBody: RequestBody?,
            encodeType: String?,
            headers: Map<String, String>?
        ): StrResponse {
            val strResponse = StrResponse()
            strResponse.setEncodeType(encodeType)
            strResponse.setResponse(getResponse(url, requestBody, headers))
            return strResponse
        }

        @Throws(IOException::class)
        fun getResponse(
            url: String?,
            requestBody: RequestBody?,
            headers: Map<String, String>?
        ): Response {
            val builder: Request.Builder = Request.Builder()
                .addHeader("Accept", "*/*")
                .addHeader("Connection", "Keep-Alive") //.addHeader("Charsert", "utf-8")
                .addHeader("Cache-Control", "no-cache")
                .addHeader("User-Agent", AppConst.DEFAULT_USER_AGENT)
            if (headers != null) {
                for (name in headers.keys) {
                    if (name.toLowerCase() == "user-agent") {
                        builder.header(name, headers[name]!!)
                    } else {
                        builder.addHeader(name, headers[name]!!)
                    }
                }
            }
            if (requestBody != null) {
                builder.post(requestBody)
                Log.d("HttpPost URl", url!!)
            } else {
                Log.d("HttpGet URl", url!!)
            }
            val request: Request = builder
                .url(url)
                .build()
            return getOkHttpClient()
                ?.newCall(request)
                ?.execute()!!
        }
    }
}