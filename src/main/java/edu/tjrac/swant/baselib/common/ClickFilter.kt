package edu.tjrac.swant.baselib.common

import android.view.View

object ClickFilter {
    fun setFilter(view: View) {
        try {
            val field = View::class.java.getDeclaredField("mListenerInfo")
            field.isAccessible = true
            val listInfoType = field.type
            val listinfo = field.get(view)
            val onclickField = listInfoType.getField("mOnClickListener")
            val origin = onclickField.get(listinfo) as View.OnClickListener
            onclickField.set(listinfo, ClickProxy(origin))
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }
}
