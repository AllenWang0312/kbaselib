package edu.tjrac.swant.baselib.common

import android.view.View

class ClickProxy : View.OnClickListener {

    private var origin: View.OnClickListener? = null
    private var lastclick: Long = 0
    private var timems: Long = 1000 //ms
    private var mIAgain: IAgain?=null

    constructor(origin: View.OnClickListener, timems: Long, again: IAgain) {
        this.origin = origin
        this.mIAgain = again
        this.timems = timems
    }

    constructor(origin: View.OnClickListener) {
        this.origin = origin
    }

    override fun onClick(v: View) {
        if (System.currentTimeMillis() - lastclick >= timems) {
            origin!!.onClick(v)
            lastclick = System.currentTimeMillis()
        } else {
            mIAgain?.onAgain()
        }
    }

    interface IAgain {
        fun onAgain() //重复点击
    }
}
