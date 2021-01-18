package edu.tjrac.swant.baselib.common.base

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import edu.tjrac.swant.baselib.R
import edu.tjrac.swant.baselib.util.L

/**
 * Created by wpc on 2020-01-15.
 */

open class BaseWebViewFragment : BaseFragment {

    var tital: String? = null
    var url: String? = null
    var content: String? = null
    var webview: WebView? = null
    var prog: ProgressBar? = null

    var client: WebViewClient? = null


    internal var layoutId: Int? = null

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString("url", url)
        outState.putString("content", content)
        super.onSaveInstanceState(outState)
    }

    constructor() {

    }

    constructor(url: String) {
        this.url = url
    }

    constructor(url: String, id: Int) {
        this.url = url
        layoutId = id
    }

    constructor(title: String, content: String) {
        this.tital = title
        this.content = content
    }

    var v: View? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        v = inflater.inflate(getLayoutId(), container, false)
        webview = v?.findViewById(R.id.webview)
        prog = v?.findViewById(R.id.progress)
        val settings = webview?.settings
        settings?.javaScriptEnabled = true
        //支持插件
        //        settings.setPluginsEnabled(true);
        //设置自适应屏幕，两者合用
        settings?.useWideViewPort = true //将图片调整到适合webview的大小
        settings?.loadWithOverviewMode = true // 缩放至屏幕的大小

        //缩放操作
        settings?.setSupportZoom(true) //支持缩放，默认为true。是下面那个的前提。
        settings?.builtInZoomControls = true //设置内置的缩放控件。若为false，则该WebView不可缩放
        //        webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件
        //其他细节操作
        //        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); //关闭webview中缓存
        //        webSettings.setAllowFileAccess(true); //设置可以访问文件
        //        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        settings?.loadsImagesAutomatically = true //支持自动加载图片
        settings?.defaultTextEncodingName = "utf-8"///设置编码格式

        //优先使用缓存:
        settings?.cacheMode = WebSettings.LOAD_NO_CACHE
        //缓存模式如下：
        //LOAD_CACHE_ONLY: 不使用网络，只读取本地缓存数据
        //LOAD_DEFAULT: （默认）根据cache-control决定是否从网络上取数据。
        //LOAD_NO_CACHE: 不使用缓存，只从网络获取数据.
        //LOAD_CACHE_ELSE_NETWORK，只要本地有，无论是否过期，或者no-cache，都使用缓存中的数据。

        //不使用缓存:
        //        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings?.mixedContentMode = WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE
        }
        if (client == null) {
            client = object : WebViewClient() {
                //默认自己处理重定向
                override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                    view.loadUrl(url)
                    return true
                }

            }
        }
        webview?.webViewClient = client!!

        webview?.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView, newProgress: Int) {
                Log.i("onProgressChanged", newProgress.toString() + "")
                if (newProgress == 100) {
                    prog?.visibility = View.GONE
                    if (tital == null) {
                        Log.i("onProgressChanged", webview?.title!!)
                    }
                    //                    setTital(web.getTital());
                } else {
                    if (prog?.visibility == View.GONE) {
                        prog?.visibility = View.VISIBLE
                    }
                    prog?.progress = newProgress
                }
                super.onProgressChanged(view, newProgress)
            }
        }


        //        String saveUrl= savedInstanceState.getString("url");
        //        if(saveUrl!=null){
        //            url=saveUrl;
        //        }
        //        savedInstanceState.get("content");
        return v
    }

    fun withWebViewClient(client: WebViewClient): BaseWebViewFragment {//自定义拦截操作
        this.client = client
        return this
    }

    override fun onBack() {
        if (webview?.canGoBack()!!) {
            webview?.goBack()
        } else {
            activity!!.onBackPressed()
        }
    }

    fun refeshData() {
        if (url != null) {
            L.i("refreshDataa", url!!)
            webview?.loadUrl(url!!)
        } else {
            if (content != null) {
                webview?.loadData(content!!, "text/html", "UTF-8")
            }
        }

    }

    fun isBackable(): Boolean {
        return webview?.canGoBack()!!
    }

    fun getLayoutId(): Int {
        return if (layoutId != null) {
            layoutId!!
        } else {
            R.layout.fragment_base_webview
        }

    }
}
