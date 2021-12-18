package com.dd.rbook.ui.search

import android.os.Bundle
import com.dd.baseui.flowLayout.FlowAdapter
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import android.widget.Toast
import com.dd.base.route.RouteCenter
import com.dd.rbook.R
import com.dd.rbook.config.Constants

/**
 * @author zwl
 * @date on 2021/8/5
 */
class SearchAdapter : FlowAdapter<String>() {

    override fun getView(parent: ViewGroup?, item: Any?, position: Int): View? {
        return LayoutInflater.from(parent!!.context).inflate(R.layout.item_search, null)
    }

    override fun initView(view: View?, item: Any?, position: Int) {
        val textView: AppCompatTextView = view!!.findViewById(R.id.item_tv)
        textView.text = getItem(position)!!.toString()
        textView.setOnClickListener { v: View? ->
            val bundle = Bundle()
            bundle.putString("searchKey",getItem(position)!!.toString())
            RouteCenter.navigate(Constants.Router.Main.A_SEARCHRESULT,bundle)
        }
    }
}