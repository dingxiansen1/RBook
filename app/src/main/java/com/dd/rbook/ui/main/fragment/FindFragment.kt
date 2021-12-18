package com.dd.rbook.ui.main.fragment

import com.dd.base.base.BaseVDFragment
import com.dd.baseui.recyclerview.utils.linear
import com.dd.baseui.recyclerview.utils.setup
import com.dd.rbook.R
import com.dd.rbook.databinding.FindFragmentBinding

class FindFragment : BaseVDFragment<FindFragmentBinding>(R.layout.find_fragment) {


    override fun initView() {
        binding!!.recycler.linear().setup {

        }
    }

}