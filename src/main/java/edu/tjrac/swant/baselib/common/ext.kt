package edu.tjrac.swant.baselib.common

import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Paint
import android.text.TextUtils
import com.google.gson.JsonObject
import java.util.*


val Float.dp: Float                 // [xxhdpi](360 -> 1080)
    get() = android.util.TypedValue.applyDimension(android.util.TypedValue.COMPLEX_UNIT_DIP, this, Resources.getSystem().displayMetrics)

val Int.dp: Int
    get() = android.util.TypedValue.applyDimension(android.util.TypedValue.COMPLEX_UNIT_DIP, this.toFloat(), Resources.getSystem().displayMetrics)
        .toInt()


val Float.sp: Float                 // [xxhdpi](360 -> 1080)
    get() = android.util.TypedValue.applyDimension(android.util.TypedValue.COMPLEX_UNIT_SP, this, Resources.getSystem().displayMetrics)


val Int.sp: Int
    get() = android.util.TypedValue.applyDimension(android.util.TypedValue.COMPLEX_UNIT_SP, this.toFloat(), Resources.getSystem().displayMetrics)
        .toInt()

val Float.px: Float                 // [xxhdpi](360 -> 1080)
    get() = android.util.TypedValue.applyDimension(android.util.TypedValue.COMPLEX_UNIT_PX, this, Resources.getSystem().displayMetrics)


val Int.px: Int
    get() = android.util.TypedValue.applyDimension(android.util.TypedValue.COMPLEX_UNIT_PX, this.toFloat(), Resources.getSystem().displayMetrics)
        .toInt()

fun Canvas.drawLine(startX:Number,startY:Number,endX:Number,endY:Number,paint:Paint?){
    this.drawLine(startX.toFloat(),startY.toFloat(),endX.toFloat(),endY.toFloat(),paint)
}

fun JsonObject.equals(o: Any): Boolean {
    return this.toString().equals(o.toString())
}
