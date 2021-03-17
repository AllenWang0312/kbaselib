package edu.tjrac.swant.baselib.common.base

import android.app.Dialog

/**
 * Created by wpc on 2018-08-02.
 */

interface BaseView {

    fun showToast(msg: String)

    fun showToast(msg: String,resId:Int)

    fun showInfoDialog()

    fun dismissInfoDialog()

    fun showProgressDialog()
    fun showProgressDialog(cancleable: Boolean)
    fun showProgressDialog(text:String,cancleable:Boolean)
    fun dismissProgressDialog()


    companion object {
        val DIALOG: Dialog? = null
    }


}
