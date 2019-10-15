package edu.tjrac.swant.baselib.util

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

import java.lang.reflect.Field

/**
 * Created by wpc on 2019-08-29.
 */
object LeakUtil {

    fun fixInputMethodManagerLeak(destContext: Context?) {
        if (destContext == null) {
            return
        }

        val imm = destContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager ?: return

        val arr = arrayOf("mCurRootView", "mServedView", "mNextServedView", "mLastSrvView")
        var f: Field? = null
        var obj_get: Any? = null
        for (i in arr.indices) {
            val param = arr[i]
            try {
                f = imm.javaClass.getDeclaredField(param)
                if (f!!.isAccessible == false) {
                    f.isAccessible = true
                } // author: sodino mail:sodino@qq.com
                obj_get = f.get(imm)
                if (obj_get != null && obj_get is View) {
                    val v_get = obj_get as View?
                    if (v_get!!.context === destContext) { // 被InputMethodManager持有引用的context是想要目标销毁的
                        f.set(imm, null) // 置空，破坏掉path to gc节点
                    } else {
                        // 不是想要目标销毁的，即为又进了另一层界面了，不要处理，避免影响原逻辑,也就不用继续for循环了
                        //                        if (QLog.isColorLevel()) {
                        //                            QLog.d(ReflecterHelper.class.getSimpleName(), QLog.CLR, "fixInputMethodManagerLeak break, context is not suitable, get_context=" + v_get.getContext()+" dest_context=" + destContext);
                        //                        }
                        break
                    }
                }
            } catch (t: Throwable) {
                t.printStackTrace()
            }

        }
    }
}
