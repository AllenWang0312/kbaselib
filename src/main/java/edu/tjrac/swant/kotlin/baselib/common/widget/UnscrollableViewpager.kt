package edu.tjrac.swant.kotlin.baselib.common.widget

import android.content.Context
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.MotionEvent

/**
 * 类描述:
 * 创建人: Administrator
 * 创建时间: 2018/3/28 0028 下午 3:18
 * 修改人:
 * 修改时间:
 * 修改备注:
 */

class UnscrollableViewpager : ViewPager {

    /**是否禁止左右滑动，true为禁止，false为不禁止 */
    private var noScroll = true

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

    constructor(context: Context) : super(context) {}

    fun setNoScroll(noScroll: Boolean) {
        this.noScroll = noScroll
    }

    override fun scrollTo(x: Int, y: Int) {
        super.scrollTo(x, y)
    }

//    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(arg0: MotionEvent): Boolean {
        if (noScroll)
            return false
        else
            return super.onTouchEvent(arg0)
    }

    override fun onInterceptTouchEvent(arg0: MotionEvent): Boolean {
        if (noScroll)
            return false
        else
            return super.onInterceptTouchEvent(arg0)
    }

    override fun setCurrentItem(item: Int, smoothScroll: Boolean) {
        super.setCurrentItem(item, smoothScroll)
    }

    override fun setCurrentItem(item: Int) {
        super.setCurrentItem(item)
    }
}
