package edu.tjrac.swant.kotlin.baselib.common

import android.app.Application
import java.util.*

/**
 * Created by wpc on 2018-08-02.
 */

abstract class BaseApplication : Application() {

    protected var activitys: ArrayList<BaseActivity>? = null


    override fun onCreate() {
        super.onCreate()
    }

    fun addActivity(activity: BaseActivity) {
        if (null === activitys) {
            activitys = ArrayList<BaseActivity>()
        }
        activitys!!.add(activity)
    }

    fun removeActivity(activity: BaseActivity) {
        activitys?.remove(activity)
    }

    fun exit() {
        for (item in activitys!!) {
            item.finish()
        }
        activitys!!.clear()
    }



    companion object {

        public open var instance: BaseApplication? = null
            set
    }

}