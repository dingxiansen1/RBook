package com.dd.rbook.ui.search

import androidx.lifecycle.MutableLiveData
import com.dd.base.base.BaseViewModel
import com.dd.rbook.api.SearchBookApi
import com.dd.rbook.room.book.Book
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.withContext

class SearchResultViewModel : BaseViewModel() {

    var search= MutableLiveData<String>()



    /*
    * 搜索图书
    * */
    fun searchOnClick(block: ((MutableList<Book>) -> Unit)?){
        launch {
            val data=SearchBookApi.searchBook(search.value.toString())
            withContext(Dispatchers.Main) {
                block?.invoke(data)
            }
        }
    }


    /*
    * 清空搜索输入框内容
    * */
    fun clearOnClick(){
        search.value=""
    }

}