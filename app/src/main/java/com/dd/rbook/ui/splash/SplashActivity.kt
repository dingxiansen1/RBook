package com.dd.rbook.ui.splash

import com.alibaba.android.arouter.facade.annotation.Route
import com.dd.base.base.BaseVDActivity
import com.dd.base.route.RouteCenter
import com.dd.base.utils.SpHelper
import com.dd.base.utils.immersive
import com.dd.baseui.dialog.AlertDialog
import com.dd.rbook.R
import com.dd.rbook.config.Constants
import com.dd.rbook.databinding.SplashActivityBinding
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Route(path = Constants.Router.Main.A_SPLASH)
class SplashActivity : BaseVDActivity<SplashActivityBinding>(R.layout.splash_activity) {

    var job: Job? = null

    override fun initView() {
        immersive()

        binding.a=this

        //第一次进入APP，让用户同意用户协议和隐私协议
        val agreePrivacyPolicy = SpHelper.getBoolean(Constants.Key.AGREE_PRIVACYPOLICY)

        if (agreePrivacyPolicy) {
            openTimer()
        } else {
            AlertDialog(this)
                .setDialogCancelable(false)//不可取消
                .setDialogTitle("隐私政策")
                .setHtmlMessage(getString(R.string.privacy_tip))
                .setLeftButton("不同意") {
                    finish()
                }
                .setRightButton("同意并继续") {
                    SpHelper.put(Constants.Key.AGREE_PRIVACYPOLICY, true)
                    openTimer()
                }
                .show()
        }
    }

    fun gotoMain(){
        job?.let {
            job!!.cancel()
            job = null
        }
        RouteCenter.navigate(Constants.Router.Main.A_MAIN)
        finish()
    }

    private fun openTimer() {
        job = MainScope().launch {
            startTime()
            RouteCenter.navigate(Constants.Router.Main.A_MAIN)
            finish()
        }
    }

    suspend fun startTime(): Boolean {
        var i = 3
        while (i >= 0) {
            binding.btnSkip.text = getString(R.string.skip_ad, i)
            i--
            delay(1000)
        }
        return false
    }

}