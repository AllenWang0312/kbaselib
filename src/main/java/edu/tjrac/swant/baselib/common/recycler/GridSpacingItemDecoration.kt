package edu.tjrac.swant.baselib.common.recycler

import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View

/**
 * Created by wpc on 2019-09-05.
 */
class GridSpacingItemDecoration(private val spanCount: Int //列数
                                , private val vertical_spacing: Int
                                , private val spacing: Int //间隔
) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        //这里是关键，需要根据你有几列来判断
        val position = parent.getChildAdapterPosition(view) // item position
        val column = position % spanCount // item column

        outRect.left = spacing / 2
        outRect.right = spacing / 2
//        if (position >= spanCount) {
        outRect.top = vertical_spacing / 2 // item top
        outRect.bottom = vertical_spacing / 2
//        }
    }

}