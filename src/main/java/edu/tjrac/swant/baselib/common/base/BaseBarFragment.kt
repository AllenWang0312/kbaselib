package edu.tjrac.swant.baselib.common.base

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import edu.tjrac.swant.baselib.R

/**
 * Created by wpc on 2018-08-31.
 */


abstract class BaseBarFragment : BaseFragment() {

    var tool: View? = null
    var tv_left: TextView? = null
    var tv_right: TextView? = null

    var iv_left: ImageView? = null
    var iv_right: ImageView? = null

    var iv_right2: ImageView? = null

    var title: TextView? = null


    open fun setToolbar(tool: View) {
        this.tool = tool
        this.iv_left = tool.findViewById(R.id.iv_left)
        title = tool.findViewById(R.id.title)
        tv_left = tool.findViewById(R.id.tv_left)
        tv_right = tool.findViewById(R.id.tv_right)

        iv_right = tool.findViewById(R.id.iv_right)
        iv_right2 = tool.findViewById(R.id.iv_right2)
    }

    fun setTitle(t: String) {
        title!!.visibility = View.VISIBLE
        title?.text = t
    }

    fun setLeftText(text: String, click: View.OnClickListener) {
        tv_left!!.visibility = View.VISIBLE
        tv_left!!.text = text
        tv_left!!.setOnClickListener(click)
    }

    fun setRightText(text: String, click: View.OnClickListener) {
        tv_right!!.visibility = View.VISIBLE
        tv_right!!.text = text
        tv_right!!.setOnClickListener(click)
    }

    fun setLeftIcon(id: Int, click: View.OnClickListener) {
        iv_left!!.visibility = View.VISIBLE
        iv_left!!.setImageResource(id)
        iv_left!!.setOnClickListener(click)
    }

    fun setRightIcon(id: Int, click: View.OnClickListener) {
        iv_right!!.visibility = View.VISIBLE
        iv_right!!.setImageResource(id)
        iv_right!!.setOnClickListener(click)
    }

    fun setRightIcon2(id: Int, click: View.OnClickListener) {
        iv_right2!!.visibility = View.VISIBLE
        iv_right2!!.setImageResource(id)
        iv_right2!!.setOnClickListener(click)
    }

    fun enableBackIcon() {
        iv_left?.setImageResource(R.drawable.ic_arrow_back_white_24dp)
        iv_left?.setOnClickListener(View.OnClickListener { onBack() })
    }


}