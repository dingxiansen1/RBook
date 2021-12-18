package com.dd.rbook.ui.main

import android.graphics.Color
import com.alibaba.android.arouter.facade.annotation.Route
import com.dd.base.base.BaseVDActivity
import com.dd.rbook.R
import com.dd.rbook.config.Constants
import com.dd.rbook.databinding.MainActivityBinding
import com.dd.rbook.ui.main.fragment.BookFragment
import com.dd.rbook.ui.main.fragment.FindFragment
import com.dd.rbook.ui.main.fragment.MineFragment

@Route(path = Constants.Router.Main.A_MAIN)
class MainActivity : BaseVDActivity<MainActivityBinding>(R.layout.main_activity) {

    override fun initView() {

        binding.oneBottomLayout.attachViewPager(supportFragmentManager,binding.viewPager, listOf(
            BookFragment(),
            FindFragment(),
            MineFragment()
        ))
        binding.oneBottomLayout.isReplace = false
        binding.oneBottomLayout.setFloatingEnable(true)
        binding.oneBottomLayout.setTopLineColor(Color.BLUE)
    }
}