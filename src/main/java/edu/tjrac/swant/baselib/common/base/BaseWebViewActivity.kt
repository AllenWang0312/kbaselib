package edu.tjrac.swant.baselib.common.base

//package com.yanchuan.SuiQi.activity;

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import androidx.fragment.app.FragmentActivity
import edu.tjrac.swant.baselib.R
import edu.tjrac.swant.baselib.util.UiUtil


open class BaseWebViewActivity : BaseBarActivity() {

    var titleStr: String? = null
    private var url: String? = null
    private var config:Boolean?=false

    internal var content: String? = null

    lateinit var progressBar: ProgressBar
    lateinit var webView: WebView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        UiUtil.setStatusBar(this@BaseWebViewActivity, false, false)
        if(intent.hasExtra("url")) url = intent.getStringExtra("url")
//        + "?time=" + System.currentTimeMillis()
        if(intent.hasExtra("tital"))  titleStr = intent.getStringExtra("tital")
        if(intent.hasExtra("content")) content = intent.getStringExtra("content")
        if(intent.hasExtra("config"))  config=intent?.getBooleanExtra("config",false)
        if (url != null || content != null) {

        } else {
            showToast("content or url is null")
            finish()
            return
        }
        setContentView(R.layout.fragment_base_webview)
        //        bindToolbar(R.id.toolbar_bg);
        //        R.drawable.anglered
        setToolbar(findViewById<View>(R.id.toolbar))
        initView()
        initDate()
    }


    override fun setToolbar(view: View) {
        super.setToolbar(view)
        enableBackIcon()
        if (null != titleStr) {
            setTitle(titleStr)
        }
        setLeftIcon2(R.drawable.close, View.OnClickListener { _ ->
            finish()
        })
//        if(config!!){
//            setRightIcon2(R.drawable.more, View.OnClickListener{
//            })
//        }
    }

    open fun initView() {
        webView = findViewById<View>(R.id.webview) as WebView
        progressBar = findViewById<View>(R.id.progress) as ProgressBar
        val settings = webView.settings
        settings.javaScriptEnabled = false
        //设置自适应屏幕，两者合用
        settings.useWideViewPort = true //将图片调整到适合webview的大小
        settings.loadWithOverviewMode = true // 缩放至屏幕的大小

        //缩放操作
        settings.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
        settings.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
        settings.setDisplayZoomControls(false); //隐藏原生的缩放控件
        //其他细节操作
        settings.setAllowFileAccess(true); //设置可以访问文件
        settings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        settings.loadsImagesAutomatically = true //支持自动加载图片
        settings.defaultTextEncodingName = "utf-8"///设置编码格式

        settings.setAppCacheEnabled(true)
        settings.cacheMode = WebSettings.LOAD_DEFAULT
        //缓存模式如下：
        //LOAD_CACHE_ONLY: 不使用网络，只读取本地缓存数据
        //LOAD_DEFAULT: （默认）根据cache-control决定是否从网络上取数据。
        //LOAD_NO_CACHE: 不使用缓存，只从网络获取数据.
        //LOAD_CACHE_ELSE_NETWORK，只要本地有，无论是否过期，或者no-cache，都使用缓存中的数据。

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings.mixedContentMode = WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE
        }
        //        复写shouldOverrideUrlLoading()方法，使得打开网页时不调用系统浏览器， 而是在本WebView中显示
        webView.webViewClient = getWebViewClient()
        webView.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
                if (newProgress == 100) {
                    progressBar.visibility = View.GONE
                    if (null == titleStr) {
                        setTitle(webView.title)
                    }
                } else {
                    progressBar.visibility = View.VISIBLE
                    progressBar.progress = newProgress
                }
            }
        }
    }

    open fun getWebViewClient(): WebViewClient {
        return object : WebViewClient() {
            //          override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
//              return super.shouldOverrideUrlLoading(view, request)
//          }
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                view.loadUrl(url)
                Log.i("url", url)
                return true
            }
        }
    }

    private fun initDate() {
        if (url != null) {
            webView.loadUrl(url)
        } else {
            if (content != null) {
                webView.loadData(content, "head_chose_file/html", "UTF-8")
            }
        }
    }

//    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
//        if (keyCode == KEYCODE_BACK && webView!!.canGoBack()) {
//            webView!!.goBack()
//            return true
//        }
//        return super.onKeyDown(keyCode, event)
//    }

    override fun onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack()
        } else {
            super.onBackPressed()
        }
    }

    override fun onDestroy() {
        //由于内核缓存是全局的因此这个方法不仅仅针对webview而是针对整个应用程序.
        //        webview.clearCache(true);
        //清除当前webview访问的历史记录
        //只会webview访问历史记录里的所有记录除了当前访问记录
        webView.clearHistory()
        webView.destroy()
        //这个api仅仅清除自动完成填充的表单数据，并不会清除WebView存储到本地的数据
        //        Webview.clearFormData()；
        super.onDestroy()
    }

    companion object {

        fun start(activity: Context, title: String, url: String) {
            activity.startActivity(Intent(activity, BaseWebViewActivity::class.java)
                    .putExtra("url", url)
                    .putExtra("tital", title))
        }

        fun startWithContent(activity: FragmentActivity, title: String, content: String) {
            activity.startActivity(Intent(activity, BaseWebViewActivity::class.java)
                    .putExtra("content", content)
                    .putExtra("tital", title))
        }
    }
}
