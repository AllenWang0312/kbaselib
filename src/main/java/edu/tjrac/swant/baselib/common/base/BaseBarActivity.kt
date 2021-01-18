package edu.tjrac.swant.baselib.common.base

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import edu.tjrac.swant.baselib.R

/**
 * Created by wpc on 2018-09-03.
 */

abstract class BaseBarActivity : BaseActivity() {

    var tool: View? = null
    var iv_left: ImageView? = null
    var iv_left2: ImageView? = null
    var tv_left: TextView? = null

    var iv_right: ImageView? = null
    var iv_right2: ImageView? = null

    var taiter: TextView? = null
    var tv_right: TextView? = null

    open fun setToolbar(tool: View) {
        this.tool = tool
        iv_left = tool.findViewById(R.id.iv_left)
        iv_left2 = tool.findViewById(R.id.iv_left2)
        tv_left = tool.findViewById(R.id.tv_left)

        taiter = tool.findViewById(R.id.title)
        iv_right = tool.findViewById(R.id.iv_right)
        iv_right2 = tool.findViewById(R.id.iv_right2)
        tv_right = tool.findViewById(R.id.tv_right)
    }

    override fun setTitle(t: CharSequence?) {
        taiter?.visibility = View.VISIBLE
        taiter?.text = t
    }

    fun setTitle(t: CharSequence?, colorId: Int) {
        taiter?.visibility = View.VISIBLE
        taiter?.setTextColor(resources.getColor(colorId))
        taiter?.text = t
    }

    fun setLeftIcon(id: Int, click: View.OnClickListener) {
        iv_left?.visibility = View.VISIBLE
        iv_left?.setImageResource(id)
        iv_left?.setOnClickListener(click)
    }

    fun setLeftIcon2(id: Int, click: View.OnClickListener) {
        iv_left2?.visibility = View.VISIBLE
        iv_left2?.setImageResource(id)
        iv_left2?.setOnClickListener(click)
    }

    fun setRightIcon(id: Int, click: View.OnClickListener) {
        iv_right?.visibility = View.VISIBLE
        iv_right?.setImageResource(id)
        iv_right?.setOnClickListener(click)
    }

    fun setLeftText(text: String, click: View.OnClickListener) {
        tv_left?.visibility = View.VISIBLE
        tv_left?.text = text
        tv_left?.setOnClickListener(click)
    }

    fun setRightText(text: String, click: View.OnClickListener) {
        tv_right?.visibility = View.VISIBLE
        tv_right?.text = text
        tv_right?.setOnClickListener(click)
    }

    fun setRightText(text: String, colorId: Int, click: View.OnClickListener) {
        tv_right?.visibility = View.VISIBLE
        tv_right?.setTextColor(resources.getColor(colorId))
        this.setRightText(text, click)
    }

    fun setRightIcon2(id: Int, click: View.OnClickListener) {
        iv_right2?.visibility = View.VISIBLE
        iv_right2?.setImageResource(id)
        iv_right2?.setOnClickListener(click)
    }

    fun enableBackIcon() {
        enableBackIcon(R.drawable.back)
    }

    fun enableBackIcon(red_id: Int) {
        this.setLeftIcon(red_id,View.OnClickListener { _ ->
            iv_left?.visibility = View.VISIBLE
            onBackPressed()
        })
    }
}