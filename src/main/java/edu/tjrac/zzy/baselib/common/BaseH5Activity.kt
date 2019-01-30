package edu.tjrac.swant.kotlin.baselib.common

import android.annotation.SuppressLint
import android.os.Bundle

/**
 * Created by wpc on 2018-09-03.
 */

abstract class BaseH5Activity<out T : H5InterfaceBean> : BaseWebViewActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @SuppressLint("JavascriptInterface")
    override fun initView() {
        super.initView()
        webView.addJavascriptInterface(getH5Interface(), getH5Interface().toString())
    }

    abstract fun getH5Interface(): T?
}