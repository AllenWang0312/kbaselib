package edu.tjrac.swant.baselib.common.base

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.support.v4.app.FragmentActivity
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import edu.tjrac.swant.baselib.R
import edu.tjrac.swant.baselib.util.SUtil
import edu.tjrac.swant.baselib.util.T

/**
 * Created by wpc on 2019-12-10.
 */
open class BaseFragmentActivity : FragmentActivity(), BaseView {

    var mContext: Context? = null
        get() {
            if (field == null) field = this
            return field
        }

    var progress: Dialog? = null

    //base view
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
                var view = LayoutInflater.from(mContext).inflate(R.layout.progress, null)
                progress = Dialog(mContext, R.style.default_dialog_style)
                progress!!.setContentView(view)
//            prog = ProgressDialog(mContext)
            }
            var tv_progress = progress!!.findViewById<TextView>(R.id.tv_progress)
            if (!SUtil.isEmpty(text)) {
                tv_progress.visibility = View.VISIBLE
                tv_progress.setText(text)
            } else {
                tv_progress.visibility = View.GONE
            }
            progress!!.show()
        }
    }
}