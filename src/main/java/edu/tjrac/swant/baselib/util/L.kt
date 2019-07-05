package edu.tjrac.swant.baselib.util

import android.util.Log
import edu.tjrac.swant.baselib.BuildConfig


/**
 * 功能描述:
 * 作者: wpc
 * 创建于: 2018/3/14 0014 下午 12:55
 */
object L {
    val isdebug = BuildConfig.DEBUG

    fun i(tag: String, str: String) {
        if (isdebug) Log.i(tag, str)
    }

    fun i(str: String) {
        i("", str)
    }

    fun d(tag: String, str: String) {
        if (isdebug) Log.d(tag, str)
    }

    fun d(str: String) {
        d("", str)
    }

    fun v(tag: String, str: String) {
        if (isdebug) Log.v(tag, str)
    }

    fun v(str: String) {
        v("", str)
    }

    fun e(tag: String, str: String) {
        if (isdebug) Log.e(tag, str)
    }

    fun e(str: String) {
        e("", str)
    }
}
