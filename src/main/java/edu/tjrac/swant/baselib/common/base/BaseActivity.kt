package edu.tjrac.swant.baselib.common.base

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.pm.ActivityInfo
import android.content.res.TypedArray
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import edu.tjrac.swant.baselib.BuildConfig
import edu.tjrac.swant.baselib.R
import edu.tjrac.swant.baselib.util.LeakUtil
import edu.tjrac.swant.baselib.util.SUtil
import edu.tjrac.swant.baselib.util.T
import edu.tjrac.swant.baselib.util.UiUtil


/**
 * Created by wpc on 2018-08-02.
 */

 abstract class BaseActivity : AppCompatActivity(), BaseContextView {

    override fun getContext(): Context {
        return mContext
    }

    lateinit var mContext: Context
    var TAG = javaClass.simpleName
    var themeId = 0
    var progress: Dialog? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        mContext = this
        super.onCreate(savedInstanceState)
        initStatusBar()
        setOrientation()
        BaseApplication.instance?.addActivity(this)
//        Log.d(this.javaClass.simpleName, "onCreate")
    }

    protected fun setOrientation() {
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.O) {
            return
        }
        if (isTranslucentOrFloating()) {//android 8.0 透明activity 不能设置方向
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED          //跟随父activity
        } else {
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT          //竖屏
        }
    }

    private fun isTranslucentOrFloating(): Boolean {
        var isTranslucentOrFloating = false
        try {
            val styleableRes = Class.forName("com.android.internal.R\$styleable").getField("Window").get(null) as IntArray
            val ta = obtainStyledAttributes(styleableRes)
            val m = ActivityInfo::class.java.getMethod("isTranslucentOrFloating", TypedArray::class.java)
            m.isAccessible = true
            isTranslucentOrFloating = m.invoke(null, ta) as Boolean
            m.isAccessible = false
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return isTranslucentOrFloating
    }

    open fun initStatusBar() {
        if (themeId == R.style.AppTheme) {
            UiUtil.setStatusBar(this, false, true)
        } else if (themeId == R.style.AppTheme_TranslucentStatus) {
            UiUtil.setStatusBar(this, true, true)
        }
    }

    override fun setTheme(resid: Int) {
//        Log.d(TAG,"setTheme")
        super.setTheme(resid)
        themeId = resid
    }

//    override fun setTitle(title: CharSequence?) {
//        super.setTitle(title)
//    }

    //baseview
    override fun showToast(msg: String) {
        T.show(msg)
    }

    @SuppressLint("WrongConstant")
    override fun showToast(msg: String, resId: Int) {
        if (null != mContext) {
            T.showToast(mContext, msg, resId)
        }
    }

    override fun showInfoDialog() {

    }

    override fun dismissInfoDialog() {
    }

    override fun dismissProgressDialog() {
        if (progress != null && progress!!.isShowing) {
            progress!!.dismiss()
        }
    }

    override fun showProgressDialog() {
        showProgressDialog("")
    }

    override fun showProgressDialog(text: String) {
        if (null != mContext) {
            if (progress == null) {
                val view = LayoutInflater.from(mContext).inflate(R.layout.progress, null)
                progress = Dialog(mContext, R.style.default_dialog_style)
                progress!!.setContentView(view)
//            prog = ProgressDialog(mContext)
            }
            val tv_progress = progress!!.findViewById<TextView>(R.id.tv_progress)
            if (!SUtil.isEmpty(text)) {
                tv_progress.visibility = View.VISIBLE
                tv_progress.setText(text)
            } else {
                tv_progress.visibility = View.GONE
            }
            progress!!.show()
        }
    }

    override fun onDestroy() {
        BaseApplication.instance?.removeActivity(this)
        if ("huawei,honor".contains(Build.BRAND.toLowerCase()) && Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            LeakUtil.fixInputMethodManagerLeak(this)
        }
        super.onDestroy()
        Log.d(this.javaClass.simpleName, "onDeatroy")
    }

    override fun onResume() {
        super.onResume()
        if (!BuildConfig.DEBUG) {
            try {
                val aClass = Class.forName("com.umeng.analytics.MobclickAgent")
                var getter = aClass.getDeclaredMethod("onResume", Context::class.java)
                getter.invoke(aClass.newInstance(), this)
            } catch (e: ClassNotFoundException) {
                e.printStackTrace()
            }
        }
//        Log.d(this.javaClass.simpleName, "onResume")
    }

    override fun onPause() {
        super.onPause()
        if (!BuildConfig.DEBUG) {
            try {
                val aClass = Class.forName("com.umeng.analytics.MobclickAgent")
                var getter = aClass.getDeclaredMethod("onPause", Context::class.java)
                getter.invoke(aClass.newInstance(), this)
            } catch (e: ClassNotFoundException) {
                e.printStackTrace()
            }
        }
//        Log.d(this.javaClass.simpleName, "onPause")
    }

}
