package edu.tjrac.swant.baselib.common

import android.widget.RadioGroup


fun RadioGroup.whereIs(checkedId: Int):Int {
    for (i in 0 until childCount!!) {
        if (getChildAt(i).id == checkedId) {
            return i
        }
    }
    return -1
}
fun RadioGroup.indesOf(checkedId: Int):Int {
   return indexOfChild(findViewById(checkedId))
}