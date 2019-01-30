package edu.tjrac.swant.baselib.util

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import edu.tjrac.swant.baselib.R
import edu.tjrac.swant.baselib.common.base.BaseApplication

//import es.dmoral.toasty.Toasty;

/**
 * Created by wpc on 2017/4/1.
 */

open class T {
    companion object {
        val duration = Toast.LENGTH_SHORT

        fun show(str: CharSequence) {
            val t = Toast.makeText(BaseApplication.instance, str, duration)
            t.setGravity(Gravity.CENTER, 0, 0)
            t.show()
        }
        fun show(context: Context?, str: CharSequence) {
            if(null!=context){
                val t = Toast.makeText(context, str, duration)
                t.setGravity(Gravity.CENTER, 0, 0)
                t.show()
            }
        }

        fun showToast(context: Context?, msg: String, resId: Int) {
            if(null!=context){
                val inflater = LayoutInflater.from(context)
                val view = inflater.inflate(R.layout.toast_view, null)
                val imageView = view.findViewById<ImageView>(R.id.toast_image)
                imageView.setBackgroundResource(resId)
                val t = view.findViewById<TextView>(R.id.toast_text)
                t.text = msg

                var toast = Toast(context)
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.duration = Toast.LENGTH_SHORT
                toast.view = view
                toast.show()
            }
        }
    }

    val DeBug = true


    fun debug(context: Context, str: CharSequence) {
        if (DeBug) {
            Toast.makeText(context, str, duration).show()
        }
    }

    fun isDevelopping(cont: Context) {
        Toast.makeText(cont, "模块正在开发中", duration).show()
        //        Toasty.warning(cont, "模块正在开发中").show();
    }




    fun showWarning(context: Context, str: CharSequence) {
        //        Toasty.warning(context, str).show();
    }

    fun showError(context: Context, str: CharSequence) {
        //        Toasty.error(context, str).show();
    }

    fun showSuccess(context: Context, str: CharSequence) {
        //        Toasty.success(context, str).show();
    }
}
