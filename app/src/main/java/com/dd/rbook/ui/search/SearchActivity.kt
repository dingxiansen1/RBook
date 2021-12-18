package com.dd.rbook.ui.search

import android.text.TextUtils
import androidx.lifecycle.Observer
import com.alibaba.android.arouter.facade.annotation.Route
import com.dd.base.base.BaseVDActivity
import com.dd.base.utils.isVisible
import com.dd.baseui.dialog.AlertDialog
import com.dd.rbook.R
import com.dd.rbook.config.Constants
import com.dd.rbook.databinding.SearchActivityBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.withContext

@Route(path = Constants.Router.Main.A_SEARCH)
class SearchActivity :BaseVDActivity<SearchActivityBinding>(R.layout.search_activity){

    lateinit var mViewModel : SearchViewModel
    private val searchAdapter= SearchAdapter()
    private val searchHistoryAdapter= SearchAdapter()
    private val hotSearchAdapter= SearchAdapter()
    override fun initView() {
        mViewModel=getViewModel(SearchViewModel::class.java)
        binding.m=mViewModel
        binding.a=this
        initData()
        initSearchFilter()
        initObserve()
        binding.searchingList.setAdapter(searchAdapter)
        initSearchHistory()
        initHotSearch()
    }

    private fun initSearchFilter() {
        when(mViewModel.getSearchFilter()){
            0->binding.rbAllSearch.isChecked =true
            1->binding.rbFuzzySearch.isChecked =true
            2->binding.rbPreciseSearch.isChecked =true
            else -> binding.rbAllSearch.isChecked =true
        }
    }

    /*
    * 初始化数据
    * */
    private fun initData() {
        mViewModel.getSearchHistoryData()
    }
    /*
    * 观察输入框：动态更新大家都在搜的内容
    * 观察输入框（动态隐藏显示”清除输入框内容按钮“）
    * */
    private fun initObserve() {
        mViewModel.search.observe(this, Observer {
            if (TextUtils.isEmpty(it)||it.equals("")){
                isVisible(binding.searchInputClearImg,false)
                isVisible(binding.searchLayout,false)
                searchAdapter.clearData()
            }else{
                isVisible(binding.searchInputClearImg,true)
                isVisible(binding.searchLayout,true)
                mViewModel.getSearchKeyData(it){ value ->
                    searchAdapter.setNewData(value)
                }
            }
        })
    }
    /*
    * 初始化搜索历史
    * */
    private fun initSearchHistory() {
        binding.searchHistoryList.setAdapter(searchHistoryAdapter)
        mViewModel.searchHistory.observe(this, Observer {
            if (it==null||it.size<=0){
                isVisible(binding.searchHistoryLayout,false)
            }else{
                searchHistoryAdapter.setNewData(mViewModel.searchHistory.value)
                isVisible(binding.searchHistoryLayout,true)
            }
        })
    }
    /*
    * 删除历史搜索记录
    * */
    fun clearSearchHistory(){
        AlertDialog(this)
            .setDialogTitle("温馨提示")
            .setHtmlMessage("您确定要删除历史搜索记录吗？")
            .setLeftButton("取消")
            .setRightButton("确定") {
                mViewModel.clearSearchHistoryData()
            }
            .show()
    }
    /*
    * 初始化热门搜索
    * */
    private fun initHotSearch() {
        hotSearchAdapter.setNewData(mViewModel.getHotSearch())
        binding.hotSearchList.setAdapter(hotSearchAdapter)
    }


}