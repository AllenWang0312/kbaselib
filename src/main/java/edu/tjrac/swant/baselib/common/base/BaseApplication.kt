package edu.tjrac.swant.baselib.common.base

import android.app.Activity
import android.app.Application

/**
 * Created by wpc on 2018-08-02.
 */

abstract class BaseApplication : Application() {

    var activitys: ArrayList<Activity>? = null


    override fun onCreate() {
        super.onCreate()
    }

    fun addActivity(activity: Activity) {
        if (null === activitys) {
            activitys = ArrayList<Activity>()
        }
        activitys!!.add(activity)
    }

    fun removeActivity(activity: Activity) {
        activitys?.remove(activity)
    }

    fun exit() {
        for (item in activitys!!) {
            item.finish()
        }
        activitys!!.clear()
    }



    companion object {

        public  var instance: BaseApplication? = null
            set
    }

}