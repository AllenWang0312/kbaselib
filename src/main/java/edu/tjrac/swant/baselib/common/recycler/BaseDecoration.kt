package edu.tjrac.swant.baselib.common.recycler

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View

/**
 * Created by wpc on 2018/1/31 0031.
 */

class BaseDecoration(private val mContext: Context, orientation: Int) : RecyclerView.ItemDecoration() {
    private val mDivider: Drawable?
    private var mOrientation: Int = 0

    init {
        val ta = mContext.obtainStyledAttributes(ATRRS)
        this.mDivider = ta.getDrawable(0)
        ta.recycle()
        setOrientation(orientation)
    }

    //设置屏幕的方向
    fun setOrientation(orientation: Int) {
        require(!(orientation != LinearLayoutManager.HORIZONTAL && orientation != LinearLayoutManager.VERTICAL)) { "invalid orientation" }
        mOrientation = orientation
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        if (mOrientation == LinearLayoutManager.HORIZONTAL) {
            drawVerticalLine(c, parent, state)
        } else {
            drawHorizontalLine(c, parent, state)
        }
    }

    //画横线, 这里的parent其实是显示在屏幕显示的这部分
    fun drawHorizontalLine(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val left = parent.paddingLeft
        val right = parent.width - parent.paddingRight
        val childCount = parent.childCount
        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)

            //获得child的布局信息
            val params = child.layoutParams as RecyclerView.LayoutParams
            val top = child.bottom + params.bottomMargin
            val bottom = top + mDivider!!.intrinsicHeight
            mDivider.setBounds(left, top, right, bottom)
            mDivider.draw(c)
            //Log.d("wnw", left + " " + top + " "+right+"   "+bottom+" "+i);
        }
    }

    //画竖线
    fun drawVerticalLine(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val top = parent.paddingTop
        val bottom = parent.height - parent.paddingBottom
        val childCount = parent.childCount
        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)

            //获得child的布局信息
            val params = child.layoutParams as RecyclerView.LayoutParams
            val left = child.right + params.rightMargin
            val right = left + mDivider!!.intrinsicWidth
            mDivider.setBounds(left, top, right, bottom)
            mDivider.draw(c)
        }
    }

    //由于Divider也有长宽高，每一个Item需要向下或者向右偏移
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        if (mOrientation == LinearLayoutManager.VERTICAL) {
            //画横线，就是往下偏移一个分割线的高度
            outRect.set(0, 0, 0, mDivider!!.intrinsicHeight)
        } else {
            //画竖线，就是往右偏移一个分割线的宽度
            outRect.set(0, 0, mDivider!!.intrinsicWidth, 0)
        }
    }

    companion object {
        //我们通过获取系统属性中的listDivider来添加，在系统中的AppTheme中设置
        val ATRRS = intArrayOf(android.R.attr.listDivider)
    }
}
