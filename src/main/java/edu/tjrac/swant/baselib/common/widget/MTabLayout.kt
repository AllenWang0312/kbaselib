package edu.tjrac.swant.baselib.common.widget

import android.content.Context
import android.graphics.Canvas
import android.support.v4.view.ViewPager
import android.widget.FrameLayout
import edu.tjrac.swant.baselib.R

/**
 * Created by wpc on 2019-12-11.
 */
class MTabLayout(context: Context) : FrameLayout(context) {

    var selectTextSize: Int? = 20
    var selectTextColor: Int? = R.color.colorAccent
    var textColor: Int? = R.color.text_color_primary
    var textSize: Int? = 16
//    var style:Int?=TextView.b

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
    }

    fun setUpWithViewPager(vp: ViewPager) {
        var adapter = vp.adapter
        adapter?.count

    }
}