package com.dd.rbook.ui.main.fragment

import com.dd.base.base.BaseVBFragment
import com.dd.base.base.BaseVDFragment
import com.dd.base.route.RouteCenter
import com.dd.rbook.R
import com.dd.rbook.config.Constants
import com.dd.rbook.databinding.BookFragmentBinding

class BookFragment : BaseVDFragment<BookFragmentBinding>(R.layout.book_fragment) {
    override fun initView() {
        binding!!.searchLayout.setOnClickListener {
            RouteCenter.navigate(Constants.Router.Main.A_SEARCH)
        }
    }
}