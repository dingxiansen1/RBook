package com.dd.rbook.ui.search

import androidx.lifecycle.lifecycleScope
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.dd.base.base.BaseVDActivity
import com.dd.baseui.recyclerview.utils.linear
import com.dd.baseui.recyclerview.utils.models
import com.dd.baseui.recyclerview.utils.setup
import com.dd.rbook.R
import com.dd.rbook.config.Constants
import com.dd.rbook.databinding.SearchResultActivityBinding
import com.dd.rbook.room.book.Book
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Route(path = Constants.Router.Main.A_SEARCHRESULT)
class SearchResultActivity :BaseVDActivity<SearchResultActivityBinding>(R.layout.search_result_activity) {


    lateinit var mViewModel : SearchResultViewModel
    @JvmField
    @Autowired(name = "searchKey")
    var searchKey :String ?= null

    override fun initView() {
        ARouter.getInstance().inject(this)
        mViewModel=getViewModel(SearchResultViewModel::class.java)
        mViewModel.search.value=searchKey
        mViewModel.searchOnClick(null)
        binding.recycler.linear().setup{
            addType<Book>(R.layout.search_result_list_item)
        }

        binding.page.onRefresh {
            mViewModel.searchOnClick{
                binding.recycler.models=it
                addData(it)
            }
        }.autoRefresh()
    }
}