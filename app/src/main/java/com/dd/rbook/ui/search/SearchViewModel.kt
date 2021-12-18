package com.dd.rbook.ui.search

import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.TextUtils
import android.text.style.ForegroundColorSpan
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.dd.base.base.BaseViewModel
import com.dd.base.route.RouteCenter
import com.dd.base.utils.SpHelper
import com.dd.rbook.config.Constants
import com.dd.rbook.room.RoomInit
import com.dd.rbook.room.searchHistory.SearchHistory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONException
import org.json.JSONObject
import rxhttp.toStr
import rxhttp.wrapper.param.RxHttp
import java.util.ArrayList

class SearchViewModel : BaseViewModel() {

    var search= MutableLiveData<String>()
    var searchData: MutableList<String> = mutableListOf()
    var searchHistory = MutableLiveData<MutableList<String>>()


    fun getSearchFilter():Int{
        return SpHelper.getInt(Constants.Key.SEARCH_FILTE)
    }
    fun setSearchFilter(i : Int){
        SpHelper.put(Constants.Key.SEARCH_FILTE,i)
    }

    /*
    * 清空搜索输入框内容
    * */
    fun clearOnClick(){
        search.value=""
    }

    /*
    * 搜索图书
    * */
    fun searchOnClick(){
        if (TextUtils.isEmpty(search.value)){
            return
        }
        searchHistory.value = searchHistory.value?.apply {
            this.remove(search.value)
            this.add(0, search.value.toString())
        }

        launch {
            RoomInit.RmSearchHistory.SearchHistoryDao().insertSearchHistory(SearchHistory(content = search.value!!,createDate = System.currentTimeMillis().toString()))
        }
        val bundle = Bundle()
        bundle.putString("searchKey",search.value)
        RouteCenter.navigate(Constants.Router.Main.A_SEARCHRESULT,bundle)
    }
    /*
    * 获取历史搜索记录
    * */
    fun getSearchHistoryData()=launch {
        val text=RoomInit.RmSearchHistory.SearchHistoryDao().queryAll()
        withContext(Dispatchers.Main){
            searchHistory.value
        }
    }
    /*
    * 清除历史搜索记录
    * */
    fun clearSearchHistoryData(){
        searchHistory.value  = searchHistory.value?.apply {
            this.clear()
        }
        launch {
            RoomInit.RmSearchHistory.SearchHistoryDao().deleteAll()
        }
    }
    /*
    * 模拟热门数据，可以网络下发
    * */
    fun getHotSearch(): MutableList<String>{
        val historyList: MutableList<String> = ArrayList()
        historyList.add("诡秘之主")
        historyList.add("我的26岁女房客")
        historyList.add("我在风花雪月等你")
        historyList.add("我的郁金香小姐")
        historyList.add("夜的命名术")
        return historyList
    }

    fun getSearchKeyData(searchKey:String,value:(MutableList<String>)->Unit) = launch {
        searchData.clear()
        val jsonStr = RxHttp.get("https://newzxautocmp.reader.qq.com/BookSuggAll?key=$searchKey").toStr().await()

        try {
            val json= JSONObject(jsonStr)
            val names = json.getJSONArray("matchList")

            for (i in 0 until names.length()) {
                val title = names.getJSONObject(i).getString("title")
                val start: Int = title.indexOf(searchKey)
                if (start != -1) {
                    val spannableString = SpannableString(title)
                    spannableString.setSpan(
                        ForegroundColorSpan(Color.RED),
                        start, start + searchKey.length, Spanned.SPAN_INCLUSIVE_EXCLUSIVE
                    )
                    searchData.add(spannableString.toString())
                } else {
                    searchData.add(title)
                }
            }
            searchData
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        withContext(Dispatchers.Main) {
            value.invoke(searchData)
        }
    }
}