package com.dd.rbook

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Context
import com.dd.base.base.BaseApp
import com.dd.baseui.recyclerview.statelayout.StateConfig
import com.dd.baseui.recyclerview.utils.BRV
import com.dd.rbook.room.RoomInit
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.MaterialHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import java.util.concurrent.TimeUnit

class App : BaseApp() {


    companion object{
        @SuppressLint("StaticFieldLeak")
        var app :Context ?=null
    }

    override fun onCreate() {
        super.onCreate()

        app=this

        RoomInit.initDatabase(this)

        /**
         * 全局配置缺省页, 当然同样每个页面可以单独指定缺省页.
         */
        BRV.modelId=BR.m
        StateConfig.apply {
            emptyLayout = R.layout.layout_empty
            errorLayout = R.layout.layout_error
            loadingLayout = R.layout.layout_loading

            setRetryIds(R.id.msg)

            onLoading {
                // 此生命周期可以拿到LoadingLayout创建的视图对象, 可以进行动画设置或点击事件.
            }
        }

        SmartRefreshLayout.setDefaultRefreshHeaderCreator { context, layout ->
            MaterialHeader(context)
        }
        SmartRefreshLayout.setDefaultRefreshFooterCreator { context, layout ->
            ClassicsFooter(context)
        }
        initNet()
    }

    private fun initNet() {

    }
}