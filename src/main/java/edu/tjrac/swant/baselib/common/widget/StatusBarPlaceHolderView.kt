package edu.tjrac.swant.baselib.common.widget

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.View
import edu.tjrac.swant.baselib.common.Config
import edu.tjrac.swant.baselib.util.UiUtil


/**
 * Created by wpc on 2018/7/12.
 */

class StatusBarPlaceHolderView : View {
    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {}

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {}

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        if (Build.VERSION.SDK_INT >= Config.transVersion) {
            setMeasuredDimension(widthMeasureSpec, UiUtil.getStatusBarHeight(context))
        } else {
            setMeasuredDimension(widthMeasureSpec, 0)
        }
    }
}
